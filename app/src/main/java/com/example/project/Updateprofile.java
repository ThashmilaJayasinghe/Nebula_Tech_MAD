package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.PrivateKey;

public class Updateprofile extends AppCompatActivity {
    private TextView Display;
    private EditText Name;
    private EditText Email;
    private EditText Password;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String _uid,_email,_password,_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatenew);
        Display= findViewById(R.id.DPUsername);
        Name= findViewById(R.id.Pname);
        Email= findViewById(R.id.Pemail);
        Password= findViewById(R.id.PPassword);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference("Users");


        //showalldata
        showAllUserData();

    }
    private void showAllUserData(){
        Intent intent = getIntent();
        _name=intent.getStringExtra("Name");
        _email=intent.getStringExtra("email");
        _password=intent.getStringExtra("password");
        _uid=intent.getStringExtra("uid");

        Name.setText(_name);
        Display.setText(_name);
        Email.setText(_email);
        Password.setText(_password);

    }

    public void update(View view){
        if(isNamechanged() || ispasswordchanged()|| isemailchanged()){
            Toast.makeText(this, "Data has been updated ", Toast.LENGTH_LONG).show();

        }else
        {
            Toast.makeText(this, "Data is same", Toast.LENGTH_LONG).show();}
    }

    private boolean isemailchanged() {
        if (!_email.equals(Email.getText().toString())){
            databaseReference.child(_uid).child("email").setValue(Name.getText().toString());
            return true;
        }else{
            return false;
        }
    }

    private boolean ispasswordchanged() {
        if (!_password.equals(Password.getText().toString())){
            databaseReference.child(_uid).child("password").setValue(Password.getText().toString());
            return true;
        }else{
            return false;
        }
    }

    private boolean isNamechanged() {
        if (!_name.equals(Name.getText().toString())){
                databaseReference.child(_uid).child("name").setValue(Name.getText().toString());
                return true;
        }else{
            return false;
        }
    }

}