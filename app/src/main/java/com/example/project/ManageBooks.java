package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManageBooks extends AppCompatActivity {

    Button updateBook, addbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_books);
        Toolbar toolbar = findViewById(R.id.toolbar6);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        setSupportActionBar(toolbar);


        updateBook = (Button)findViewById(R.id.btn_UpdateBook);
        updateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openeupdateBook();

            }
        });

        addbook = (Button)findViewById(R.id.btn_AddBook);
        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openeaddbook();

            }
        });

    }

    private void openeupdateBook() {
        Intent intent = new Intent(this,ViewBooks.class);
        startActivity(intent);
    }

    private void openeaddbook() {
        Intent intent = new Intent(this,AddBook.class);
        startActivity(intent);
    }


}