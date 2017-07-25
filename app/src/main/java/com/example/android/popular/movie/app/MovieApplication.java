package com.example.android.popular.movie.app;

import android.app.Application;
import android.content.Context;

import com.example.android.popular.movie.utils.PreferenceUtils;

/**
 * Created by fabian.hoyos on 1/07/2017.
 *
 */

public class MovieApplication extends Application {

    private Context context;

    private static MovieApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        PreferenceUtils.getInstance().init(getApplicationContext());
    }

    public Context getContext() {
        return context;
    }

    public static MovieApplication getInstance() {
        return instance;
    }
}
