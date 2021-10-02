package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MyMovieAdapter extends FirebaseRecyclerAdapter<
        Review, MyMovieAdapter.mymovieViewholder> {


    public MyMovieAdapter(
            @NonNull FirebaseRecyclerOptions<Review> options)
    {
        super(options);
    }

//comment

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull MyMovieAdapter.mymovieViewholder holder,
                     int position, @NonNull Review model)
    {
        //delete

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String uid = user.getUid();
        String uid = "WuylE2qPCLXfTw4NnNu1XSOTbJg2";

        DatabaseReference mbase, nbase;
        mbase = FirebaseDatabase.getInstance().getReference("MyMovieReviews");
        nbase = FirebaseDatabase.getInstance().getReference("Movie");



        final DatabaseReference itemRef = getRef(position);
        final String myKey = itemRef.getKey();

        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mbase.child(myKey).removeValue();
                nbase.child(model.getLockey()).child("reviews").child(uid).removeValue();

            }
        });


        holder.title.setText(model.getTitle());
        holder.review.setText(model.getReview());
        Glide.with(holder.itemView.getContext()).load(model.getImage()).into(holder.image);
    }

    @NonNull
    @Override
    public MyMovieAdapter.mymovieViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mymoviereview, parent, false);
        return new MyMovieAdapter.mymovieViewholder(view);
    }
    class mymovieViewholder
            extends RecyclerView.ViewHolder {
        TextView title, review;
        ImageView image;
        Button delBtn;

        public mymovieViewholder(@NonNull View itemView)
        {
            super(itemView);

            title = itemView.findViewById(R.id.tv_mrtitle);
            review = itemView.findViewById(R.id.tv_mrreview);
            image = itemView.findViewById(R.id.iv_mrimage);
            delBtn = itemView.findViewById(R.id.btn_mrdelete);

        }
    }




}
