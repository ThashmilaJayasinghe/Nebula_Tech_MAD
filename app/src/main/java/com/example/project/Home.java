package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    private Button btn_books;
    private Button btn_movies;
    private Button btn_tvshows;
    private Button btn_myprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_books = (Button)findViewById(R.id.btn_books);
        btn_movies = (Button)findViewById(R.id.btn_movies);
        btn_tvshows = (Button)findViewById(R.id.btn_tvshows);
        btn_myprofile = findViewById(R.id.btn_myprofile);

        btn_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInsideActivity2();
            }
        });
        btn_movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInsideActivity3();
            }
        });
        btn_tvshows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInsideActivity4();
            }
        });

        btn_myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInsideActivity5();
            }
        });

    }

    public void openInsideActivity2(){
        Intent intent = new Intent(this,Book_View_K.class);
        startActivity(intent);
    }
    public void openInsideActivity3(){
        Intent intent = new Intent(this,Movie_View_K.class);
        startActivity(intent);
    }
    public void openInsideActivity4(){
        Intent intent = new Intent(this,TVShow_View_K.class);
        startActivity(intent);
    }
    public void openInsideActivity5(){
        Intent intent = new Intent(this,profileview.class);
        startActivity(intent);
    }

}



