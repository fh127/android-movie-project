package com.example.android.popular.movie.utils;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

/**
 * Created by fabian.hoyos on 2/07/2017.
 */

public class DeviceUtils {

    public static boolean isVerticalSscreenOrientation(int orientation) {
        return orientation == ORIENTATION_PORTRAIT;
    }
}
