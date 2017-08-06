package com.example.android.popular.movie.view.ui;

import com.example.android.popular.movie.R;
import com.example.android.popular.movie.utils.Constants;
import com.example.android.popular.movie.utils.MovieUtils;
import com.example.android.popular.movie.utils.PreferenceUtils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

public class MovieActivity extends AppCompatActivity {
    private MovieFragment fragmentListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentListener = (MovieFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        String menuTitleOptionSelected = PreferenceUtils.getInstance().getStringValue(Constants.MENU_ITEM_OPTION);
        if (!TextUtils.isEmpty(menuTitleOptionSelected)) {
            menu.findItem(R.id.action_movies_message).setTitle(menuTitleOptionSelected);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int option = MovieUtils.movieMenuUiOption.get(item.getItemId());
        ActionMenuItemView menuItem = (ActionMenuItemView) findViewById(R.id.action_movies_message);
        menuItem.setTitle(item.getTitle());
        PreferenceUtils.getInstance().setStringValue(Constants.MENU_ITEM_OPTION, item.getTitle().toString());
        PreferenceUtils.getInstance().setIntValue(Constants.MENU_ITEM_ID, option);
        fragmentListener.sortMovies(option);
        return true;
    }

}
