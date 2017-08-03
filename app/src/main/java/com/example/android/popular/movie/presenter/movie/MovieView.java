package com.example.android.popular.movie.presenter.movie;

import java.util.List;

import com.example.android.popular.movie.model.entity.Movie;
import com.example.android.popular.movie.presenter.View;

import android.content.Context;

/**
 * Created by fabian.hoyos on 1/07/2017.
 */

public interface MovieView extends View {

    void onMoviesLoaded(List<Movie> movies);

}
