package com.example.android.popular.movie.model.api.request;

import android.util.Log;
import com.example.android.popular.movie.model.api.MovieApi;
import com.example.android.popular.movie.model.api.response.JsonMovie;
import com.example.android.popular.movie.model.entity.Movie;
import org.json.JSONException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabianantoniohoyospulido on 7/26/17.
 */

public class MovieSortListTask implements MovieRequest {
    private static final String POPULAR_PREFIX = "/popular";
    private static final String TOP_RATED_PREFIX = "/top_rated";
    private boolean sort;

    public MovieSortListTask(boolean sort) {
        this.sort = sort;
    }

    public URL buildUrl() {
        String prefix = sort ? TOP_RATED_PREFIX : POPULAR_PREFIX;
        return MovieApi.buildUrl(prefix);
    }

    @Override
    public List<Movie> execute() {
        List<Movie> movies = new ArrayList<>();
        try {
            String responseData = MovieApi.getResponseFromHttpUrl(buildUrl());
            movies = parseJSON(responseData);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return movies;
    }

    private List<Movie> parseJSON(String response) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        List<JsonMovie> jsonMovies = MovieApi.parseJSON(response, JsonMovie.class);
        for (JsonMovie json : jsonMovies) {
            movies.add(wrapMovie(json));
        }
        return movies;
    }

    private Movie wrapMovie(JsonMovie jsonMovie) {
        return new Movie.Builder().
                id(jsonMovie.getId())
                .title(jsonMovie.getTitle())
                .originalTitle(jsonMovie.getOriginalTitle())
                .backdropPath(jsonMovie.getBackdropPath())
                .overview(jsonMovie.getOverview())
                .releaseDate(jsonMovie.getReleaseDate())
                .voteAverage(jsonMovie.getVoteAverage())
                .build();
    }
}
