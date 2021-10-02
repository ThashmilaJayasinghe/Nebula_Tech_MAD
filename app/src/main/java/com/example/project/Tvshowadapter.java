package com.example.project;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Tvshowadapter extends RecyclerView.Adapter<Tvshowadapter.reviewholder > {
    Context context;
    ArrayList<Tvshowreviews> reviewArrayList;

    public Tvshowadapter(Context context, ArrayList<Tvshowreviews> reviewArrayList) {
        this.context = context;
        this.reviewArrayList = reviewArrayList;
    }

    @NonNull
    @Override
    public com.example.project.Tvshowadapter.reviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //initialise card view element to recycler view
        View v = LayoutInflater.from(context).inflate(R.layout.cardview,parent,false);
        return new reviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Tvshowadapter.reviewholder holder, int position) {
        Tvshowreviews user = reviewArrayList.get(position);
        holder.review.setText(user.getReview());
    }
    //getting the number of entries in database(array count)
    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }
    public static class reviewholder extends RecyclerView.ViewHolder{
        TextView review;

        public reviewholder(@NonNull View itemView) {
            super(itemView);
            review = itemView.findViewById(R.id.reviewid);
        }
    }
}
