package com.platydev.calculmental.injection.datastore;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.rxjava3.RxDataStore;

public abstract class DataStore {

    protected RxDataStore<Preferences> dataStore;
}
