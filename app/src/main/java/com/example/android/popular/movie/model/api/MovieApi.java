package com.example.android.popular.movie.model.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import com.example.android.popular.movie.R;
import com.example.android.popular.movie.app.MovieApplication;

import android.net.Uri;
import android.util.Log;

/**
 * Created by fabian.hoyos on 1/07/2017.
 */

public class MovieApi {

    private static final String TAG = MovieApi.class.getSimpleName();
    private static final String TAG_TITLE = "URI ";
    private static final String DELIMIT= "\\A";
    private static final String SERVER_BASE_URL = "https://api.themoviedb.org/3/movie";
    private static final String POPULAR_PREFIX = "/popular";
    private static final String TOP_RATED_PREFIX = "/top_rated";
    private final static String QUERY_PARAM = "api_key";
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p";
    private static final String IMAGE_SIZE = "/w342";
    private static final int TIME_OUT = 30000;

    /**
     *build url api
     * @param sort
     * @return
     */
    public static URL buildUrl(boolean sort) {
        String apiKey = MovieApplication.getInstance().getString(R.string.api_key);
        Uri builtUri = Uri.parse(getServerBaseUrl(sort)).buildUpon().appendQueryParameter(QUERY_PARAM, apiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, TAG_TITLE + url);

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(TIME_OUT);
        urlConnection.setReadTimeout(TIME_OUT);
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter(DELIMIT);
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     *  get base url
     * @param sort
     * @return
     */
    private static String getServerBaseUrl(boolean sort) {
        return SERVER_BASE_URL.concat(sort ? TOP_RATED_PREFIX : POPULAR_PREFIX);
    }

    /**
     * get image base url
     * @param imageUrl
     * @return
     */
    public static String getImageUri(String imageUrl) {
        return IMAGE_BASE_URL.concat(IMAGE_SIZE).concat(imageUrl);
    }

}
