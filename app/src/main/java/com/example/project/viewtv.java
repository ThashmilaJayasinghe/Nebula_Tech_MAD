package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class viewtv extends AppCompatActivity {
    RecyclerView recview;
    myadapterTVshow adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtv);

        Toolbar toolbar = findViewById(R.id.toolbar4);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        setSupportActionBar(toolbar);

        recview = (RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<modelTV> options =
                new FirebaseRecyclerOptions.Builder<modelTV>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("TVShow"), modelTV.class)
                        .build();
        adapter = new myadapterTVshow(options);
        recview.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        recview.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}