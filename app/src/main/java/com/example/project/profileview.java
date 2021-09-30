package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileview extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private EditText email;
    FirebaseUser user;
    private String uid;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileview);
        name= findViewById(R.id.Pname);
        //name.setKeyListener(null);
        password= findViewById(R.id.Ppassword);
       // password.setKeyListener(null);
        email = findViewById(R.id.Pemail);
        //email.setKeyListener(null);

        firebaseDatabase = FirebaseDatabase.getInstance();
        user= FirebaseAuth.getInstance().getCurrentUser();
        uid= user.getUid();
        databaseReference= firebaseDatabase.getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Username = snapshot.child(uid).child("name").getValue(String.class);
                String Email = snapshot.child(uid).child("email").getValue(String.class);
                String Password = snapshot.child(uid).child("password").getValue(String.class);

                email.setText(Email);
                name.setText(Username);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void opensecond(View view){
        Intent intent = new Intent(this, Updateprofile.class);
        startActivity(intent);
    }
}