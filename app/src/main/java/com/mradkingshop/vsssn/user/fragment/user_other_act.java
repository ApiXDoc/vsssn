package com.mradkingshop.vsssn.user.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mradkingshop.vsssn.BuildConfig;
import com.mradkingshop.vsssn.R;
import com.mradkingshop.vsssn.user.activity.User_Otp_Login;
import com.mradkingshop.vsssn.user.activity.User_splash;

public class user_other_act extends Fragment {

    LinearLayout facebook,instagram,website,share,support,log_out;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    TextView phone_number,name;
    String share_message,website_link,support_mail,instagram_page,facebook_page;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.user_other_account,container,false);

        facebook=view.findViewById(R.id.vist_facebook);
        instagram=view.findViewById(R.id.vist_instagram);
        website=view.findViewById(R.id.vist_website);
        share=view.findViewById(R.id.share);
        support=view.findViewById(R.id.support);
        log_out=view.findViewById(R.id.log_out);
        phone_number=view.findViewById(R.id.phone_number);
        name=view.findViewById(R.id.name);



        firebaseAuth= FirebaseAuth.getInstance();
       firebaseFirestore=FirebaseFirestore.getInstance();
       progressDialog=new ProgressDialog(getActivity());




        data_set_up();
        user_data_set_up();



        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signOut();
                Intent intent=new Intent(getActivity(), User_splash.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(facebook_page));
                startActivity(i);
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(instagram_page));
                startActivity(i);

            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(website_link));
                startActivity(i);

            }
        });


        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ support_mail});
                email.putExtra(Intent.EXTRA_SUBJECT, "i need help");
                email.putExtra(Intent.EXTRA_TEXT, "hello vsssn team");

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
                Toast.makeText(getActivity(), support_mail, Toast.LENGTH_SHORT).show();
            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        share_message+" \n App Link: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }
        });

        return view;

    }

    private void user_data_set_up() {
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        firebaseFirestore.collection("user_list").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot documentSnapshot=task.getResult();
                String name_st=documentSnapshot.getString("name");
                String phone_st=documentSnapshot.getString("phone");

                name.setText(name_st+" Ji");
                phone_number.setText("Phone Number:- "+phone_st);
                progressDialog.dismiss();



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(), "user data not geted", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void data_set_up() {

        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        firebaseFirestore.collection("admin").document("1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot documentSnapshot=task.getResult();

                 share_message=documentSnapshot.getString("share_message");
                 facebook_page=documentSnapshot.getString("facebook_page_url");
                 instagram_page=documentSnapshot.getString("instagram_page_url");
                 website_link=documentSnapshot.getString("website_url");
                 support_mail=documentSnapshot.getString("support_email");




                progressDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getActivity(), "data is not geted", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
