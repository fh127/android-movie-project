package com.example.android.popular.movie.model.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.android.popular.movie.R;
import com.example.android.popular.movie.app.MovieApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.net.Uri;
import android.util.Log;

/**
 * Created by fabian.hoyos on 1/07/2017.
 */

public class MovieApi {

    private static final String TAG = MovieApi.class.getSimpleName();
    private static final String TAG_TITLE = "URI ";
    private static final String DELIMIT = "\\A";
    private static final String SERVER_BASE_URL = "https://api.themoviedb.org/3/movie";
    private final static String QUERY_PARAM = "api_key";
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p";
    private static final String IMAGE_SIZE = "/w342";
    public final static String RESULTS_PARAMETER = "results";
    private static final int TIME_OUT = 10000;

    public static URL buildUrl(String prefix) {
        Uri builtUri = getServerBaseUrl(prefix);
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
        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(TIME_OUT);
            urlConnection.setReadTimeout(TIME_OUT);
            if (handleResponseCode(urlConnection.getResponseCode())) {
                InputStream in = urlConnection.getInputStream();
                Scanner scanner = new Scanner(in);
                scanner.useDelimiter(DELIMIT);
                boolean hasInput = scanner.hasNext();
                return hasInput ? scanner.next() : null;
            }
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static boolean handleResponseCode(int responseCode) {
        switch (responseCode) {
            case HttpURLConnection.HTTP_OK:
                Log.d(TAG, "handleResponseCode: OK");
                return true;
            case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
                Log.e(TAG, "handleResponseCode: **gateway timeout**");
                break;
            case HttpURLConnection.HTTP_UNAVAILABLE:
                Log.e(TAG, "handleResponseCode:*unavailable**");
                break;
            default:
                Log.e(TAG, "handleResponseCode **unknown response code**.");
                break;
        }
        return false;
    }

    /**
     * get base url
     *
     * @param prefix
     * @return
     */
    private static Uri getServerBaseUrl(String prefix) {
        String apiKey = MovieApplication.getInstance().getString(R.string.api_key);
        return Uri.parse(SERVER_BASE_URL.concat(prefix)).buildUpon().appendQueryParameter(QUERY_PARAM, apiKey)
                .build();
    }

    /**
     * get image base url
     *
     * @param imageUrl
     * @return
     */
    public static String getImageUri(String imageUrl) {
        return IMAGE_BASE_URL.concat(IMAGE_SIZE).concat(imageUrl);
    }

    private static JSONArray getJsonArray(String response) {
        JSONArray results = new JSONArray();
        try {
            results = new JSONObject(response).optJSONArray(RESULTS_PARAMETER);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
    }


    public static <I> List<I> parseJSON(String response, Class<I> classOfI) throws JSONException {
        List<I> array = new ArrayList<>();
        JSONArray results = getJsonArray(response);
        if (results.length() > 0) {
            int size = results.length();
            Gson gson = new GsonBuilder().create();
            for (int i = 0; i < size; i++) {
                String json = results.getString(i);
                array.add(gson.fromJson(json, classOfI));
            }
        }
        return array;
    }

}
