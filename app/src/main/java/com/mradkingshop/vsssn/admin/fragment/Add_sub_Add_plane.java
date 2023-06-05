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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class Add_sub_Add_plane extends Fragment {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_donation_plane, container, false);


        plane_name = view.findViewById(R.id.plane_name);
        product_image = view.findViewById(R.id.product_image);

        donation_amount = view.findViewById(R.id.donation_amt);
        summit_bt = view.findViewById(R.id.summit_bt);
        number_pepople_donate=view.findViewById(R.id.number_pepople_donate);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getActivity());


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

                    Toast.makeText(getActivity(), "Please Select Image First", Toast.LENGTH_SHORT).show();
                }


            }
        });
        return view;

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


                    firebaseFirestore.collection("plane_list").document().
                            set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            plane_name.setText("");
                            donation_amount.setText("");

                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Plane created", Toast.LENGTH_SHORT).show();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Plane not created", Toast.LENGTH_SHORT).show();


                        }
                    });


                } else {

                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "image_not_uploaded", Toast.LENGTH_SHORT).show();

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
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);


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