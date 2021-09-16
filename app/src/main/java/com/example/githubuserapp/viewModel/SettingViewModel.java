package com.example.githubuserapp.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.example.githubuserapp.SettingPreferences;

public class SettingViewModel extends ViewModel {
    private final SettingPreferences pref;


    public SettingViewModel(SettingPreferences pref) {
        this.pref = pref;
    }
    public LiveData<Boolean> getThemeSettings() {
        return LiveDataReactiveStreams.fromPublisher(pref.getThemeSetting());
    }

    public void saveThemeSetting(Boolean isDarkModeActive) {
        pref.saveThemeSetting(isDarkModeActive);
    }
    public LiveData<Boolean> getNotificationSettings() {
        return LiveDataReactiveStreams.fromPublisher(pref.getNotificationSetting());
    }
    public void saveNotificationSetting(Boolean isNotificationActive) {
        pref.saveNotificationSetting(isNotificationActive);
    }
}
