package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class MostPopular extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_popular);

        Toolbar toolbar = findViewById(R.id.poptoolbar);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);

        setSupportActionBar(toolbar);
    }
}