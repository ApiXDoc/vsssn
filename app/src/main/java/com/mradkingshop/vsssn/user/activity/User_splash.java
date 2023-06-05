package com.mradkingshop.vsssn.user.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mradkingshop.vsssn.R;
import com.mradkingshop.vsssn.admin.activity.Splash;
import com.mradkingshop.vsssn.admin.activity.login;

import java.nio.charset.StandardCharsets;

public class User_splash extends Activity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_splash);

        Intent intent = getIntent();
        firebaseAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);
        firebaseFirestore=FirebaseFirestore.getInstance();

        if (intent.hasExtra("key")) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(getIntent().getExtras().getString("key")));
            startActivity(i);


        } else {
            new Handler().postDelayed(new Runnable() {


                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    if(firebaseAuth.getCurrentUser()!=null){

                        firebaseFirestore.collection("user_list").document(firebaseAuth.getCurrentUser().getUid().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                DocumentSnapshot documentSnapshot=task.getResult();

                                String form_st=documentSnapshot.getString("details_enter_status");

                                if(documentSnapshot.exists()){


                                    if (form_st.contentEquals("yes")){

                                        Intent i = new Intent(User_splash.this, User_Dasboard.class);
                                        startActivity(i);
                                        finish();

                                    }else if(form_st.contentEquals("no")) {

                                        Intent i = new Intent(User_splash.this, User_Sigin_up.class);
                                        startActivity(i);
                                        finish();

                                    }
                                }else {

                                    Intent i = new Intent(User_splash.this, User_Otp_Login.class);
                                    startActivity(i);
                                    finish();



                                }




                            }
                        });


                    }else {

                        Intent i = new Intent(User_splash.this, User_Otp_Login.class);
                        startActivity(i);
                        finish();
                    }

                }
            }, 2000);


        }
    }

}
