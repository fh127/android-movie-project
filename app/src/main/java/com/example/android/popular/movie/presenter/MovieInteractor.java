package com.example.android.popular.movie.presenter;

import com.example.android.popular.movie.model.entity.Movie;

import java.util.List;

/**
 * Created by fabian.hoyos on 1/07/2017.
 */

public interface MovieInteractor {

    interface OnResponseListener {

        void onResponseError();

        void onResponseSuccess(List<Movie> movieList);
    }

    void getMovies(boolean sort, OnResponseListener listener);
}
