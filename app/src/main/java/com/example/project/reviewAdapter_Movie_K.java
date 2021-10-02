package com.example.project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class reviewAdapter_Movie_K extends FirebaseRecyclerAdapter<ReviewModel_K,reviewAdapter_Movie_K.myviewholder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public reviewAdapter_Movie_K(@NonNull FirebaseRecyclerOptions<ReviewModel_K> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull reviewAdapter_Movie_K.myviewholder holder,
                                    @SuppressLint("RecyclerView") final int position, @NonNull ReviewModel_K model) {
        holder.user.setText(model.getUser());
        holder.review.setText(model.getReview());
//remove
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.user.getContext());
                builder.setTitle("Are you Sure?");
                builder.setMessage("Deleted...Data can not Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Movie").child("-MkMJRFCQcGrXrzo7fgM").child("reviews")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.user.getContext(),"Cancelled.",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

            }
        });

    }

    @NonNull
    @Override
    public reviewAdapter_Movie_K.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_itemk, parent, false);
        return new reviewAdapter_Movie_K.myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView user;
        TextView review;
        Button remove;
        ImageView img;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            user = (TextView) itemView.findViewById(R.id.username);
            review = (TextView) itemView.findViewById(R.id.review);
            remove = (Button) itemView.findViewById(R.id.btnRemove);
        }
    }
}

