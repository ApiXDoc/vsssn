package com.mradkingshop.vsssn.admin.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mradkingshop.vsssn.R;
import com.mradkingshop.vsssn.admin.adapter.StateWiseListAdapter;
import com.mradkingshop.vsssn.admin.modal.Recently_donation;
import com.mradkingshop.vsssn.admin.modal.StateWiseModal;
import com.mradkingshop.vsssn.user.adapter.Real_RecentDonation_adapter;
import com.mradkingshop.vsssn.user.adapter.RecentDonation_adapter;

import java.util.ArrayList;
import java.util.List;

public class donation_sub_state_wise extends Fragment {

    RecyclerView recyclerView;
    private List<StateWiseModal> mProductList;
    StateWiseListAdapter stateWiseListAdapter;
    ProgressDialog progressDialog;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.donation_state_wise,container,false);

        recyclerView = (RecyclerView)view. findViewById(R.id.recent_withdraw_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        firebaseFirestore =FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        mProductList = new ArrayList<StateWiseModal>();



        String[] yourArray = getResources().getStringArray(R.array.state);

        for(int i=0;i<yourArray.length;i++){


            create_list(yourArray[i]);

        }


        swipeRefreshLayout=view.findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mProductList.clear();

                String[] yourArray = getResources().getStringArray(R.array.state);

                for(int i=0;i<yourArray.length;i++){


                    create_list(yourArray[i]);

                }
                swipeRefreshLayout.setRefreshing(false);

            }
        });


        return view;
    }


    private void create_list(String state) {


        firebaseFirestore.collection("donation_list").whereEqualTo("state",state)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        QuerySnapshot documentSnapshot=task.getResult();

                        int total_donation_amt=0;

                        for(int i = 0; i < documentSnapshot.getDocuments().size(); i++){


                            total_donation_amt +=  Integer.parseInt((String) documentSnapshot.getDocuments().get(i).get("donation_amount"));

                        }





                        if(total_donation_amt==0){


                        }else {

                            mProductList.add(new StateWiseModal(state,String.valueOf(documentSnapshot.size()),String.valueOf(total_donation_amt)));
                            stateWiseListAdapter=new StateWiseListAdapter(getActivity(),mProductList);
                            recyclerView.setAdapter(stateWiseListAdapter);

                        }


                    }
                });






    }
}
