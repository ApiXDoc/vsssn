package com.mradkingshop.vsssn.admin.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mradkingshop.vsssn.R;

import java.util.HashMap;
import java.util.Map;

public class other_data_other_details extends Fragment {

    ProgressDialog progressDialog;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String share_message,website_link,support_mail,instagram_page,facebook_page;
    EditText web_url,insta_url,fb_url,support_url,share_message_et;
    CardView update_bt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.other_data_other_details, container, false);

        progressDialog=new ProgressDialog(getActivity());
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();

        web_url=root.findViewById(R.id.web_url);
        insta_url=root.findViewById(R.id.insta_url);
        fb_url=root.findViewById(R.id.fb_url);
        support_url=root.findViewById(R.id.support_url);
        share_message_et=root.findViewById(R.id.share_message);
        update_bt=root.findViewById(R.id.ok);

        data_set_up();



        update_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Please Wait.........");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
                progressDialog.show();

                Map<String,Object>map=new HashMap<>();
                map.put("website_url",web_url.getText().toString());
                map.put("instagram_page_url",insta_url.getText().toString());
                map.put("facebook_page_url",fb_url.getText().toString());
                map.put("support_email",support_url.getText().toString());
                map.put("share_message",share_message_et.getText().toString());

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
                        Toast.makeText(getActivity(), "data update failed", Toast.LENGTH_SHORT).show();

                    }
                });




            }
        });



        return root;
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

                web_url.setText(website_link);
                insta_url.setText(instagram_page);
                fb_url.setText(facebook_page);
                support_url.setText(support_mail);
                share_message_et.setText(share_message);





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
