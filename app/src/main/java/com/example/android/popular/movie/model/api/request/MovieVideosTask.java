package com.example.android.popular.movie.model.api.request;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.android.popular.movie.model.api.MovieApi;
import com.example.android.popular.movie.model.api.response.JsonVideo;
import com.example.android.popular.movie.model.entity.Movie;
import com.example.android.popular.movie.model.entity.Video;

import org.json.JSONException;

/**
 * Created by fabianantoniohoyospulido on 7/26/17.
 */

public class MovieVideosTask implements MovieRequest {
    private static final String VIDEOS_PREFIX_FORMAT = "/%1$s/videos";
    private String movieId;

    public MovieVideosTask(String movieId) {
        this.movieId = movieId;
    }

    public URL buildUrl() {
        String prefix = String.format(VIDEOS_PREFIX_FORMAT, movieId);
        return MovieApi.buildUrl(prefix);
    }

    private List<Video> parseJSON(String response) throws JSONException {
        List<Video> videos = new ArrayList<>();
        List<JsonVideo> jsonVideos = MovieApi.parseJSON(response, JsonVideo.class);
        for (JsonVideo json : jsonVideos) {
            videos.add(wrapVideo(json));
        }
        return videos;
    }

    private Video wrapVideo(JsonVideo jsonVideo) {
        return new Video.Builder()
                .id(jsonVideo.getId())
                .iso6391(jsonVideo.getIso6391())
                .iso31661(jsonVideo.getIso31661())
                .key(jsonVideo.getKey())
                .name(jsonVideo.getName())
                .site(jsonVideo.getSite())
                .size(jsonVideo.getSize())
                .type(jsonVideo.getType())
                .build();
    }

    @Override
    public List<Video> execute() {
        List<Video> videos = new ArrayList<>();
        try {
            String responseData = MovieApi.getResponseFromHttpUrl(buildUrl());
            videos = parseJSON(responseData);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return videos;
    }

}
