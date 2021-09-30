package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_MovieReview extends AppCompatActivity {

    String postkey;
    RecyclerView recyclerView;
    reviewAdapter_K reviewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_movie_review);

        recyclerView = (RecyclerView)findViewById(R.id.rv_review);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //.child("Movie").child(postkey)

        postkey=getIntent().getStringExtra("postkey");

        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Movie").child(postkey).child("reviews");
        FirebaseRecyclerOptions<ReviewModel_K> options =
                new FirebaseRecyclerOptions.Builder<ReviewModel_K>()
                        .setQuery(readRef, ReviewModel_K.class)
                        .build();

        reviewAdapter = new reviewAdapter_K(options);
        recyclerView.setAdapter(reviewAdapter);


    }
    @Override
    protected void onStart() {
        super.onStart();
        reviewAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        reviewAdapter.stopListening();
    }
}