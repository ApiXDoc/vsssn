package com.mradkingshop.vsssn.admin.fragment;



import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mradkingshop.vsssn.R;
import com.mradkingshop.vsssn.admin.adapter.PlaneListAdapter;
import com.mradkingshop.vsssn.admin.modal.PlanModal;

import java.util.ArrayList;
import java.util.List;

public class donation_plane_wise_collection extends Fragment {
    private RecyclerView cart_recycler_view;
    private List<PlanModal> productList;
    private Activity activity = getActivity();
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    public PlaneListAdapter latestGovtJobRecyclerAdapter;
    public DocumentSnapshot lastVisible;
    private Boolean isFirstPageFirstLoad = true;
    ProgressDialog progressDialog;
    int posion_int;

    ImageView plane_image;
    TextView plane_name,donation_amt;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.donation_plane_wise_collection_list, container, false);

        productList = new ArrayList<PlanModal>();

        cart_recycler_view = root.findViewById(R.id.recent_withdraw_list);
        firebaseAuth = FirebaseAuth.getInstance();
        latestGovtJobRecyclerAdapter = new PlaneListAdapter(activity, productList, "admin_plane_wise_colection");
        LinearLayoutManager lm1 = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        lm1.setReverseLayout(true);
        lm1.setStackFromEnd(true);
        progressDialog = new ProgressDialog(getActivity());

        cart_recycler_view.setHasFixedSize(true);
        cart_recycler_view.setLayoutManager(lm1);
        cart_recycler_view.setAdapter(latestGovtJobRecyclerAdapter);
        firebaseFirestore = FirebaseFirestore.getInstance();

        plane_name=root.findViewById(R.id.plane_name);
        donation_amt=root.findViewById(R.id.donation_amt);
        plane_image=root.findViewById(R.id.plan_image);

        swipeRefreshLayout=root.findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                productList.clear();
                shop_list();
                data_setup();

                swipeRefreshLayout.setRefreshing(false);

            }
        });

        shop_list();

        data_setup();


        return root;
    }

    private void data_setup() {

        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


     Glide.with(getActivity()).load("https://firebasestorage.googleapis.com/v0/b/vsssn-507e2.appspot.com/o/plan_images%2Fparment_meber.jpg?alt=media&token=db045f69-914c-407f-9385-596151997bf2")
             .into(plane_image);

     plane_name.setText("VSSSN Permanent Member");


        firebaseFirestore.collection("donation_list").whereEqualTo("plane_name","VSSSN Permanent Member")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        QuerySnapshot documentSnapshot=task.getResult();

                        int total_donation_amt=0;

                        for(int i = 0; i < documentSnapshot.getDocuments().size(); i++){


                            total_donation_amt +=  Integer.parseInt((String) documentSnapshot.getDocuments().get(i).get("donation_amount"));

                        }

                        donation_amt.setText("₹"+String.valueOf(total_donation_amt));

                        progressDialog.dismiss();





                    }
                });






    }

    private void shop_list() {

        if (firebaseAuth.getCurrentUser() != null) {

            firebaseFirestore = FirebaseFirestore.getInstance();

            cart_recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    Boolean reachedBottom = !recyclerView.canScrollVertically(1);

                    if (reachedBottom) {


                        loadMorePost();

                    }

                }
            });

            final String uid = firebaseAuth.getCurrentUser().getUid();

            Query firstQuery = firebaseFirestore.collection("plane_list")
                    .orderBy("timestamp", Query.Direction.ASCENDING).limit(10);


            firstQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {


                    Log.d("error", String.valueOf(e));

                    if (isFirstPageFirstLoad) {

                        if (documentSnapshots.isEmpty()) {


                        } else {


                            lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);


                        }


                    }
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            PlanModal blogPost = doc.getDocument().toObject(PlanModal.class).withId(doc.getDocument().getId());
                            String itemId = doc.getDocument().getId();

                            blogPost.setItemId(itemId);


                            if (isFirstPageFirstLoad) {
                                productList.add(0, blogPost);
                            } else {

                                productList.add(0, blogPost);
                            }


                            latestGovtJobRecyclerAdapter.notifyDataSetChanged();

                        }
                    }

                    isFirstPageFirstLoad = false;

                }
            });

        } else {

            Toast.makeText(activity, "no user", Toast.LENGTH_SHORT).show();
        }


    }

    private void loadMorePost() {

        String uid = firebaseAuth.getCurrentUser().getUid();

        Query nextQuery = firebaseFirestore.collection("plane_list")
                .orderBy("timestamp", Query.Direction.ASCENDING)

                .startAfter(lastVisible).limit(10);


        nextQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (!documentSnapshots.isEmpty()) {

                    lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            PlanModal govt_job = doc.getDocument().toObject(PlanModal.class);
                            String itemId = doc.getDocument().getId();

                            govt_job.setItemId(itemId);

                            productList.add(govt_job);

                            latestGovtJobRecyclerAdapter.notifyDataSetChanged();

                        }
                    }

                }

            }
        });
    }


}