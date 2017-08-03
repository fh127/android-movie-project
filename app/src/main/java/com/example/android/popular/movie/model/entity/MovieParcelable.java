package com.example.android.popular.movie.model.entity;

import android.os.Parcelable;

/**
 * Created by fabianantoniohoyospulido on 7/30/17.
 */

public interface MovieParcelable extends Parcelable {

    Movie getMovie();

    Video getVideo();

    Review getReview();

}
