package com.hevets.simpletodo;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Stetho is used to view SQLite in the browser
        Stetho.initializeWithDefaults(this);

        // This instantiates DBFlow
        FlowManager.init(new FlowConfig.Builder(this).build());
        // FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);
    }
}
