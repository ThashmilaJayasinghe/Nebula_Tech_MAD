package com.example.project;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Movie_View_K extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button myreviews;

    RecyclerView recyclerView;
    movieAdapter_K movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view_k);

        drawerLayout = findViewById(R.id.drower);
        navigationView = findViewById(R.id.nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_open);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myreviews = findViewById(R.id.btn_myreviews);

        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Movie");
        FirebaseRecyclerOptions<MovieModel_K> options =
                new FirebaseRecyclerOptions.Builder<MovieModel_K>()
                        .setQuery(readRef, MovieModel_K.class)
                        .build();

        movieAdapter = new movieAdapter_K(options);
        recyclerView.setAdapter(movieAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        myreviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Movie_View_K.this,MyReviewsMovies.class );
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        movieAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        movieAdapter.stopListening();
    }
}