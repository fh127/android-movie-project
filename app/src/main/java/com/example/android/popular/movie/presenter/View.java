package com.example.android.popular.movie.presenter;

import android.app.Activity;
import android.content.Context;

/**
 * Created by fabianantoniohoyospulido on 7/30/17.
 */

public interface View {

    void onShowLoader(boolean show);

    Context getContextReference();

    Activity getActivityReference();

    void ShowErrorMessage(String message);
}
