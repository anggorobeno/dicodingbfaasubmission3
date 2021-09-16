package com.example.githubuserapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.githubuserapp.NotificationReceiver;
import com.example.githubuserapp.R;

import java.util.Calendar;

import de.mateware.snacky.Snacky;

public class ReminderActivity extends AppCompatActivity {
    private static final int NOTIFICATION = 1;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    SwitchCompat switchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        switchCompat = findViewById(R.id.switch1);
        sharedPreferences = getSharedPreferences("alarm", Context.MODE_PRIVATE);
        switchCompat.setOnCheckedChangeListener((compoundButton,cek) -> {
            editor = sharedPreferences.edit();
            editor.putBoolean("notification", cek);
            editor.apply();

            if (cek) {
                enableNotification();
                Snacky.builder()
                        .setActivity(ReminderActivity.this)
                        .setText("Alarm set")
                        .setDuration(Snacky.LENGTH_SHORT)
                        .setActionText("OK")
                        .info()
                        .show();
            } else {
                cancelNotification();
            }
        });

        boolean check = sharedPreferences.getBoolean("notification", false);

        switchCompat.setChecked(check);
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
        Snacky.builder()
                .setActivity(ReminderActivity.this)
                .setText("Alarm canceled")
                .setDuration(Snacky.LENGTH_SHORT)
                .setActionText("OK")
                .info()
                .show();

    }
}