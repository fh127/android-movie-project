package com.example.android.popular.movie.presenter;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class QueryOption {

    public static final int QUERY_ALL = 1;
    public static final int QUERY_FIND_ID=3;
    public static final int QUERY_FIND_BY_ID = 2;

    @IntDef({QUERY_ALL, QUERY_FIND_BY_ID, QUERY_FIND_ID })
    @Retention(RetentionPolicy.SOURCE)
    public @interface QUERYOPTION {
    }
}