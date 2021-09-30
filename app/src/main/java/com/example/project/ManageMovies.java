package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ManageMovies extends AppCompatActivity {

    Button updateMovie, addMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_movies);

        Toolbar toolbar = findViewById(R.id.toolbar6);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        setSupportActionBar(toolbar);

        updateMovie = (Button)findViewById(R.id.btn_UpdateMovie);

        updateMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openeupdateMovie();

            }
        });

        addMovie = (Button)findViewById(R.id.btn_AddMovie);
        addMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openeaddMovie();

            }
        });
    }

    private void openeupdateMovie() {
        Intent intent = new Intent(this,ViewMovies.class);
        startActivity(intent);
    }

    private void openeaddMovie() {
        Intent intent = new Intent(this,AddMovie.class);
        startActivity(intent);
    }
}