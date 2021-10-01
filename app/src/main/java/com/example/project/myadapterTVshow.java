package com.example.project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapterTVshow extends FirebaseRecyclerAdapter <modelTV, myadapterTVshow.myviewholder>
{
    Button deletebtn;
    public myadapterTVshow(@NonNull FirebaseRecyclerOptions<modelTV> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position, @NonNull modelTV modelTV) {

        holder.name.setText(modelTV.getTitle());
        holder.email.setText(modelTV.getGenre());

        Glide.with(holder.img.getContext()).load(modelTV.getImageUrl()).into(holder.img);

       //edit
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus =DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontenttv))
                        .setExpanded(true,1800)
                        .create();

               View myview = dialogPlus.getHolderView();
                EditText title = myview.findViewById(R.id.et_Title);
                EditText genre = myview.findViewById(R.id.et_Genre_TV);
                EditText sessions = myview.findViewById(R.id.et_Sessions_TV);
                EditText description = myview.findViewById(R.id.et_Description);
                Button submit = myview.findViewById(R.id.btn_submit);

                title.setText(modelTV.getTitle());
                sessions.setText(modelTV.getSeasons());
                genre.setText(modelTV.getGenre());
                description.setText(modelTV.getDescription());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map =new HashMap<>();
                        map.put("title", title.getText().toString());
                        map.put("sessions", sessions.getText().toString());
                        map.put("genre", genre.getText().toString());
                        map.put("description", description.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("TVShow").child(getRef(position)
                                .getKey())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });



        //delete
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Delelte Pannel");
                builder.setMessage("Are you want to delete ?");

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("TVShow").child(getRef(position).getKey())
                                .removeValue();
                    }
                });



                builder.show();
            }
        });


    }//end of blind class

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{


        CircleImageView img;
        TextView name, email;
        ImageView delete;
        Button edit;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView) itemView.findViewById(R.id.img_view);
            name =(TextView)itemView.findViewById(R.id.nametext);
            email =(TextView)itemView.findViewById(R.id.emailtext);

            delete = (ImageView) itemView.findViewById(R.id.img_btn_delete);
            edit =  (Button) itemView.findViewById(R.id.btn_edit);


        }
    }
}
