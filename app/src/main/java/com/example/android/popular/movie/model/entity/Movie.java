package com.example.android.popular.movie.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.design.internal.ParcelableSparseArray;

/**
 * Created by fabianantoniohoyospulido on 7/24/17.
 */
@Entity(tableName = "movies")
public class Movie implements Parcelable, MovieParcelable {


    public Movie() {
    }

    @PrimaryKey
    private Integer id;
    private Double voteAverage;
    private String title;
    private String originalTitle;
    private String backdropPath;
    private String overview;
    private String releaseDate;
    private boolean prefered;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isPrefered() {
        return prefered;
    }

    public void setPrefered(boolean prefered) {
        this.prefered = prefered;
    }


    @Override
    public Movie getMovie() {
        return this;
    }

    @Override
    public Video getVideo() {
        return null;
    }

    @Override
    public Review getReview() {
        return null;
    }


    // Builder configuration

    public static final class Builder {
        private Integer id;
        private Double voteAverage;
        private String title;
        private String originalTitle;
        private String backdropPath;
        private String overview;
        private String releaseDate;

        public Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder voteAverage(Double val) {
            voteAverage = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder originalTitle(String val) {
            originalTitle = val;
            return this;
        }

        public Builder backdropPath(String val) {
            backdropPath = val;
            return this;
        }

        public Builder overview(String val) {
            overview = val;
            return this;
        }

        public Builder releaseDate(String val) {
            releaseDate = val;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }

    @Ignore
    private Movie(Builder builder) {
        setId(builder.id);
        setVoteAverage(builder.voteAverage);
        setTitle(builder.title);
        setOriginalTitle(builder.originalTitle);
        setBackdropPath(builder.backdropPath);
        setOverview(builder.overview);
        setReleaseDate(builder.releaseDate);
    }


    // Parcelable configuration

    @Ignore
    protected Movie(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.title = in.readString();
        this.originalTitle = in.readString();
        this.backdropPath = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.prefered = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.voteAverage);
        dest.writeString(this.title);
        dest.writeString(this.originalTitle);
        dest.writeString(this.backdropPath);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeByte(this.prefered ? (byte) 1 : (byte) 0);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
