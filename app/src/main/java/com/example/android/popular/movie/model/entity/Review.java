package com.example.android.popular.movie.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fabianantoniohoyospulido on 7/27/17.
 */

public class Review implements MovieParcelable {

    private String id;
    private String author;
    private String content;
    private String url;

    private Review(Builder builder) {
        setId(builder.id);
        setAuthor(builder.author);
        setContent(builder.content);
        setUrl(builder.url);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.author);
        dest.writeString(this.content);
        dest.writeString(this.url);
    }

    public Review() {
    }

    protected Review(Parcel in) {
        this.id = in.readString();
        this.author = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public Movie getMovie() {
        return null;
    }

    @Override
    public Video getVideo() {
        return null;
    }

    @Override
    public Review getReview() {
        return this;
    }


    public static final class Builder {
        private String id;
        private String author;
        private String content;
        private String url;

        public Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder author(String val) {
            author = val;
            return this;
        }

        public Builder content(String val) {
            content = val;
            return this;
        }

        public Builder url(String val) {
            url = val;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }
}
