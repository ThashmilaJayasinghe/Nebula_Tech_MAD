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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyBookAdapter extends FirebaseRecyclerAdapter<
        Review, MyBookAdapter.mybookViewholder> {


    public MyBookAdapter(
            @NonNull FirebaseRecyclerOptions<Review> options)
    {
        super(options);
    }



    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull MyBookAdapter.mybookViewholder holder,
                     int position, @NonNull Review model)
    {
        //delete
        DatabaseReference mbase;
        mbase = FirebaseDatabase.getInstance().getReference("MyBookReviews");



        final DatabaseReference itemRef = getRef(position);
        final String myKey = itemRef.getKey();

        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbase.child(myKey).removeValue();
            }
        });


        holder.title.setText(model.getTitle());
        holder.review.setText(model.getReview());
        Glide.with(holder.itemView.getContext()).load(model.getImage()).into(holder.image);
    }

    @NonNull
    @Override
    public MyBookAdapter.mybookViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mybookreview, parent, false);
        return new MyBookAdapter.mybookViewholder(view);
    }
    class mybookViewholder
            extends RecyclerView.ViewHolder {
        TextView title, review;
        ImageView image;
        Button delBtn;

        public mybookViewholder(@NonNull View itemView)
        {
            super(itemView);

            title = itemView.findViewById(R.id.tv_brtitle);
            review = itemView.findViewById(R.id.tv_brreview);
            image = itemView.findViewById(R.id.iv_brimage);
            delBtn = itemView.findViewById(R.id.btn_brdelete);

        }
    }







}
