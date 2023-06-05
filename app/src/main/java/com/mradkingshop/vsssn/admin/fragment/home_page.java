package com.mradkingshop.vsssn.admin.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mradkingshop.vsssn.R;
import com.mradkingshop.vsssn.admin.activity.today_donation_list;

public class home_page extends Fragment {

    TextView total_member,total_donation,total_spend,today_donation,today_donation_txt,total_donation_txt;
    LinearLayout see_more_bt;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.home_page,container,false);

        total_member=root.findViewById(R.id.total_member);
        total_donation=root.findViewById(R.id.total_donation);
        total_spend=root.findViewById(R.id.total_spend);
        today_donation=root.findViewById(R.id.today_donation);
        today_donation_txt=root.findViewById(R.id.today_donation_txt);
        total_donation_txt=root.findViewById(R.id.total_donation_txt);
        see_more_bt=root.findViewById(R.id.see_more_bt);

        firebaseAuth=FirebaseAuth.getInstance();
        swipeRefreshLayout=root.findViewById(R.id.swiperefresh);
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(getActivity());


        data_set();

        see_more_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(), today_donation_list.class);
                startActivity(intent);

            }
        });



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                data_set();

                swipeRefreshLayout.setRefreshing(false);

            }
        });





        return root;

    }

    private void data_set() {

        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        firebaseFirestore.collection("user_list")
                 .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        QuerySnapshot documentSnapshot=task.getResult();


                        total_member.setText(String.valueOf(documentSnapshot.size()));

                      total_donation_set_up();


                    }
                });




    }

    private void total_donation_set_up() {



        firebaseFirestore.collection("donation_list")
                 .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        QuerySnapshot documentSnapshot=task.getResult();

                        int total_withdrawl=0;

                        for(int i = 0; i < documentSnapshot.getDocuments().size(); i++){


                            total_withdrawl +=  Integer.parseInt((String) documentSnapshot.getDocuments().get(i).get("donation_amount"));

                        }


                        total_donation_txt.setText("₹"+String.valueOf(total_withdrawl));
                        total_donation.setText("₹"+String.valueOf(total_withdrawl));

                       total_spend_set_up();


                    }
                });

    }

    private void total_spend_set_up() {

        firebaseFirestore.collection("proof_list")
                  .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        QuerySnapshot documentSnapshot=task.getResult();

                        int total_withdrawl=0;

                        for(int i = 0; i < documentSnapshot.getDocuments().size(); i++){


                            total_withdrawl +=  Integer.parseInt((String) documentSnapshot.getDocuments().get(i).get("proof_spending"));

                        }


                        total_spend.setText("₹"+String.valueOf(total_withdrawl));

                     today_donation_set_up();


                    }
                });
    }

    private void today_donation_set_up() {
        DatePicker datePicker=new DatePicker(getActivity());
        String current_date = datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear();


        firebaseFirestore.collection("donation_list")
                .whereEqualTo("donation_date",current_date)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        QuerySnapshot documentSnapshot=task.getResult();

                        int total_withdrawl=0;

                        for(int i = 0; i < documentSnapshot.getDocuments().size(); i++){


                            total_withdrawl +=  Integer.parseInt((String) documentSnapshot.getDocuments().get(i).get("donation_amount"));

                        }


                        today_donation.setText("₹"+String.valueOf(total_withdrawl));
                        today_donation_txt.setText("₹"+String.valueOf(total_withdrawl));

                        progressDialog.dismiss();



                    }
                });
    }
}
