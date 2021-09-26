package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MyReviews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reviews);

        Toolbar toolbar = findViewById(R.id.mytoolbar);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);

        setSupportActionBar(toolbar);

    }

    /**Called to access my books reviews */
    public void openBooks(View view) {
        Intent intent = new Intent(this, MyReviewsBooks.class);
        startActivity(intent);
    }

    /**Called to access my movies reviews */
    public void openMovies(View view) {
        Intent intent = new Intent(this, MyReviewsMovies.class);
        startActivity(intent);

    }

    /**Called to access my tvshows reviews */
    public void openTvshows(View view) {
        Intent intent = new Intent(this, MyReviewsTVShows.class);
        startActivity(intent);

    }

}