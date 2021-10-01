package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AdminRegistration extends AppCompatActivity implements View.OnClickListener
 {

    private TextView registeruser;
    private EditText editTextTextPassword, editTextTextEmailAddress, etAdminName;
    private Button Btn;




    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);

        mAuth = FirebaseAuth.getInstance();

        registeruser = (Button) findViewById(R.id.btnRegisterAdmin);
        registeruser.setOnClickListener(this);

        etAdminName = (EditText) findViewById(R.id.etAdminName);
        editTextTextEmailAddress = (EditText) findViewById(R.id.etAdminEmail);
        editTextTextPassword = (EditText) findViewById(R.id.etAdminPassword);


    }



     @Override
     public void onClick(View view) {


         switch (view.getId()) {
             case R.id.btnRegisterAdmin:
                 registerAdmin();
                 break;
         }

     }

     private void registerAdmin() {

         // Take the value of two edit texts in Strings
         String email, password, name, male, female;
         name = etAdminName.getText().toString().trim();
         email = editTextTextEmailAddress.getText().toString().trim();
         password = editTextTextPassword.getText().toString().trim();



         if (name.isEmpty()) {
             etAdminName.setError("Please enter name!!");
             etAdminName.requestFocus();
             return;

         }
        //test
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

                             FirebaseDatabase.getInstance().getReference("Admins")
                                     .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                     .setValue(userReg).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {

                                     if (task.isSuccessful()) {
                                        Toast.makeText(AdminRegistration.this, "Admin registration succesfull", Toast.LENGTH_LONG).show();
                                     } else {
                                         Toast.makeText(AdminRegistration.this, "Admin registration Unsuccesfull", Toast.LENGTH_LONG).show();
                                     }
                                 }

                             });


                         }else {
                            Toast.makeText(AdminRegistration.this, "Admin registration Unsuccesfull", Toast.LENGTH_LONG).show();
                         }
                     }
                 });
     }


 }