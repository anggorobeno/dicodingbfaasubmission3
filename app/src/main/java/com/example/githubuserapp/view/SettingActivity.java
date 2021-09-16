package com.example.githubuserapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.githubuserapp.NotificationReceiver;
import com.example.githubuserapp.R;
import com.example.githubuserapp.SettingPreferences;
import com.example.githubuserapp.ViewModelFactory;
import com.example.githubuserapp.viewModel.SettingViewModel;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Calendar;

import de.mateware.snacky.Snacky;

public class SettingActivity extends AppCompatActivity {
    private static final int NOTIFICATION = 1;
    SwitchCompat switchCompat;
    SwitchMaterial switchDarkMode;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        switchCompat = findViewById(R.id.switch1);
        switchDarkMode = findViewById(R.id.darkModeSwitch);
        textView = findViewById(R.id.textView);
        RxDataStore<Preferences> dataStore = new RxPreferenceDataStoreBuilder(this, "settings").build();
        SettingPreferences pref = SettingPreferences.getInstance(dataStore);
        SettingViewModel settingViewModel = new ViewModelProvider(this, new ViewModelFactory(pref)).get(SettingViewModel.class);
        settingViewModel.getThemeSettings().observe(this, isDarkModeActive -> {
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                switchDarkMode.setChecked(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                switchDarkMode.setChecked(false);
            }
        });
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) ->
                settingViewModel.saveThemeSetting(isChecked)
        );
        settingViewModel.getNotificationSettings().observe(this, isNotificationActive -> {
            Boolean switchState = switchCompat.isChecked();

            if (isNotificationActive) {
                enableNotification();
                textView.setText("On");

                switchCompat.setChecked(true);

            }
            else {
                cancelNotification();
                textView.setText("off");

                switchCompat.setChecked(false);
            }

        });
        switchCompat.setOnCheckedChangeListener((notifButton, isChecked1) ->
                settingViewModel.saveNotificationSetting(isChecked1)
        );


    }
    public void enableNotification() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (alarmManager != null)
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    public void cancelNotification() {
        Intent intent = new Intent(this, NotificationReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION, intent, 0);
        pendingIntent.cancel();

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (alarmManager != null)
            alarmManager.cancel(pendingIntent);


    }
}