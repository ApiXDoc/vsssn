package com.mradkingshop.vsssn.user.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.mradkingshop.vsssn.user.activity.User_Donation_Payment_act;

import java.util.ArrayList;
import java.util.List;

public class user_my_donation_permanent_member extends Fragment {

    FirebaseFirestore firebaseFirestore;
    private RecyclerView cart_recycler_view;
    private List<PlanModal> productList;
    String donation_type_st="no_custom";
    private Activity activity = getActivity();

    private FirebaseAuth firebaseAuth;
    public PlaneListAdapter latestGovtJobRecyclerAdapter;
    public DocumentSnapshot lastVisible;
    private Boolean isFirstPageFirstLoad = true;
    ProgressDialog progressDialog;
    String total_donation_amt_st;
    int posion_int;
    EditText donation_et;
    CardView donate_more,donate_now;
    LinearLayout donation_et_ly;
    TextView donation_amt_pm;
    TextView heading,reg,title,discreption;
    ImageView image;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.user_be_parmanet_member,container,false);

        productList = new ArrayList<PlanModal>();

        cart_recycler_view = view.findViewById(R.id.recent_withdraw_list);
        firebaseAuth = FirebaseAuth.getInstance();
        latestGovtJobRecyclerAdapter = new PlaneListAdapter( activity,productList,"parmament");
        LinearLayoutManager lm1 = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        lm1.setReverseLayout(true);
        lm1.setStackFromEnd(true);
        progressDialog =new ProgressDialog(getActivity());
        donation_amt_pm=view.findViewById(R.id.donation_amt_pm);
        donate_now=view.findViewById(R.id.donate_now);
        donate_more=view.findViewById(R.id.donate_more);

        donation_et_ly=view.findViewById(R.id.donation_et_ly);
        donation_et=view.findViewById(R.id.donation_et);

        heading=view.findViewById(R.id.heading);
        reg=view.findViewById(R.id.reg);
        title=view.findViewById(R.id.title);
        discreption=view.findViewById(R.id.discreption);
        image=view.findViewById(R.id.image);



        cart_recycler_view.setHasFixedSize(true);
        cart_recycler_view.setLayoutManager(lm1);
        cart_recycler_view.setAdapter(latestGovtJobRecyclerAdapter);
        firebaseFirestore=FirebaseFirestore.getInstance();

        cart_recycler_view.setNestedScrollingEnabled(false);


        data_setUp();

        shop_list();

        donate_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                donation_et_ly.setVisibility(View.VISIBLE);
                donate_more.setVisibility(View.GONE);
                donation_amt_pm.setText("No Customize Donation");
                donation_type_st="yes_custom";


            }
        });

        donation_amt_pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                donation_et_ly.setVisibility(View.GONE);
                donate_more.setVisibility(View.VISIBLE);
                donation_amt_pm.setText("₹"+total_donation_amt_st);
                donation_type_st="no_custom";


            }
        });




        donate_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(donation_type_st.contentEquals("no_custom")){
                    Intent intent=new Intent(getActivity(), User_Donation_Payment_act.class);
                    intent.putExtra("plane_name","VSSSN Permanent Member");
                    intent.putExtra("donation_amount",total_donation_amt_st);
                    intent.putExtra("plane_image","https://firebasestorage.googleapis.com/v0/b/vsssn-507e2.appspot.com/o/plan_images%2Fparment_meber.jpg?alt=media&token=db045f69-914c-407f-9385-596151997bf2");
                    intent.putExtra("number_pepople_donate","101");
                    intent.putExtra("item_id","permanent");
                    getActivity().startActivity(intent);

                }else if(donation_type_st.contentEquals("yes_custom")){

                    Intent intent=new Intent(getActivity(), User_Donation_Payment_act.class);
                    intent.putExtra("plane_name","VSSSN Permanent Member");
                    intent.putExtra("donation_amount",donation_et.getText().toString());
                    intent.putExtra("plane_image","https://firebasestorage.googleapis.com/v0/b/vsssn-507e2.appspot.com/o/plan_images%2Fparment_meber.jpg?alt=media&token=db045f69-914c-407f-9385-596151997bf2");
                    intent.putExtra("number_pepople_donate","101");
                    intent.putExtra("item_id","permanent");
                    getActivity().startActivity(intent);

                }





            }
        });




        return view;

    }


    private void data_setUp() {
        progressDialog.setMessage("Please Wait........");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        firebaseFirestore.collection("plane_list")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        QuerySnapshot documentSnapshot=task.getResult();

                        int total_donation_amt=0;

                        for(int i = 0; i < documentSnapshot.getDocuments().size(); i++){


                            total_donation_amt +=  Integer.parseInt((String) documentSnapshot.getDocuments().get(i).get("donation_amount"));

                        }

                        total_donation_amt_st=String.valueOf(total_donation_amt);
                        donation_amt_pm.setText("₹"+String.valueOf(total_donation_amt));
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

            Query firstQuery =   firebaseFirestore.collection("plane_list")
                    .orderBy("timestamp", Query.Direction.ASCENDING).limit(10);




            firstQuery.addSnapshotListener( new EventListener<QuerySnapshot>() {
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

        Query nextQuery =  firebaseFirestore.collection("plane_list")
                .orderBy("timestamp", Query.Direction.ASCENDING)

                .startAfter(lastVisible).limit(10);




        nextQuery.addSnapshotListener( new EventListener<QuerySnapshot>() {
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


    private void data_set() {

        progressDialog.setMessage("Please Wait.....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        firebaseFirestore.collection("admin").document("1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                DocumentSnapshot documentSnapshot=task.getResult();

                String home_main_heading_st=documentSnapshot.getString("my_donation_main_heading");
                String home_page_dis=documentSnapshot.getString("my_donation_page_dis");
                String home_page_image_url=documentSnapshot.getString("my_donation_image");
                String home_page_title=documentSnapshot.getString("my_donation_page_title");
                String home_reg=documentSnapshot.getString("my_donation_reg");

                Glide.with(getActivity()).load(home_page_image_url).into(image);
                heading.setText(home_main_heading_st);
                title.setText(home_page_title);
                discreption.setText(home_page_dis);
                reg.setText("Reg:-"+home_reg);

                progressDialog.dismiss();




            }
        });

    }


}
