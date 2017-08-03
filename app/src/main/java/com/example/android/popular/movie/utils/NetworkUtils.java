package com.example.android.popular.movie.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.android.popular.movie.app.MovieApplication;

/**
 * Created by fabian.hoyos on 1/07/2017.
 */

public class NetworkUtils {

    public static boolean isNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MovieApplication.getInstance().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
