package com.mradkingshop.vsssn.user.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mradkingshop.vsssn.R;

import java.util.HashMap;
import java.util.Map;

public class User_Sigin_up extends Activity {

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    EditText name,email_id,phone,pin_code,address;
    FirebaseFirestore firebaseFirestore;
    CardView ok;
    Spinner state_spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_sigin_up);

        name = findViewById(R.id.name);
        email_id = findViewById(R.id.email_id);
        pin_code = findViewById(R.id.pin_code);
        state_spinner = findViewById(R.id.state_spinner);
        phone = findViewById(R.id.phone_number);
        address = findViewById(R.id.address);
        ok = findViewById(R.id.ok);


        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseFirestore = FirebaseFirestore.getInstance();


            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    progressDialog.setMessage("Please Wait........");
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    if (TextUtils.isEmpty(name.getText().toString())) {

                        Toast.makeText(getApplicationContext(), "please enter your name", Toast.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(address.getText().toString())) {

                        Toast.makeText(getApplicationContext(), "please enter your address", Toast.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(email_id.getText().toString())) {

                        Toast.makeText(getApplicationContext(), "please enter your Email", Toast.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(pin_code.getText().toString())) {

                        Toast.makeText(getApplicationContext(), "please enter your pin code", Toast.LENGTH_SHORT).show();

                    } else {


                        Map<String, Object> map = new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("email_id", email_id.getText().toString());
                        map.put("pin_code", pin_code.getText().toString());
                        map.put("state", state_spinner.getSelectedItem().toString());
                        map.put("Membership Status", "not parmanet member");
                        map.put("phone", firebaseAuth.getCurrentUser().getPhoneNumber());
                        map.put("uid", firebaseAuth.getCurrentUser().getUid());
                        map.put("timestamp", FieldValue.serverTimestamp());
                        map.put("status", "member");
                        map.put("details_enter_status", "yes");
                        map.put("address",address.getText().toString());

                        firebaseFirestore.collection("user_list")
                                .document(firebaseAuth.getCurrentUser().getUid()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(@NonNull Void unused) {


                                FirebaseMessaging.getInstance().subscribeToTopic("user").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Intent intent = new Intent(User_Sigin_up.this, User_Dasboard.class);
                                        startActivity(intent);
                                        finish();
                                        progressDialog.dismiss();
                                    }
                                });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(getApplicationContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });


                    }

                }
            });


        }
    }


