package com.example.githubuserapp;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.rxjava3.RxDataStore;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class SettingPreferences {
    private final RxDataStore<Preferences> dataStore;
    private final Preferences.Key<Boolean> THEME_KEY = PreferencesKeys.booleanKey("theme_setting");
    private final Preferences.Key<Boolean> NOTIFICATION_KEY = PreferencesKeys.booleanKey("notification_setting");
    public SettingPreferences(RxDataStore<Preferences> dataStore) {
        this.dataStore = dataStore;
    }
    private static volatile SettingPreferences INSTANCE;
    public static SettingPreferences getInstance(final RxDataStore<Preferences> dataStore) {
        if (INSTANCE == null) {
            synchronized (SettingPreferences.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SettingPreferences(dataStore);
                }
            }
        }
        return INSTANCE;
    }
    public Flowable<Boolean> getThemeSetting() {
        return dataStore.data().map(preferences -> {
                    if (preferences.get(THEME_KEY) != null) {
                        return preferences.get(THEME_KEY);
                    } else {
                        return false;
                    }
                }
        );
    }
    public Flowable<Boolean> getNotificationSetting() {
        return dataStore.data().map(preferences -> {
                    if (preferences.get(NOTIFICATION_KEY) != null) {
                        return preferences.get(NOTIFICATION_KEY);
                    } else {
                        return false;
                    }
                }
        );
    }

    public void saveThemeSetting(Boolean isDarkModeActive) {
        dataStore.updateDataAsync(preferences -> {
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();
            mutablePreferences.set(THEME_KEY, isDarkModeActive);
            return Single.just(mutablePreferences);
        });
    }
    public void saveNotificationSetting(Boolean isNotificationActive) {
        dataStore.updateDataAsync(preferences -> {
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();
            mutablePreferences.set(NOTIFICATION_KEY, isNotificationActive);
            return Single.just(mutablePreferences);
        });
    }
}

