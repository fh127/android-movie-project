package com.example.android.popular.movie.model.api.request;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.android.popular.movie.model.api.MovieApi;
import com.example.android.popular.movie.model.api.response.JsonReview;
import com.example.android.popular.movie.model.entity.Movie;
import com.example.android.popular.movie.model.entity.Review;

import org.json.JSONException;

/**
 * Created by fabianantoniohoyospulido on 7/26/17.
 */

public class MovieReviewsTask implements MovieRequest {
    private static final String REVIEWS_PREFIX_FORMAT = "/%1$s/reviews";
    private String movieId;

    public MovieReviewsTask(String movieId) {
        this.movieId = movieId;
    }

    public URL buildUrl() {
        String prefix = String.format(REVIEWS_PREFIX_FORMAT, movieId);
        return MovieApi.buildUrl(prefix);
    }

    private List<Review> parseJSON(String response) throws JSONException {
        List<Review> reviews = new ArrayList<>();
        List<JsonReview> jsonVideos = MovieApi.parseJSON(response, JsonReview.class);
        for (JsonReview json : jsonVideos) {
            reviews.add(wrapReview(json));
        }
        return reviews;
    }

    private Review wrapReview(JsonReview jsonReview) {
        return new Review.Builder()
                .id(jsonReview.getId())
                .author(jsonReview.getAuthor())
                .content(jsonReview.getContent())
                .url(jsonReview.getUrl())
                .build();
    }

    @Override
    public List<Review> execute() {
        List<Review> reviews = new ArrayList<>();
        try {
            String responseData = MovieApi.getResponseFromHttpUrl(buildUrl());
            reviews = parseJSON(responseData);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return reviews;
    }

}
