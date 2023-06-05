package com.mradkingshop.vsssn.admin.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mradkingshop.vsssn.R;

import java.util.HashMap;
import java.util.Map;

public class donation_manual_act extends Activity {

    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    Switch switch_bt;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_manual);

        switch_bt=findViewById(R.id.switch_bt);
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);

        toolbar.setTitle("Donation Manual act");



        data_set_up();

        switch_bt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(compoundButton.isChecked()){

                    data_update_yes();

                }else {

                    data_update_no();
                }


            }
        });


    }

    private void data_update_no() {

        Map<String,Object>map=new HashMap<>();
        map.put("recently_donation_list_show","no");

        progressDialog.setMessage("Please Wait.....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        firebaseFirestore.collection("admin").document("1").update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(@NonNull Void unused) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Your Fake is Inactive", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "data is not upadted", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void data_update_yes() {

        Map<String,Object>map=new HashMap<>();
        map.put("recently_donation_list_show","yes");

        progressDialog.setMessage("Please Wait.....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        firebaseFirestore.collection("admin").document("1").update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(@NonNull Void unused) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Your Fake List Activated", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "data is not upadted", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void data_set_up() {
       progressDialog.setMessage("Please Wait.....");
       progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        firebaseFirestore.collection("admin").document("1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot documentSnapshot=task.getResult();

                String recently_donation_list_show=documentSnapshot.getString("recently_donation_list_show");

                if(recently_donation_list_show.contentEquals("yes")){

                    switch_bt.setChecked(true);
                    progressDialog.dismiss();

                }else if(recently_donation_list_show.contentEquals("no")){

                    switch_bt.setChecked(false);
                    progressDialog.dismiss();

                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(), "data is not geted", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
