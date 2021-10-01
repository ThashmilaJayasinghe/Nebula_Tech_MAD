package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileview extends AppCompatActivity {
    private TextView Display;
    private TextView name;
    private EditText password;
    private TextView email;
    private Button update;
    FirebaseUser user;
    private String uid="5pGDTkgh4uZLyx6ILT72v9avCTa2";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public static String UserName;
    public static String UEmail;
    public static String PassWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileview);
        name= findViewById(R.id.PName);
        //name.setKeyListener(null);
        password= findViewById(R.id.PPassword);
       // password.setKeyListener(null);
        email= findViewById(R.id.PEmail);
        //email.setKeyListener(null);
        update= findViewById(R.id.profileupdatebutton);

        Display = findViewById(R.id.DUsername);

        firebaseDatabase = FirebaseDatabase.getInstance();
        //user= FirebaseAuth.getInstance().getCurrentUser();
        //uid= user.getUid();
        databaseReference= firebaseDatabase.getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Username = snapshot.child("5pGDTkgh4uZLyx6ILT72v9avCTa2").child("name").getValue(String.class);
                String Email = snapshot.child("5pGDTkgh4uZLyx6ILT72v9avCTa2").child("email").getValue().toString();
                String Password = snapshot.child("5pGDTkgh4uZLyx6ILT72v9avCTa2").child("password").getValue().toString();

                email.setText(Email);
                name.setText(Username);
                UserName=Username;
                UEmail = Email;
                PassWord= Password;
                Display.setText(Username);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profileview.this,Updateprofile.class );

                intent.putExtra("Name",UserName);
                intent.putExtra("email",UEmail);
                intent.putExtra("password",PassWord);
                intent.putExtra("uid",uid);
                startActivity(intent);

            }
        });

    }
     //View.OnClickListener

    /*public void opensecond(View view){
        Intent intent = new Intent(this, Updateprofile.class);
        startActivity(intent);
    }*/
}