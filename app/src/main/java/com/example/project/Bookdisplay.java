package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Bookdisplay extends AppCompatActivity {

    private EditText bookreview;
    private Button confirm;
    RecyclerView Rreview;
    private EditText description;
    private ImageView img;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReferenceR,databaseReferenceD,databaseReferenceI;
    ArrayList<Bookreviews> list;
    Bookadapter bookadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdisplay);
        bookreview= findViewById(R.id.addreviewbook);
        confirm = findViewById(R.id.reviewconfirmbook);
        Rreview = findViewById(R.id.Rbook);
        description= findViewById(R.id.moviedescription);
        description.setEnabled(false);


        //database instance and reference
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceR = firebaseDatabase.getReference("Book").child("-MkIhcvghnveftcABZMp");
        //for description
        databaseReferenceD= firebaseDatabase.getReference("Book").child("-MkIhcvghnveftcABZMp");
        //for image
        //databaseReferenceI= firebaseDatabase.getReference("Book").child("-MkIhcvghnveftcABZMp").child("url");
        //recyclerview
        Rreview.setHasFixedSize(true);
        Rreview.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        bookadapter = new Bookadapter(this, list);
        Rreview.setAdapter(bookadapter);

        databaseReference = firebaseDatabase.getReference("Book").child("-MkIhcvghnveftcABZMp").child("reviews");

        //retreive data to recyclerview
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Clear array list before adding new data in it because data will be duplicated
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Bookreviews user = dataSnapshot1.getValue(Bookreviews.class);
                    list.add(user);
                }
                bookadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        //retrieve description
        databaseReferenceD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Description = snapshot.child("description").getValue().toString();
                description.setText(Description);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //convert edittext to String
                String Review = bookreview.getText().toString();

                if (TextUtils.isEmpty(Review) ){
                    Toast.makeText(Bookdisplay.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    addDatatoFirebase(Review);
                }

            }
        });


    }
    //add reviews to database
    private void addDatatoFirebase ( String Review) {
        String id= "id";

        String randomkey = id+""+new Random().nextInt(1000);

        HashMap cmnt = new HashMap();
        cmnt.put("review", Review);

        databaseReference.child(randomkey).updateChildren(cmnt).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful())
                    Toast.makeText(Bookdisplay.this, "data added", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Bookdisplay.this, "data  not added", Toast.LENGTH_SHORT).show();
            }
        });

    }
}