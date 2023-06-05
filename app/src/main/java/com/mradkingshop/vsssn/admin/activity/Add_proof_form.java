package com.mradkingshop.vsssn.admin.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.deeplabstudio.fcmsend.FCMSend;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Add_proof_form extends AppCompatActivity {
    
    ImageView proof_image;
    EditText heading_et,discreption_et,spend_et;
    
    Button summit_bt;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    int image_int = 0;
    FirebaseStorage storage;
    StorageReference storageReference;
    public   static final String CHANNEL_ID="IPServiceChannel";
    private static final String CHANNEL_NAME= "IPService";
    private static final String CHANNEL_DESC= "IPService notification";

    private static String serverKey = "AAAAdFB0AZ4:APA91bEOxX9l2crJzQj1IAfPRakcgL63a1Sr61Vd6cxjdzEQZY4IFeFB9zCxmCfFuFieF7Bfc2F_4ev7fAkWpKRe2sVjLIJJfH5qiWC_2BkK7TuG60GmfP50NLz2EpQMcPdZLASD84tA";



    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.proof_add_form);

        proof_image = findViewById(R.id.proof_image);
        heading_et = findViewById(R.id.heading);
        discreption_et = findViewById(R.id.discreption);
        spend_et = findViewById(R.id.amt_spend);

        summit_bt = findViewById(R.id.summit_bt);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        FCMSend.SetServerKey(serverKey);

        if(getIntent().getExtras()!=null){

            data_update();

        }

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription(CHANNEL_DESC);

            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }



        proof_image.setOnClickListener(new View.OnClickListener() {
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

                    dat_upadate_without_image();
                }



                
            }
        });


    }

    private void dat_upadate_without_image() {

        DatePicker datePicker=new DatePicker(this);
        String current_date = datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear();

        Map<String, Object> map = new HashMap<>();
          map.put("timestamp", FieldValue.serverTimestamp());

        map.put("date",current_date);
        map.put("proof_image_url",getIntent().getExtras().getString("image"));
        map.put("proof_heading",heading_et.getText().toString());
        map.put("proof_discreption",discreption_et.getText().toString());
        map.put("proof_spending",spend_et.getText().toString());




        firebaseFirestore.collection("proof_list").document(getIntent().getExtras().getString("itemId")).
                update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {


                notification("user",getIntent().getExtras().getString("image"));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(Add_proof_form.this, "Plane not created", Toast.LENGTH_SHORT).show();


            }
        });

    }

    private void data_update_upload() {

        progressDialog.setMessage("Please Wait........");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        DatePicker datePicker=new DatePicker(this);
        String current_date = datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear();


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
                     map.put("timestamp", FieldValue.serverTimestamp());

                    map.put("date",current_date);
                    map.put("proof_image_url",main_product_image1_url1);
                    map.put("proof_heading",heading_et.getText().toString());
                    map.put("proof_discreption",discreption_et.getText().toString());
                    map.put("proof_spending",spend_et.getText().toString());




                    firebaseFirestore.collection("proof_list").document(getIntent().getExtras().getString("itemId")).
                            update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {


                            notification("user_list",main_product_image1_url1);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressDialog.dismiss();
                            Toast.makeText(Add_proof_form.this, "Plane not created", Toast.LENGTH_SHORT).show();


                        }
                    });


                } else {

                    progressDialog.dismiss();
                    Toast.makeText(Add_proof_form.this, "image_not_uploaded", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private void data_update() {

        heading_et.setText(getIntent().getExtras().getString("titel"));
        discreption_et.setText(getIntent().getExtras().getString("dis"));
        spend_et.setText(getIntent().getExtras().getString("amt_spend"));
    }


    private void Uploading_data() {

        progressDialog.setMessage("Please Wait........");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        DatePicker datePicker=new DatePicker(this);
        String current_date = datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear();


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
                    map.put("plane_name", getIntent().getExtras().getString("plane_name"));
                    map.put("donation_amount", getIntent().getExtras().getString("donation_amount"));
                    map.put("plane_image", getIntent().getExtras().getString("plane_image"));
                    map.put("number_pepople_donate",getIntent().getExtras().getString("number_pepople_donate"));
                    map.put("timestamp", FieldValue.serverTimestamp());

                    map.put("date",current_date);
                    map.put("proof_image_url",main_product_image1_url1);
                    map.put("proof_heading",heading_et.getText().toString());
                    map.put("proof_discreption",discreption_et.getText().toString());
                    map.put("proof_spending",spend_et.getText().toString());




                    firebaseFirestore.collection("proof_list").document().
                            set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {


                            notification("user",main_product_image1_url1);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressDialog.dismiss();
                            Toast.makeText(Add_proof_form.this, "Plane not created", Toast.LENGTH_SHORT).show();


                        }
                    });


                } else {

                    progressDialog.dismiss();
                    Toast.makeText(Add_proof_form.this, "image_not_uploaded", Toast.LENGTH_SHORT).show();

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
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);


                    proof_image.setImageBitmap(bitmap);
                    image_int++;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {


                image_int = 0;

            }


        }
    }

    private void notification(String topic,String link) {


        progressDialog.setMessage("Please Wait........");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();




        FCMSend.Builder build = new FCMSend.Builder("user", true)
                .setTitle(heading_et.getText().toString())
                .setBody(discreption_et.getText().toString()); // Optional
        build.send();



        String result2 = build.send().Result();
        if(ChekSuccess( result2).contentEquals("Success")){


            progressDialog.dismiss();

            Toast.makeText(Add_proof_form.this, "Proof is Added", Toast.LENGTH_SHORT).show();

        }else {

            progressDialog.dismiss();

            Toast.makeText(Add_proof_form.this, "Proof is not Added", Toast.LENGTH_SHORT).show();

            Log.e("error_in_notification",result2);
        }




    }

    private String ChekSuccess(String result) {
        try {
            JSONObject object = new JSONObject(result);
            String success = object.getString("success");
            if (success.equals("1")) {
                return "Success";
            } else if (success.equals("0")) {
                return "Unsuccessful";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Unsuccessful";
    }

}
