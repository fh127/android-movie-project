package com.example.android.popular.movie.presenter.moviedetail;

import com.example.android.popular.movie.model.entity.Review;
import com.example.android.popular.movie.model.entity.Video;
import com.example.android.popular.movie.presenter.View;

import java.util.List;

/**
 * Created by fabianantoniohoyospulido on 7/29/17.
 */

public interface MovieDetailView extends View {

    void onVideosLoaded(List<Video> videos);

    void onReviewsLoaded(List<Review> reviews);

    void updateFavoriteIcon(boolean movieAdded);

}
