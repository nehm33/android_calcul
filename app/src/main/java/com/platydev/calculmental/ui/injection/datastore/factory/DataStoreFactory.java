package com.platydev.calculmental.ui.injection.datastore.factory;

import android.content.Context;

import com.platydev.calculmental.ui.injection.datastore.DataStore;

public interface DataStoreFactory {

    DataStore createDataStore(Context context);
}
