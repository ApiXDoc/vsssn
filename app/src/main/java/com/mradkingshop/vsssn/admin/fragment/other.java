package com.mradkingshop.vsssn.admin.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mradkingshop.vsssn.BuildConfig;
import com.mradkingshop.vsssn.R;
import com.mradkingshop.vsssn.admin.activity.Add_notification_act;
import com.mradkingshop.vsssn.admin.activity.List_of_proof_main;
import com.mradkingshop.vsssn.admin.activity.Splash;
import com.mradkingshop.vsssn.admin.activity.donation_manual_act;
import com.mradkingshop.vsssn.admin.activity.other_data_edit_main;

public class other extends Fragment {
    LinearLayout add_notifaction,add_proof,edit_other_detail,donation_manual,log_out_bt,share_icon;
    LinearLayout facebook,instagram,website,share,support,log_out;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    TextView phone_number,name;
    String share_message,website_link,support_mail,instagram_page,facebook_page;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.other,container,false);

        add_notifaction=view.findViewById(R.id.add_notifaction);
        add_proof=view.findViewById(R.id.add_proof);
        edit_other_detail=view.findViewById(R.id.edit_other_detail);
        donation_manual=view.findViewById(R.id.donation_manual);
        log_out_bt=view.findViewById(R.id.log_out_bt);
        firebaseAuth=FirebaseAuth.getInstance();
        share_icon=view.findViewById(R.id.share_icon);
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(getActivity());


        data_set_up();


        share_icon.setOnClickListener(new View.OnClickListener() {
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

        log_out_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signOut();
                Intent intent=new Intent(getActivity(), Splash.class);
                startActivity(intent);
                getActivity().finish();

            }
        });
        donation_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(), donation_manual_act.class);
                startActivity(intent);

            }
        });

        edit_other_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(), other_data_edit_main.class);
                startActivity(intent);

            }
        });


        add_proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(), List_of_proof_main.class);
                startActivity(intent);
            }
        });


        add_notifaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(), Add_notification_act.class);
                startActivity(intent);


            }
        });
        return view;

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
