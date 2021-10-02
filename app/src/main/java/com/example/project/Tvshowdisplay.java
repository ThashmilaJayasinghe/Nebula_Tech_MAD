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

public class Tvshowdisplay extends AppCompatActivity {
    private  EditText tvshowreview;
    private Button confirm;
    RecyclerView Rreview;
    private ImageView img;
    private EditText description;

    FirebaseDatabase firebaseDatabase,firebaseDatabase2;
    DatabaseReference databaseReference, databaseReferenceR,databaseReferenceI,databaseReferenceD;

    String postkey;

    ArrayList<Tvshowreviews> list;
    Tvshowadapter tvshowadapter;

    //adding reviews ********************************************************************************
    Review myreview;
    String title;
    String image;
    //***********************************************************************************************


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshowdisplay);


        Intent intent = getIntent();
        postkey=getIntent().getStringExtra("postkey");


            //initialize id from resource
            tvshowreview = findViewById(R.id.addreviewtv);
            confirm = findViewById(R.id.reviewconfirmtv);
            Rreview = findViewById(R.id.tvshowR);
            img= findViewById(R.id.tvshowimage);
            description= findViewById(R.id.moviedescription);

            //database instance and reference
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReferenceR = firebaseDatabase.getReference("TVShow").child(postkey);
            //for image
            databaseReferenceI= firebaseDatabase.getReference("TVShow").child(postkey);
            //des
            databaseReferenceD= firebaseDatabase.getReference("Book").child(postkey);
            //recyclerview
            Rreview.setHasFixedSize(true);
            Rreview.setLayoutManager(new LinearLayoutManager(this));
            list = new ArrayList<>();
            tvshowadapter = new Tvshowadapter(this, list);
            Rreview.setAdapter(tvshowadapter);

            databaseReference = firebaseDatabase.getReference("TVShow").child(postkey).child("reviews");

            //retreive data to recyclerview
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Clear array list before adding new data in it because data will be duplicated
                    list.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Tvshowreviews user = dataSnapshot1.getValue(Tvshowreviews.class);
                        list.add(user);
                    }
                    tvshowadapter.notifyDataSetChanged();
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
        databaseReferenceD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String Description = snapshot.child("description").getValue().toString();
//                description.setText(Description);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //convert edittext to String
                    String Review = tvshowreview.getText().toString();

                    if (TextUtils.isEmpty(Review) ){
                        Toast.makeText(Tvshowdisplay.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                    } else {
                        addDatatoFirebase(Review);
                    }

                }
            });


        }

        private void addDatatoFirebase ( String Review) {
            String id= "id";

            String randomkey = id+""+new Random().nextInt(1000);

            FirebaseUser ruser = FirebaseAuth.getInstance().getCurrentUser();
            String ruid = ruser.getUid();

            HashMap cmnt = new HashMap();
            cmnt.put("review", Review);

            //adding reviews *****************************************************************************

            DatabaseReference revbase = FirebaseDatabase.getInstance().getReference("MyTVshowReviews");

            myreview = new Review();

            DatabaseReference movreference = FirebaseDatabase.getInstance().getReference("TVShow");

            movreference.orderByKey().equalTo(postkey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot datas: dataSnapshot.getChildren()){

                        title = datas.child("title").getValue(String.class);
                        image = datas.child("imageUrl").getValue(String.class);
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
                        Toast.makeText(Tvshowdisplay.this, "data added", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(Tvshowdisplay.this, "data  not added", Toast.LENGTH_SHORT).show();
                }
            });

        }

}