package com.example.project;

import android.content.Context;
import android.content.Intent;
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

public class bookAdaptar_K extends FirebaseRecyclerAdapter<BookModel_K,bookAdaptar_K.myviewholder> {
    public bookAdaptar_K(@NonNull FirebaseRecyclerOptions<BookModel_K> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull BookModel_K model) {
        holder.title.setText(model.getTitle());
        holder.author.setText(model.getAuthor());

        Glide.with(holder.img.getContext())
                .load(model.getImageUrl())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        final String postkey = getRef(position).getKey();

        holder.btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext() ,Admin_BookReview.class);
                Intent intent = new Intent(view.getContext() ,Bookdisplay.class);
                intent.putExtra("postkey",postkey);
                view.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_k,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView title;
        TextView author;
        Button btnReview;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.img1);
            title = (TextView)itemView.findViewById(R.id.txtname);
            author = (TextView)itemView.findViewById(R.id.txtauthor);
            btnReview = (Button)itemView.findViewById(R.id.btnReview);
        }
    }
}
