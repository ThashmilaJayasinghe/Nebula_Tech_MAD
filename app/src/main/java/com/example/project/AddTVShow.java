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

public class AddTVShow extends AppCompatActivity {

    EditText et_tvtitle;
    EditText et_tvgenre;
    EditText et_tvseasons;
    EditText et_tvdescription;
    Button btn_tvsave;
    Button btn_tvcancel;
    Button btn_tvphoto;
    ImageView iv_tv;

    Uri filePath;
    final int PICK_IMAGE_REQUEST = 22;

    DatabaseReference db_ref;
    FirebaseStorage storage;
    StorageReference st_ref;

    TVShow tvshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tvshow);

        et_tvtitle = findViewById(R.id.et_tvtitle);
        et_tvgenre = findViewById(R.id.et_tvgenre);
        et_tvseasons = findViewById(R.id.et_tvseasons);
        et_tvdescription = findViewById(R.id.et_tvdescription);

        btn_tvsave = findViewById(R.id.btn_tvsave);
        btn_tvcancel = findViewById(R.id.btn_tvcancel);
        btn_tvphoto = findViewById(R.id.btn_tvphoto);

        iv_tv = findViewById(R.id.iv_tv);

        tvshow = new TVShow();

        Toolbar toolbar = findViewById(R.id.addtvtoolbar);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        setSupportActionBar(toolbar);



        btn_tvcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearControls();
            }
        });

        btn_tvphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btn_tvsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save();
            }
        });

    }

    //Method to clear all user inputs
    private void clearControls() {
        et_tvtitle.setText("");
        et_tvgenre.setText("");
        et_tvseasons.setText("");
        et_tvdescription.setText("");
        iv_tv.setImageResource(R.drawable.tv1);
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

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();

                                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            String imageUrl = uri.toString();

                                            tvshow.setImageUrl(imageUrl);

                                            db_ref.push().setValue(tvshow);

//                                            Upload upload = new Upload("book", imageUrl);
//                                            String uploadId = databaseReference.push().getKey();
//                                            databaseReference.child(uploadId).setValue(upload);

                                        }
                                    });

                                    Toast
                                            .makeText(AddTVShow.this,
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

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(AddTVShow.this,
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


        db_ref = FirebaseDatabase.getInstance().getReference("TVShow");
        storage = FirebaseStorage.getInstance();
        st_ref = storage.getReference("TVShow");

        if(TextUtils.isEmpty(et_tvtitle.getText().toString()))
            Toast.makeText(getApplicationContext(),
                    "Please enter TVShow title",
                    Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(et_tvgenre.getText().toString()))
            Toast.makeText(getApplicationContext(),
                    "Please enter TVShow genre",
                    Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(et_tvseasons.getText().toString()))
            Toast.makeText(getApplicationContext(),
                    "Please enter number of seasons",
                    Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(et_tvdescription.getText().toString()))
            Toast.makeText(getApplicationContext(),
                    "Please enter TVShow description",
                    Toast.LENGTH_SHORT).show();
        else if(filePath == null)
            Toast.makeText(getApplicationContext(),
                    "Please select an image",
                    Toast.LENGTH_SHORT).show();
        else {
            //Take inputs from the user and assign them to this instance
            uploadImage();
            tvshow.setTitle(et_tvtitle.getText().toString().trim());
            tvshow.setGenre(et_tvgenre.getText().toString().trim());
            tvshow.setSeasons(et_tvseasons.getText().toString().trim());
            tvshow.setDescription(et_tvdescription.getText().toString().trim());
            //Insert in to the database
//            db_ref.push().setValue(book);
            //Feedback to the user via a toast
//            Toast.makeText(getApplicationContext(), "Data Saved Successfully", Toast.LENGTH_SHORT).show();
//            clearControls();
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
                iv_tv.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }


}