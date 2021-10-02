package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminPage extends AppCompatActivity {

    private Button manageBook, manageMovie, manageTV, manageReviews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_25);
        setSupportActionBar(toolbar);

        manageBook = (Button)findViewById(R.id.btn_ManageBook);
        manageMovie = (Button)findViewById(R.id.btn_ManageMovies);
        manageTV = (Button)findViewById(R.id.btn_ManageTV);
        manageReviews = (Button)findViewById(R.id.btn_ManageReviews);


        manageBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openManageBook();

            }
        });

        manageMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openManageMovie();

            }
        });
        manageTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openManageTV();

            }
        });

        manageReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openManageReviews();

            }
        });
    }

    private void openManageTV() {
        Intent intent = new Intent(this,MmanageTV.class);
        startActivity(intent);
    }

    private void openManageMovie() {
        Intent intent1 = new Intent(this,ManageMovies.class);
        startActivity(intent1);
    }

    public void openManageBook(){
        Intent intent2 = new Intent(this,ManageBooks.class);
        startActivity(intent2);
    }

    public void openManageReviews(){
        Intent intent2 = new Intent(this,Admin_Home.class);
        startActivity(intent2);
    }
}