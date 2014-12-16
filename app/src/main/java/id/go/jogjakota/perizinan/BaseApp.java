package id.go.jogjakota.perizinan;

import android.app.Application;

import net.danlew.android.joda.ResourceZoneInfoProvider;

import id.go.jogjakota.perizinan.data.Dummies;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Dummies.init();
        ResourceZoneInfoProvider.init(this);
    }
}
