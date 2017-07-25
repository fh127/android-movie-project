package com.example.android.popular.movie.model.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.android.popular.movie.model.entity.JsonMovie;
import com.example.android.popular.movie.model.entity.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MovieJsonResponse {

    public final static String RESULTS_PARAMETER = "results";

    /**
     *
     * @param response
     * @return
     */
    public static List<Movie> parseJSON(String response) {
        List<Movie> movies = new ArrayList<>();
        try {
            JSONArray results = new JSONObject(response).optJSONArray(RESULTS_PARAMETER);
            if (results != null) {
                Gson gson = new GsonBuilder().create();
                int size = results.length();
                for (int i = 0; i < size; i++) {
                    String jsonMovie = results.getString(i);
                    JsonMovie movie = gson.fromJson(jsonMovie, JsonMovie.class);
                    movies.add(wrapMovie(movie));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    private static Movie wrapMovie(JsonMovie jsonMovie){

       return new Movie.Builder()
                .id(jsonMovie.getId())
                .title(jsonMovie.getTitle())
                .originalTitle(jsonMovie.getOriginalTitle())
                .backdropPath(jsonMovie.getBackdropPath())
                .overview(jsonMovie.getOverview())
                .releaseDate(jsonMovie.getReleaseDate())
                .build();

    }


}