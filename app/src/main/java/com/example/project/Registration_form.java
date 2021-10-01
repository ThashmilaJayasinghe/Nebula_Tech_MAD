package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Registration_form extends AppCompatActivity implements View.OnClickListener {

    private TextView registeruser;
    private EditText editTextTextPassword, editTextTextEmailAddress, editTextTextPersonName3;
    private Button Btn;




    private FirebaseAuth mAuth;//test 8

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        mAuth = FirebaseAuth.getInstance();

        registeruser = (Button) findViewById(R.id.button6);
        registeruser.setOnClickListener(this);

        editTextTextPersonName3 = (EditText) findViewById(R.id.UserNameDisplay);
        editTextTextEmailAddress = (EditText) findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = (EditText) findViewById(R.id.editTextTextPassword);





    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.button6:
                registeruser();
                break;
        }

    }

    private void registeruser() {

        // Take the value of two edit texts in Strings
        String email, password, name, male, female;
        name = editTextTextPersonName3.getText().toString().trim();
        email = editTextTextEmailAddress.getText().toString().trim();
        password = editTextTextPassword.getText().toString().trim();



        if (name.isEmpty()) {
            editTextTextPersonName3.setError("Please enter name!!");
            editTextTextPersonName3.requestFocus();
            return;

        }

        if (email.isEmpty()) {
            editTextTextEmailAddress.setError("Please enter email!!");
            editTextTextEmailAddress.requestFocus();
            return;

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextTextEmailAddress.setError("Please enter Valid email!!");
            editTextTextEmailAddress.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextTextPassword.setError("Please enter password!!");
            editTextTextPassword.requestFocus();
            return;

        }

        if (password.length() < 6) {
            editTextTextPassword.setError("MIN Password length should be 6 characters");
            editTextTextPassword.requestFocus();
            return;

        }




        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userReg userReg = new userReg(name, password, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(userReg).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(Registration_form.this, "user registration succesfull", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(Registration_form.this, "user registration Unsuccesfull", Toast.LENGTH_LONG).show();
                                    }
                                }

                            });


                        }else {
                            Toast.makeText(Registration_form.this, "user registration Unsuccesfull", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}