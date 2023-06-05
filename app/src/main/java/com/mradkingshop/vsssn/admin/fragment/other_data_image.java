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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class other_data_image extends Fragment {
    ImageView im1,im2,im3,im4,im5;
    Button bt1,bt2,bt3,bt4,bt5;
    ProgressDialog progressDialog;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    int image_int = 0;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.other_data_slider_images, container, false);


        im1=root.findViewById(R.id.im1);
        im2=root.findViewById(R.id.im2);
        im3=root.findViewById(R.id.im3);
        im4=root.findViewById(R.id.im4);
        im5=root.findViewById(R.id.im5);

        bt1=root.findViewById(R.id.bt1);
        bt2=root.findViewById(R.id.bt2);
        bt3=root.findViewById(R.id.bt3);
        bt4=root.findViewById(R.id.bt4);
        bt5=root.findViewById(R.id.bt5);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(getActivity());

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                image_int=1;

                chooseImage();

            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                image_int=2;

                chooseImage();

            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                image_int=3;

                chooseImage();

            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                image_int=4;

                chooseImage();

            }
        });
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                image_int=5;

                chooseImage();

            }
        });



        set_data();





        return root;
    }

    private void set_data() {

        progressDialog.setMessage("Please Wait.....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        firebaseFirestore.collection("admin").document("image_home").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot documentSnapshot=task.getResult();

                String im1_url=documentSnapshot.getString("home_image1");
                String im2_url=documentSnapshot.getString("home_image2");
                String im3_url=documentSnapshot.getString("home_image3");
                String im4_url=documentSnapshot.getString("home_image4");
                String im5_url=documentSnapshot.getString("home_image5");


                Glide.with(getActivity()).load(im1_url).into(im1);
                Glide.with(getActivity()).load(im2_url).into(im2);
                Glide.with(getActivity()).load(im3_url).into(im3);
                Glide.with(getActivity()).load(im4_url).into(im4);
                Glide.with(getActivity()).load(im5_url).into(im5);
                progressDialog.dismiss();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(), "data not found", Toast.LENGTH_SHORT).show();


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


                    im1.setImageBitmap(bitmap);
                    image_int=0;

                    upload(1);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if(image_int==2){


                filePath = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);


                    im2.setImageBitmap(bitmap);
                    image_int=0;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else if(image_int==3){


                filePath = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);


                    im3.setImageBitmap(bitmap);
                    image_int=0;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else if(image_int==4){


                filePath = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);


                    im4.setImageBitmap(bitmap);
                    image_int=0;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else if(image_int==5){


                filePath = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);


                    im5.setImageBitmap(bitmap);
                    image_int=0;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }}

    private void upload(int i) {

        progressDialog.setMessage("please wait.....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
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

                    if(i==1){

                        map.put("home_image1",main_product_image1_url1);



                        firebaseFirestore.collection("admin")
                                .document("image_home").
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



                    }else if(i==2){

                        map.put("home_image2",main_product_image1_url1);



                        firebaseFirestore.collection("admin")
                                .document("image_home").
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



                    }else if(i==3){

                        map.put("home_image3",main_product_image1_url1);



                        firebaseFirestore.collection("admin")
                                .document("image_home").
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


                    }else if(i==4){
                        map.put("home_image4",main_product_image1_url1);



                        firebaseFirestore.collection("admin")
                                .document("image_home").
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



                    }else if(i==5){
                        map.put("home_image5",main_product_image1_url1);



                        firebaseFirestore.collection("admin")
                                .document("image_home").
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



                    }


                } else {

                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "image_not_uploaded", Toast.LENGTH_SHORT).show();

                }


            }
        });


    }
}
