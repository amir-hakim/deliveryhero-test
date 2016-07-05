package com.test.deliveryhero.view.base;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Base application for the app,
 * Used to access the application context within the application classes.
 */
public class BaseApplication extends Application
{
    private static Context context = null; // application Context
    private static BaseApplication application = null;

    @Override
    public void onCreate(){
        super.onCreate();

        context = getApplicationContext();

        application = this;
    }

    public static Context getContext(){
        return context;
    }

    /**
     * Simple network connection check.
     */
    public boolean isNetworkConnected() {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            return false;
        }

        return true;
    }

    static public BaseApplication getApplication(){
        return application;
    }
}
