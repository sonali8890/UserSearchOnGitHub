package com.sonali.githubusersearchapp;

import android.app.Application;

/**
 * Created by Sonali
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static MyApplication getApplicationInstance(){
        return mInstance;
    }

}
