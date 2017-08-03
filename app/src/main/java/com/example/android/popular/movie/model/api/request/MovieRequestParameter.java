package com.example.android.popular.movie.model.api.request;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fabianantoniohoyospulido on 7/26/17.
 */

public class MovieRequestParameter implements Parcelable {

    private boolean sort;
    private int movieId;

    private MovieRequestParameter(Builder builder) {
        sort = builder.sort;
        movieId = builder.movieId;
    }

    public boolean isSort() {
        return sort;
    }

    public int getMovieId() {
        return movieId;
    }

    public static final class Builder {
        private boolean sort;
        private int movieId;

        public Builder() {
        }

        public Builder sort(boolean val) {
            sort = val;
            return this;
        }

        public Builder movieId(int val) {
            movieId = val;
            return this;
        }

        public MovieRequestParameter build() {
            return new MovieRequestParameter(this);
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.sort ? (byte) 1 : (byte) 0);
        dest.writeInt(this.movieId);
    }

    protected MovieRequestParameter(Parcel in) {
        this.sort = in.readByte() != 0;
        this.movieId = in.readInt();
    }

    public static final Parcelable.Creator<MovieRequestParameter> CREATOR = new Parcelable.Creator<MovieRequestParameter>() {
        @Override
        public MovieRequestParameter createFromParcel(Parcel source) {
            return new MovieRequestParameter(source);
        }

        @Override
        public MovieRequestParameter[] newArray(int size) {
            return new MovieRequestParameter[size];
        }
    };
}
