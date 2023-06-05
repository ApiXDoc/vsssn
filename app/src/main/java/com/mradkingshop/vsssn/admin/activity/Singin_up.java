package com.mradkingshop.vsssn.admin.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mradkingshop.vsssn.R;

import java.util.HashMap;
import java.util.Map;

public class Singin_up extends Activity {
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    EditText name,password,conform_password,phone;
    FirebaseFirestore firebaseFirestore;
    CardView ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sgin_up_act);

        name=findViewById(R.id.name);
        password=findViewById(R.id.password);
        conform_password=findViewById(R.id.conform_password);
        phone=findViewById(R.id.phone_number);
        ok=findViewById(R.id.ok);


        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();

        firebaseFirestore= FirebaseFirestore.getInstance();


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Please Wait......");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
                progressDialog.show();

                if(password.getText().toString().contentEquals(conform_password.getText().toString())){




                    firebaseAuth.createUserWithEmailAndPassword(phone.getText().toString()+"@gmail.com",password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(@NonNull AuthResult authResult) {

                            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                                @Override
                                public void onComplete(@NonNull Task<String> task) {

                                    if (!task.isSuccessful()) {
                                        return;
                                    }
                                    String token = task.getResult();


                                    Map<String,Object>map=new HashMap<>();
                                    map.put("name",name.getText().toString());
                                    map.put("phone_number",phone.getText().toString());
                                    map.put("password",password.getText().toString());
                                    map.put("message_token",token);


                                    firebaseFirestore.collection("admin")
                                            .document("1").update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(@NonNull Void unused) {

                                            Intent intent=new Intent(Singin_up.this,Dasboard_Main.class);
                                            startActivity(intent);
                                            finish();

                                            progressDialog.dismiss();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Toast.makeText(getApplicationContext(), "data is not seted", Toast.LENGTH_SHORT).show();
                                        }
                                    });






                                }
                            });






                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getApplicationContext(), "sigin up is not completed", Toast.LENGTH_SHORT).show();
                        }
                    });


                }else {


                    Toast.makeText(getApplicationContext(), "your password is not match", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}
