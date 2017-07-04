package com.example.android.popular.movie.presenter;

import java.net.URL;
import java.util.List;

import com.example.android.popular.movie.model.api.MovieApi;
import com.example.android.popular.movie.model.api.MovieJsonResponse;
import com.example.android.popular.movie.model.entity.Movie;

/**
 * Created by fabian.hoyos on 1/07/2017.
 */

public class MovieInteractorImpl extends Thread implements MovieInteractor {

    private boolean sort;
    private OnResponseListener listener;

    @Override
    public void getMovies(boolean sort, OnResponseListener listener) {
        this.sort = sort;
        this.listener = listener;
        start();
    }

    @Override
    public void run() {
        super.run();
        URL movieRequestUrl = MovieApi.buildUrl(sort);
        try {
            String responseData = MovieApi.getResponseFromHttpUrl(movieRequestUrl);
            List<Movie> movies = MovieJsonResponse.parseJSON(responseData);
            listener.onResponseSuccess(movies);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onResponseError();
        }

    }
}
