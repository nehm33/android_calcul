package com.platydev.calculmental.injection.datastore.factory;

import android.content.Context;

import com.platydev.calculmental.injection.datastore.DataStore;

public interface DataStoreFactory {

    DataStore createDataStore(Context context);
}
