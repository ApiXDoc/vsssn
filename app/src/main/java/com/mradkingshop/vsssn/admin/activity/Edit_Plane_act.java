package com.mradkingshop.vsssn.admin.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mradkingshop.vsssn.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Edit_Plane_act extends Activity {
    EditText plane_name, donation_amount,number_pepople_donate;
    Button summit_bt;
    ImageView product_image;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    int image_int = 0;
    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_donation_plane);

        plane_name = findViewById(R.id.plane_name);
        product_image = findViewById(R.id.product_image);

        donation_amount = findViewById(R.id.donation_amt);
        summit_bt = findViewById(R.id.summit_bt);
        number_pepople_donate=findViewById(R.id.number_pepople_donate);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        Glide.with(Edit_Plane_act.this).load(getIntent().getExtras().getString("plane_image")).into(product_image);
        donation_amount.setText(getIntent().getExtras().getString("donation_amt"));
        number_pepople_donate.setText(getIntent().getExtras().getString("number_of_people_donate"));

        plane_name.setText(getIntent().getExtras().getString("plan_name"));

        summit_bt.setText("Update");





        product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseImage();
                image_int++;

            }
        });
        summit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filePath!=null){
                    Uploading_data();

                }else {


                    data_update_without_image();
                }


            }
        });
       
    }

    private void data_update_without_image() {

        progressDialog.setMessage("Please Wait........");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        Map<String, Object> map = new HashMap<>();
        map.put("plane_name", plane_name.getText().toString());
        map.put("donation_amount", donation_amount.getText().toString());
        map.put("number_pepople_donate",number_pepople_donate.getText().toString());
        map.put("timestamp", FieldValue.serverTimestamp());


        firebaseFirestore.collection("plane_list")
                .document(getIntent().getExtras().getString("item_id"))
                .update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                plane_name.setText("");
                donation_amount.setText("");

                progressDialog.dismiss();
                Toast.makeText(Edit_Plane_act.this, "Update Done", Toast.LENGTH_SHORT).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(Edit_Plane_act.this, "Plane not created", Toast.LENGTH_SHORT).show();


            }
        });


    }

    private void Uploading_data() {

        progressDialog.setMessage("Please Wait........");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        Uri filep = filePath;

        final StorageReference riversRef = storageReference.child("plan_images/" + filep.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(filep);


        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return riversRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {


                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    final String main_product_image1_url1 = downloadUri.toString();

                    Map<String, Object> map = new HashMap<>();
                    map.put("plane_name", plane_name.getText().toString());
                    map.put("donation_amount", donation_amount.getText().toString());
                    map.put("plane_image", main_product_image1_url1);
                    map.put("number_pepople_donate",number_pepople_donate.getText().toString());
                    map.put("timestamp", FieldValue.serverTimestamp());


                    firebaseFirestore.collection("plane_list")
                            .document(getIntent().getExtras().getString("item_id"))
                            .update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            plane_name.setText("");
                            donation_amount.setText("");

                            progressDialog.dismiss();
                            Toast.makeText(Edit_Plane_act.this, "Update Done", Toast.LENGTH_SHORT).show();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressDialog.dismiss();
                            Toast.makeText(Edit_Plane_act.this, "Plane not created", Toast.LENGTH_SHORT).show();


                        }
                    });


                } else {

                    Toast.makeText(Edit_Plane_act.this, "image_not_uploaded", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            if (image_int == 1) {

                filePath = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(Edit_Plane_act.this.getContentResolver(), filePath);


                    product_image.setImageBitmap(bitmap);
                    image_int++;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {


                image_int = 0;

            }


        }
    }

}
