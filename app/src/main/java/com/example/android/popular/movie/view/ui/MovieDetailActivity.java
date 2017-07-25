package com.example.android.popular.movie.view.ui;

import com.example.android.popular.movie.R;
import com.example.android.popular.movie.model.api.MovieApi;
import com.example.android.popular.movie.model.entity.Movie;
import com.example.android.popular.movie.utils.Constanst;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView image;
    private TextView description;
    private TextView releaseDate;
    private TextView userRating;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        title = (TextView) findViewById(R.id.title_view);
        image = (ImageView) findViewById(R.id.image_view_poster);
        description = (TextView) findViewById(R.id.text_view_overview);
        releaseDate = (TextView) findViewById(R.id.text_view_release_date);
        userRating = (TextView) findViewById(R.id.text_view_vote_average);
        loadMovieDetails();
    }

    private void loadMovieDetails() {
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            Movie movie = bundle.getParcelable(Constanst.MOVIE_EXTRA);
            assert movie != null;
            getSupportActionBar().setTitle(movie.getTitle());
            title.setText(movie.getOriginalTitle());
            title.setSelected(true);
            description.setText(movie.getOverview());
            releaseDate.setText(movie.getReleaseDate());
            userRating.setText(String.valueOf(movie.getVoteAverage()));
            Picasso.with(this).load(MovieApi.getImageUri(movie.getBackdropPath())).into(image);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
