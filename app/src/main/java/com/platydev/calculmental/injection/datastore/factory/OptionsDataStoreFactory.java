package com.platydev.calculmental.injection.datastore.factory;

import android.content.Context;

import com.platydev.calculmental.injection.datastore.OptionsDataStore;
import com.platydev.calculmental.injection.datastore.DataStore;

public class OptionsDataStoreFactory implements DataStoreFactory {

    private static OptionsDataStoreFactory instance;

    private OptionsDataStoreFactory() {

    }

    public static OptionsDataStoreFactory getInstance() {
        if (instance == null) {
            synchronized (OptionsDataStoreFactory.class) {
                if (instance == null) {
                    instance = new OptionsDataStoreFactory();
                }
            }
        }
        return instance;
    }

    @Override
    public DataStore createDataStore(Context context) {
        return OptionsDataStore.getInstance(context);
    }
}
