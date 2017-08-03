package com.example.android.popular.movie.model.api.request;

import android.support.annotation.Nullable;

public class MovieRequestFactory {

    public static final String MOVIE_SORT_LIST_ACTION = "movie_sort_list_action";
    public static final String VIDEOS_LIST_ACTION = "video_list_action";
    public static final String REVIEWS_LIST_ACTION = "review_list_action";

    public static @Nullable MovieRequest getRequest(String action, MovieRequestParameter parameter) {
        if (MOVIE_SORT_LIST_ACTION.equals(action)) {
            return new MovieSortListTask(parameter.isSort());
        } else if (VIDEOS_LIST_ACTION.equals(action)) {
            return new MovieVideosTask(String.valueOf(parameter.getMovieId()));
        } else if (REVIEWS_LIST_ACTION.equals(action)) {
            return new MovieReviewsTask(String.valueOf(parameter.getMovieId()));
        }
        return null;
    }

    public static boolean isMovieAction(String action) {
       return MOVIE_SORT_LIST_ACTION.equals(action);
    }

    public static boolean isVideoAction(String action) {
        return VIDEOS_LIST_ACTION.equals(action);
    }

    public static boolean isReviewAction(String action) {
        return REVIEWS_LIST_ACTION.equals(action);
    }
}
