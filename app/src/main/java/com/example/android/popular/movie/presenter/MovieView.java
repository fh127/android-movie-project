package com.example.android.popular.movie.presenter;

import android.content.Context;

import com.example.android.popular.movie.model.entity.Movie;

import java.util.List;

/**
 * Created by fabian.hoyos on 1/07/2017.
 */

public interface MovieView {

    void onShowLoader(boolean show);

    void onMoviesLoaded(List<Movie> movies);

    void ShowErrorMessage(String message);

    Context getContextReference();

}
