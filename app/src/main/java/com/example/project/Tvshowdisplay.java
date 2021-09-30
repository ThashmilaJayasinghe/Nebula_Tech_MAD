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

public class Tvshowdisplay extends AppCompatActivity {
    private  EditText tvshowreview;
    private Button confirm;
    RecyclerView Rreview;

    FirebaseDatabase firebaseDatabase,firebaseDatabase2;
    DatabaseReference databaseReference, databaseReferenceR;

    ArrayList<Tvshowreviews> list;
    Tvshowadapter tvshowadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshowdisplay);




            //initialize id from resource
            tvshowreview = findViewById(R.id.addreviewtv);
            confirm = findViewById(R.id.reviewconfirmtv);
            Rreview = findViewById(R.id.tvshowR);

            //database instance and reference
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReferenceR = firebaseDatabase.getReference("TVShow").child("-MkMiBSz-svCP2tp2rx2");
            //recyclerview
            Rreview.setHasFixedSize(true);
            Rreview.setLayoutManager(new LinearLayoutManager(this));
            list = new ArrayList<>();
            tvshowadapter = new Tvshowadapter(this, list);
            Rreview.setAdapter(tvshowadapter);

            databaseReference = firebaseDatabase.getReference("TVShow").child("-MkMiBSz-svCP2tp2rx2").child("reviews");

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

            HashMap cmnt = new HashMap();
            cmnt.put("review", Review);

            databaseReference.child(randomkey).updateChildren(cmnt).addOnCompleteListener(new OnCompleteListener() {
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