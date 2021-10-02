package com.example.project;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_TVShow_View_K extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    RecyclerView recyclerView;
    Admin_tvshowAdapter_K tvshowAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tvshow_view_k);

        drawerLayout = findViewById(R.id.drower);
        navigationView = findViewById(R.id.nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_open,R.string.nav_open);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("TVShow");
        FirebaseRecyclerOptions<TvshowModel_K> options =
                new FirebaseRecyclerOptions.Builder<TvshowModel_K>()
                        .setQuery(readRef, TvshowModel_K.class)
                        .build();

        tvshowAdapter = new Admin_tvshowAdapter_K(options);
        recyclerView.setAdapter(tvshowAdapter);

        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

    }
    @Override
    protected void onStart() {
        super.onStart();
        tvshowAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tvshowAdapter.stopListening();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu,menu);

        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s) {

        DatabaseReference readRef=FirebaseDatabase.getInstance().getReference().child("TVShow");
        FirebaseRecyclerOptions<TvshowModel_K> options =
                new FirebaseRecyclerOptions.Builder<TvshowModel_K>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("TVShow").orderByChild("title").startAt(s).endAt(s+"\uf8ff"), TvshowModel_K.class)
                        .build();

        tvshowAdapter = new Admin_tvshowAdapter_K(options);
        tvshowAdapter.startListening();
        recyclerView.setAdapter(tvshowAdapter);

    }

}