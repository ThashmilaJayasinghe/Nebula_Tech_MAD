package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_Book_View_K extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;

    RecyclerView recyclerView;
    Admin_bookAdaptar_K bookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_book_view_k);

        drawerLayout = (DrawerLayout) findViewById(R.id.drower);
        navigationView = (NavigationView) findViewById(R.id.nav);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_open,R.string.nav_open);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_myProfile:
                        Toast.makeText(getApplicationContext(), "My profile", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_home:
                        Log.i("Home","home");
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
//                         Intent intent = new Intent(String.valueOf(MainActivity.class));
//                         startActivity(intent);
                        break;

                    case R.id.nav_lists:
                        Toast.makeText(getApplicationContext(), "List", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_myreviews:
                        Toast.makeText(getApplicationContext(), "My Reviews", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_books:
                        Toast.makeText(getApplicationContext(), "Books", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_tvshows:
                        Toast.makeText(getApplicationContext(), "TV Show", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_movies:
                        Toast.makeText(getApplicationContext(), "Movies", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_logout:
                        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
                return true;
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.rv);

        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Book");
        FirebaseRecyclerOptions<BookModel_K> options =
                new FirebaseRecyclerOptions.Builder<BookModel_K>()
                        .setQuery(readRef, BookModel_K.class)
                        .build();

        bookAdapter = new Admin_bookAdaptar_K(options);
        recyclerView.setAdapter(bookAdapter);

        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }
    @Override
    protected void onStart() {
        super.onStart();
        bookAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bookAdapter.stopListening();
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

        DatabaseReference readRef=FirebaseDatabase.getInstance().getReference().child("Book");
        FirebaseRecyclerOptions<BookModel_K> options =
                new FirebaseRecyclerOptions.Builder<BookModel_K>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Book").orderByChild("title").startAt(s).endAt(s+"\uf8ff"), BookModel_K.class)
                        .build();

        bookAdapter = new Admin_bookAdaptar_K(options);
        bookAdapter.startListening();
        recyclerView.setAdapter(bookAdapter);

    }

}