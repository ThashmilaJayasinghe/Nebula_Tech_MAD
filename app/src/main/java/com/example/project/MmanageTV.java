package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MmanageTV extends AppCompatActivity {

    Button updateTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmanage_tv);

        Toolbar toolbar = findViewById(R.id.toolbar6);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        setSupportActionBar(toolbar);

        updateTV = (Button)findViewById(R.id.btn_UpdateTV);

        updateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openeupdateTV();

            }
        });
    }

    private void openeupdateTV() {
        Intent intent = new Intent(this,viewtv.class);
        startActivity(intent);
    }
}