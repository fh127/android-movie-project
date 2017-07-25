package com.example.android.popular.movie.presenter;

import java.util.List;

import com.example.android.popular.movie.model.entity.Movie;

/**
 * Created by fabian.hoyos on 1/07/2017.
 */

public interface MovieInteractor {

    interface OnResponseListener {

        void onResponseError();

        void onResponseSuccess(List<Movie> jsonMovieList);
    }

    void getMovies(boolean sort, OnResponseListener listener);
}
