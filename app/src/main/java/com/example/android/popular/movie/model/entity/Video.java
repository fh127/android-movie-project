package com.example.android.popular.movie.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.popular.movie.presenter.moviedetail.MovieDetailView;

/**
 * Created by fabianantoniohoyospulido on 7/27/17.
 */

public class Video implements MovieParcelable{
    private String id;
    private String iso6391;
    private String iso31661;
    private String key;
    private String name;
    private String site;
    private Integer size;
    private String type;

    private Video(Builder builder) {
        setId(builder.id);
        setIso6391(builder.iso6391);
        setIso31661(builder.iso31661);
        setKey(builder.key);
        setName(builder.name);
        setSite(builder.site);
        setSize(builder.size);
        setType(builder.type);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.iso6391);
        dest.writeString(this.iso31661);
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.site);
        dest.writeValue(this.size);
        dest.writeString(this.type);
    }

    public Video() {
    }

    protected Video(Parcel in) {
        this.id = in.readString();
        this.iso6391 = in.readString();
        this.iso31661 = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.size = (Integer) in.readValue(Integer.class.getClassLoader());
        this.type = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @Override
    public Movie getMovie() {
        return null;
    }

    @Override
    public Video getVideo() {
        return this;
    }

    @Override
    public Review getReview() {
        return null;
    }


    public static final class Builder {
        private String id;
        private String iso6391;
        private String iso31661;
        private String key;
        private String name;
        private String site;
        private Integer size;
        private String type;

        public Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder iso6391(String val) {
            iso6391 = val;
            return this;
        }

        public Builder iso31661(String val) {
            iso31661 = val;
            return this;
        }

        public Builder key(String val) {
            key = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder site(String val) {
            site = val;
            return this;
        }

        public Builder size(Integer val) {
            size = val;
            return this;
        }

        public Builder type(String val) {
            type = val;
            return this;
        }

        public Video build() {
            return new Video(this);
        }
    }
}
