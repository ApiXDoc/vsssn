package com.mradkingshop.vsssn.admin.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mradkingshop.vsssn.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class other_data_my_daonation extends Fragment {
    EditText home_page_heading,reg_no,title,Discreption;
    ImageView image;
    Button change_image_bt;
    CardView ok;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    int image_int = 0;
    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.other_data_edit_home_page, container, false);

        home_page_heading=root.findViewById(R.id.home_page_heading);
        reg_no=root.findViewById(R.id.reg_no);
        title=root.findViewById(R.id.title);
        Discreption=root.findViewById(R.id.discreption);
        image=root.findViewById(R.id.image);
        change_image_bt=root.findViewById(R.id.change_image_bt);
        ok=root.findViewById(R.id.ok);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(getActivity());

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        set_dat();

        change_image_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                chooseImage();
                image_int++;
            }
        });


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Please wait.......");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                if(filePath!=null){

                    data_update_with_image();

                }else {

                    Map<String,Object> map=new HashMap<>();
                    map.put("my_donation_main_heading",home_page_heading.getText().toString());
                    map.put("my_donation_page_dis",Discreption.getText().toString());
                    map.put("my_donation_page_title",title.getText().toString());
                    map.put("my_donation_reg",reg_no.getText().toString());
                    firebaseFirestore.collection("admin").document("1").update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(@NonNull Void unused) {


                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "data updated", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "data not updated", Toast.LENGTH_SHORT).show();

                        }
                    });


                }






            }
        });


        return root;
    }

    private void data_update_with_image() {



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

                    map.put("my_donation_main_heading",home_page_heading.getText().toString());
                    map.put("my_donation_page_dis",Discreption.getText().toString());
                    map.put("my_donation_page_title",title.getText().toString());
                    map.put("my_donation_reg",reg_no.getText().toString());

                    map.put("my_donation_image",main_product_image1_url1);



                    firebaseFirestore.collection("admin")
                            .document("1").
                            update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {


                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "data updated", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "data not updated", Toast.LENGTH_SHORT).show();


                        }
                    });


                } else {

                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "image_not_uploaded", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private void set_dat() {

        progressDialog.setMessage("Please wait.......");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        firebaseFirestore.collection("admin").document("1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                DocumentSnapshot documentSnapshot=task.getResult();
                String home_main_heading_st=documentSnapshot.getString("my_donation_main_heading");
                String home_page_dis=documentSnapshot.getString("my_donation_page_dis");
                String home_page_image_url=documentSnapshot.getString("my_donation_image");
                String home_page_title=documentSnapshot.getString("my_donation_page_title");
                String home_reg=documentSnapshot.getString("my_donation_reg");

                home_page_heading.setText(home_main_heading_st);
                title.setText(home_page_title);
                reg_no.setText(home_reg);
                Discreption.setText(home_page_dis);

                Glide.with(getActivity()).load(home_page_image_url).into(image);

                progressDialog.dismiss();


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
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);


                    image.setImageBitmap(bitmap);
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