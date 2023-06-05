package com.mradkingshop.vsssn.user.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mradkingshop.vsssn.R;
import com.mradkingshop.vsssn.admin.activity.Dasboard_Main;
import com.mradkingshop.vsssn.admin.activity.Singin_up;
import com.mradkingshop.vsssn.admin.activity.login;

public class User_login extends Activity {
    Button log_in_bt;
    TextView sigin_up_txt;
    ProgressDialog progressDialog;
    EditText phone_number_et,password_et;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        log_in_bt=findViewById(R.id.log_in);
        sigin_up_txt=findViewById(R.id.sigin_up_txt);
        phone_number_et=findViewById(R.id.username);
        password_et=findViewById(R.id.password);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        if(firebaseAuth.getCurrentUser()!=null){



            Intent intent=new Intent(User_login.this, User_Dasboard.class);
            startActivity(intent);
            finish();
        }


        sigin_up_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent=new Intent(User_login.this, User_Sigin_up.class);
                startActivity(intent);

            }
        });

        log_in_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                progressDialog.setMessage("Please Wait........");

                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword(phone_number_et.getText().toString()+"@gmail.com",password_et.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(@NonNull AuthResult authResult) {


                        Intent intent=new Intent(User_login.this,User_Dasboard.class);
                        startActivity(intent);
                        finish();
                        progressDialog.dismiss();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });



    }
}
