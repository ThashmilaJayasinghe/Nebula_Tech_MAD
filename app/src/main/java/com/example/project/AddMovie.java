package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddMovie extends AppCompatActivity {

    EditText et_mvtitle;
    EditText et_mvgenre;
    EditText et_mvdirector;
    EditText et_mvdescription;
    Button btn_mvsave;
    Button btn_mvcancel;
    Button btn_mvphoto;
    ImageView iv_mv;

    Uri filePath;
    final int PICK_IMAGE_REQUEST = 22;

    DatabaseReference db_ref;
    FirebaseStorage storage;
    StorageReference st_ref;

    Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        et_mvtitle = findViewById(R.id.et_mvtitle);
        et_mvgenre = findViewById(R.id.et_mvgenre);
        et_mvdirector = findViewById(R.id.et_mvdirector);
        et_mvdescription = findViewById(R.id.et_mvdescription);

        btn_mvsave = findViewById(R.id.btn_mvsave);
        btn_mvcancel = findViewById(R.id.btn_mvcancel);
        btn_mvphoto = findViewById(R.id.btn_mvphoto);

        iv_mv = findViewById(R.id.iv_mv);

        movie = new Movie();

        Toolbar toolbar = findViewById(R.id.addmvtoolbar);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        setSupportActionBar(toolbar);


        btn_mvcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearControls();
            }
        });

        btn_mvphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        btn_mvsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save();
            }
        });
    }

    //Method to clear all user inputs
    private void clearControls() {
        et_mvtitle.setText("");
        et_mvgenre.setText("");
        et_mvdirector.setText("");
        et_mvdescription.setText("");
        iv_mv.setImageResource(R.drawable.mv1);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    // UploadImage method
    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = st_ref
                    .child(System.currentTimeMillis()
                            + "." + getFileExtension(filePath));

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    progressDialog.dismiss();

                                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imageUrl = uri.toString();
                                            movie.setImageUrl(imageUrl);
                                            db_ref.push().setValue(movie);
                                        }
                                    });
                                    Toast
                                            .makeText(AddMovie.this,
                                                    "Data saved successfully",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                    clearControls();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            progressDialog.dismiss();
                            Toast
                                    .makeText(AddMovie.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }


    private void save() {

        db_ref = FirebaseDatabase.getInstance().getReference("Movie");
        storage = FirebaseStorage.getInstance();
        st_ref = storage.getReference("Movie");

        if(TextUtils.isEmpty(et_mvtitle.getText().toString()))
            Toast.makeText(getApplicationContext(),
                    "Please enter movie title",
                    Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(et_mvgenre.getText().toString()))
            Toast.makeText(getApplicationContext(),
                    "Please enter movie genre",
                    Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(et_mvdirector.getText().toString()))
            Toast.makeText(getApplicationContext(),
                    "Please enter movie director",
                    Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(et_mvdescription.getText().toString()))
            Toast.makeText(getApplicationContext(),
                    "Please enter movie description",
                    Toast.LENGTH_SHORT).show();
        else if(filePath == null)
            Toast.makeText(getApplicationContext(),
                    "Please select an image",
                    Toast.LENGTH_SHORT).show();
        else {
            //Take inputs from the user and assign them to this instance
            uploadImage();
            movie.setTitle(et_mvtitle.getText().toString().trim());
            movie.setGenre(et_mvgenre.getText().toString().trim());
            movie.setDirector(et_mvdirector.getText().toString().trim());
            movie.setDescription(et_mvdescription.getText().toString().trim());

        }
    }

    private void selectImage() {

        //Defining implicit intent to  mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }


    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                iv_mv.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }
}