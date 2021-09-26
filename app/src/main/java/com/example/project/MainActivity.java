package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button register;
    private EditText et_Username;
    private EditText et_Password;
    private Button signIn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.btn_Register);
        signIn = findViewById(R.id.btn_signin);
        et_Username = findViewById(R.id.et_Username);
        et_Password = findViewById(R.id.et_password);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

    }

    /**Called to access register */
    public void register(View view) {
        Intent intent = new Intent(this, MyReviews.class);
        startActivity(intent);

    }

    /**Called to sign in */
    public void userLogin(View view) {
        String username = et_Username.getText().toString().trim();
        String password = et_Password.getText().toString().trim();

        if(username.isEmpty()) {
            et_Username.setError("Username is required!");
            et_Username.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            et_Username.setError("Please enter a valid username!");
            et_Username.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            et_Username.setError("Password is required!");
            et_Username.requestFocus();
            return;
        }
        if(password.length() < 6) {
            et_Password.setError("Min Password length is 6 characters!");
            et_Username.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    //redirecting to Home
                    startActivity(new Intent(MainActivity.this, Profile.class));
                }else {
                    Toast.makeText(MainActivity.this, "Failed to login! Please check credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}