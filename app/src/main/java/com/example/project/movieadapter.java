package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class movieadapter extends RecyclerView.Adapter<movieadapter.reviewholder >{
    Context context;
    ArrayList<Moviereviews> reviewArrayList;

    public movieadapter(Context context, ArrayList<Moviereviews> reviewArrayList) {
        this.context = context;
        this.reviewArrayList = reviewArrayList;
    }

    @NonNull
    @Override
    public movieadapter.reviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //initialise card view element to recycler view
        View v = LayoutInflater.from(context).inflate(R.layout.cardview,parent,false);
        return new reviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull movieadapter.reviewholder holder, int position) {
        Moviereviews User = reviewArrayList.get(position);
        holder.review.setText(User.getReview());
    }

    @Override
    //getting the number of entries in database(array count)
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
