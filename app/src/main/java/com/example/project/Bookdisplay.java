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
    String postkey;

    //adding reviews ********************************************************************************
    Review myreview;
    String title;
    String image;
    //***********************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdisplay);
        bookreview= findViewById(R.id.addreviewbook);
        confirm = findViewById(R.id.reviewconfirmbook);
        Rreview = findViewById(R.id.Rbook);
        description= findViewById(R.id.moviedescription);
//        description.setEnabled(false);

        Intent intent = getIntent();
        postkey=getIntent().getStringExtra("postkey");

        //database instance and reference
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceR = firebaseDatabase.getReference("Book").child(postkey);
        //for description
        databaseReferenceD= firebaseDatabase.getReference("Book").child(postkey);
        //for image
        databaseReferenceI= firebaseDatabase.getReference("Book").child(postkey);
        //recyclerview
        Rreview.setHasFixedSize(true);
        Rreview.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        bookadapter = new Bookadapter(this, list);
        Rreview.setAdapter(bookadapter);

        databaseReference = firebaseDatabase.getReference("Book").child(postkey).child("reviews");

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
        //image
        databaseReferenceI.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String url = snapshot.child("imageUrl").getValue().toString();
//                Picasso.get().load(url).into(img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

        FirebaseUser ruser = FirebaseAuth.getInstance().getCurrentUser();
        String ruid = ruser.getUid();


        HashMap cmnt = new HashMap();
        cmnt.put("review", Review);

        //adding reviews *****************************************************************************

        DatabaseReference revbase = FirebaseDatabase.getInstance().getReference("MyBookReviews");

        myreview = new Review();

        DatabaseReference movreference = FirebaseDatabase.getInstance().getReference("Book");

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
                if (task.isSuccessful())
                    Toast.makeText(Bookdisplay.this, "data added", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Bookdisplay.this, "data  not added", Toast.LENGTH_SHORT).show();
            }
        });

    }
}