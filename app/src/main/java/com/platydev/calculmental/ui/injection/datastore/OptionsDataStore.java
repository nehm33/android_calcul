package com.platydev.calculmental.ui.injection.datastore;

import android.content.Context;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;

import com.platydev.calculmental.data.Mode;
import com.platydev.calculmental.data.Options;

import io.reactivex.rxjava3.core.Single;

public class OptionsDataStore extends DataStore {

    private static final Preferences.Key<Integer> NIVEAU = PreferencesKeys.intKey("niveau");
    private static final Preferences.Key<Integer> TEMPS = PreferencesKeys.intKey("temps");
    private static final Preferences.Key<String> MODE = PreferencesKeys.stringKey("mode");

    private static OptionsDataStore instance;

    private OptionsDataStore(Context context) {
        dataStore = new RxPreferenceDataStoreBuilder(context, "options").build();
    }

    public static OptionsDataStore getInstance(Context context) {
        if (instance == null) {
            synchronized (OptionsDataStore.class) {
                if (instance == null) {
                    instance = new OptionsDataStore(context);
                }
            }
        }
        return instance;
    }

    public Options readOptions() {
        Integer niveau = dataStore.data().map(prefs -> prefs.get(NIVEAU))
                                            .blockingFirst(Options.DEFAULT.getNiveau());
        Integer temps = dataStore.data().map(prefs -> prefs.get(TEMPS))
                                            .blockingFirst(Options.DEFAULT.getTemps());
        String mode = dataStore.data().map(prefs -> prefs.get(MODE))
                                            .blockingFirst(Options.DEFAULT.getMode().getNom());
        return new Options(niveau, temps, Mode.fromString(mode));
    }

    public void writeOptions(Options options) {
        Single<Preferences> updateResult =  dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(NIVEAU, options.getNiveau());
            mutablePreferences.set(TEMPS, options.getTemps());
            mutablePreferences.set(MODE, options.getMode().getNom());
            return Single.just(mutablePreferences);
        });
    }
}
