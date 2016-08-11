package com.codemate.blogreader;

import android.app.Application;

import com.codemate.blogreader.injection.AppComponent;

/**
 * Created by ironman on 04/08/16.
 */
public class MVPApplication extends Application {
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = AppComponent.Initializer.init(this);
    }

    public static AppComponent component() {
        return appComponent;
    }
}
