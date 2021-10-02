package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Moviedisplay extends AppCompatActivity {
    private EditText moviewreview;
    private Button confirm;
    RecyclerView Rreview;
    private EditText description;
    private ImageView img;

    FirebaseDatabase firebaseDatabase,firebaseDatabase2;
    DatabaseReference databaseReference, databaseReferenceR,databaseReferenceI,databaseReferenceD, dbreference;



    ArrayList<Moviereviews> list;
    //object of adapter
    movieadapter Movieadapter;

    //adding reviews ********************************************************************************
    String postkey;
    Review myreview;
    String title;
    String image;
    //***********************************************************************************************


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedisplay);
        moviewreview = findViewById(R.id.addreview);
        confirm = findViewById(R.id.reviewconfirm);
        Rreview = findViewById(R.id.commentview);
        img= findViewById(R.id.movieimage);
        description= findViewById(R.id.moviedescription);


        //adding review *****************************************************************************
        Intent intent = getIntent();
        postkey=getIntent().getStringExtra("postkey");
        //postkey = "-MkMJRFCQcGrXrzo7fgM";


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceR= firebaseDatabase.getReference("Movie").child(postkey);
        //des
        databaseReferenceD= firebaseDatabase.getReference("Movie").child(postkey);
        //image
        databaseReferenceI= firebaseDatabase.getReference("Movie").child(postkey);
        //recyclerview
        Rreview.setHasFixedSize(true);
        Rreview.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        Movieadapter = new movieadapter(this, list);
        Rreview.setAdapter(Movieadapter);

        //create instance
        databaseReference = firebaseDatabase.getReference("Movie").child(postkey).child("reviews");

        //retrieve
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Moviereviews user = dataSnapshot1.getValue(Moviereviews.class);
                    list.add(user);
                }
                Movieadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //image
        databaseReferenceI.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String url = snapshot.child("imageUrl").getValue().toString();
                Picasso.get().load(url).into(img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //description
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

            //convert edittext to string
            public void onClick(View v) {
                String Review = moviewreview.getText().toString();

                if (TextUtils.isEmpty(Review) ){
                    Toast.makeText(Moviedisplay.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    addDatatoFirebase(Review);
                }

            }
        });


    }
    //add reviews to firebase
    private void addDatatoFirebase ( String Review) {
        String id= "id";

        String randomkey = id+""+new Random().nextInt(1000);

        FirebaseUser ruser = FirebaseAuth.getInstance().getCurrentUser();
        String ruid = ruser.getUid();
        //String ruid = "id500";


        HashMap cmnt = new HashMap();
        cmnt.put("review", Review);

        //adding reviews *****************************************************************************

        DatabaseReference revbase = FirebaseDatabase.getInstance().getReference("MyMovieReviews");

        myreview = new Review();

        DatabaseReference movreference = FirebaseDatabase.getInstance().getReference("Movie");

        movreference.orderByKey().equalTo(postkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot datas: dataSnapshot.getChildren()){

                    title = datas.child("title").getValue(String.class);
                    image = datas.child("imageUrl").getValue(String.class);

//                    String vehicle_type = datas.child("v_tpe").getValue(String.class);
//                    book.setTitle(et_bktitle.getText().toString().trim());
                }

                myreview.setUid(ruid);
                myreview.setLockey(postkey);
                myreview.setReview(Review);
                myreview.setTitle(title);
                myreview.setImage(image);

                revbase.push().setValue(myreview);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        //adding reviews ******************************************************************************

        databaseReference.child(ruid).updateChildren(cmnt).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Moviedisplay.this, "data added", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(Moviedisplay.this, "data  not added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}