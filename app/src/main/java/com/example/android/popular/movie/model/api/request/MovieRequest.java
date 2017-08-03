package com.example.android.popular.movie.model.api.request;

import com.example.android.popular.movie.model.entity.Movie;

import java.util.List;

/**
 * Created by fabianantoniohoyospulido on 7/25/17.
 */

public interface MovieRequest<T> {

    T execute();
}
