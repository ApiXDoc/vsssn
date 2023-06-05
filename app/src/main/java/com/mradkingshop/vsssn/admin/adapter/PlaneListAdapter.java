package com.mradkingshop.vsssn.admin.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mradkingshop.vsssn.R;
import com.mradkingshop.vsssn.admin.activity.Add_proof_form;
import com.mradkingshop.vsssn.admin.activity.Edit_Plane_act;
import com.mradkingshop.vsssn.admin.activity.Read_more_proof;
import com.mradkingshop.vsssn.admin.modal.PlanModal;
import com.mradkingshop.vsssn.user.activity.User_Donation_Payment_act;
import com.mradkingshop.vsssn.user.activity.user_old_proofs_list;

import java.net.URLEncoder;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlaneListAdapter extends RecyclerView.Adapter<PlaneListAdapter.ViewHolder>{

    private Context mCtx;

    List<PlanModal> noteslist;
    ProgressDialog progressDialog;
    private FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String key;

    public PlaneListAdapter(Context mCtx, List<PlanModal> noteslist, String key) {
        this.mCtx = mCtx;
        this.noteslist = noteslist;
        this.key = key;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(key.contentEquals("parmament")){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_parmanet_row, parent, false);
            mCtx = parent.getContext();
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseAuth= FirebaseAuth.getInstance();
            progressDialog=new ProgressDialog(mCtx);
            return new ViewHolder(view);

        }else if(key.contentEquals("memberlist")){

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_member_list_row, parent, false);
            mCtx = parent.getContext();
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseAuth= FirebaseAuth.getInstance();
            progressDialog=new ProgressDialog(mCtx);
            return new ViewHolder(view);

        } else if(key.contentEquals("admin_proof_list")){

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.proof_row, parent, false);
            mCtx = parent.getContext();
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseAuth= FirebaseAuth.getInstance();
            progressDialog=new ProgressDialog(mCtx);
            return new ViewHolder(view);

        }else if(key.contentEquals("user_proof_list")){

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.proof_row, parent, false);
            mCtx = parent.getContext();
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseAuth= FirebaseAuth.getInstance();
            progressDialog=new ProgressDialog(mCtx);
            return new ViewHolder(view);


        }



        else {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plane_row, parent, false);
            mCtx = parent.getContext();
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseAuth= FirebaseAuth.getInstance();
            progressDialog=new ProgressDialog(mCtx);
            return new ViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {



        if(key.contentEquals("user_plane_list")){
            holder.glide.with(mCtx).load(noteslist.get(position).getPlane_image()).into(holder.plane_image);

            holder.plane_Name.setText(noteslist.get(position).getPlane_name());
            holder.donate_amt.setText("₹"+noteslist.get(position).getDonation_amount());
            holder.number_of_people_donate.setText(noteslist.get(position).getNumber_pepople_donate());

            holder.edit_bt.setVisibility(View.GONE);
            holder.deleat_bt.setVisibility(View.GONE);
            holder.donate_bt.setVisibility(View.VISIBLE);

            holder.donate_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(mCtx, User_Donation_Payment_act.class);
                    intent.putExtra("plane_name",noteslist.get(position).getPlane_name());
                    intent.putExtra("donation_amount",noteslist.get(position).getDonation_amount());
                    intent.putExtra("plane_image",noteslist.get(position).getPlane_image());
                    intent.putExtra("number_pepople_donate",noteslist.get(position).getNumber_pepople_donate());
                    intent.putExtra("item_id",noteslist.get(position).getItemId());
                    mCtx.startActivity(intent);

                }
            });

            holder.edit_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent=new Intent(mCtx, Edit_Plane_act.class);
                    intent.putExtra("plane_image",noteslist.get(position).getPlane_image());
                    intent.putExtra("donation_amt",noteslist.get(position).getDonation_amount());
                    intent.putExtra("number_of_people_donate",noteslist.get(position).getNumber_pepople_donate());
                    intent.putExtra("item_id",noteslist.get(position).getItemId());
                    intent.putExtra("plan_name",noteslist.get(position).getPlane_name());
                    mCtx.startActivity(intent);

                }
            });




            holder.deleat_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new AlertDialog.Builder(mCtx)
                            .setTitle("Delete entry")
                            .setMessage("Are you sure you want to delete this Plane?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation

                                    progressDialog.setMessage("Please Wait....");
                                    progressDialog.setCancelable(false);
                                    progressDialog.setCanceledOnTouchOutside(false);
                                    progressDialog.show();
                                    firebaseFirestore.collection("plane_list")
                                            .document(noteslist.get(position).getItemId())
                                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                            progressDialog.dismiss();
                                            noteslist.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, noteslist.size());



                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            progressDialog.dismiss();
                                            Toast.makeText(mCtx, "try aging not deleted", Toast.LENGTH_SHORT).show();


                                        }
                                    });

                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation

                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();



                }
            });





        }else if(key.contentEquals("user_my_donation")){

            holder.glide.with(mCtx).load(noteslist.get(position).getPlane_image()).into(holder.plane_image);

            holder.plane_Name.setText(noteslist.get(position).getPlane_name());
            holder.donate_amt.setText("₹"+noteslist.get(position).getDonation_amount());
            holder.number_of_people_donate.setText(noteslist.get(position).getNumber_pepople_donate());

            holder.edit_bt.setVisibility(View.GONE);
            holder.deleat_bt.setVisibility(View.GONE);
            holder.donate_bt.setVisibility(View.GONE);

            holder.name_my_donation.setText(noteslist.get(position).getName());
            holder.date_my_donation.setText(noteslist.get(position).getDonation_date());
            holder.date_layout_my_donation.setVisibility(View.VISIBLE);
            holder.name_layout_my_donation.setVisibility(View.VISIBLE);
            holder.view.setVisibility(View.VISIBLE);


            holder.donate_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(mCtx, User_Donation_Payment_act.class);
                    intent.putExtra("plane_name",noteslist.get(position).getPlane_name());
                    intent.putExtra("donation_amount",noteslist.get(position).getDonation_amount());
                    intent.putExtra("plane_image",noteslist.get(position).getPlane_image());
                    intent.putExtra("number_pepople_donate",noteslist.get(position).getNumber_pepople_donate());
                    intent.putExtra("item_id",noteslist.get(position).getItemId());
                    mCtx.startActivity(intent);

                }
            });

            holder.edit_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent=new Intent(mCtx, Edit_Plane_act.class);
                    intent.putExtra("plane_image",noteslist.get(position).getPlane_image());
                    intent.putExtra("donation_amt",noteslist.get(position).getDonation_amount());
                    intent.putExtra("number_of_people_donate",noteslist.get(position).getNumber_pepople_donate());
                    intent.putExtra("item_id",noteslist.get(position).getItemId());
                    intent.putExtra("plan_name",noteslist.get(position).getPlane_name());
                    mCtx.startActivity(intent);

                }
            });




            holder.deleat_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new AlertDialog.Builder(mCtx)
                            .setTitle("Delete entry")
                            .setMessage("Are you sure you want to delete this Plane?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation

                                    progressDialog.setMessage("Please Wait....");
                                    progressDialog.setCancelable(false);
                                    progressDialog.setCanceledOnTouchOutside(false);
                                    progressDialog.show();
                                    firebaseFirestore.collection("plane_list")
                                            .document(noteslist.get(position).getItemId())
                                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                            progressDialog.dismiss();
                                            noteslist.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, noteslist.size());



                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            progressDialog.dismiss();
                                            Toast.makeText(mCtx, "try aging not deleted", Toast.LENGTH_SHORT).show();


                                        }
                                    });

                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation

                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();



                }
            });




        }else if(key.contentEquals("admin_donation")){

            holder.glide.with(mCtx).load(noteslist.get(position).getPlane_image()).into(holder.plane_image);

            holder.plane_Name.setText(noteslist.get(position).getPlane_name());
            holder.donate_amt.setText("₹"+noteslist.get(position).getDonation_amount());
            holder.number_of_people_donate.setText(noteslist.get(position).getNumber_pepople_donate());

            holder.edit_bt.setVisibility(View.VISIBLE);
            holder.deleat_bt.setVisibility(View.VISIBLE);
            holder.name_layout_my_donation.setVisibility(View.VISIBLE);
            holder.date_layout_my_donation.setVisibility(View.VISIBLE);

            holder.name_my_donation.setVisibility(View.VISIBLE);
            holder.state_layout.setVisibility(View.VISIBLE);
            holder.state_name.setText(noteslist.get(position).getState());


            holder.donate_bt.setVisibility(View.GONE);

            holder.name_my_donation.setText(noteslist.get(position).getName());
            holder.date_my_donation.setText(noteslist.get(position).getDonation_date());

             holder.edit_bt_txt.setText("CAll");
             holder.delet_bt_txt.setText("Whats App");


            holder.edit_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+noteslist.get(position).getPhone().toString()));
                    mCtx.startActivity(intent);

                }
            });




            holder.deleat_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PackageManager packageManager = mCtx.getPackageManager();
                    Intent i = new Intent(Intent.ACTION_VIEW);

                    try {
                        String url = "https://api.whatsapp.com/send?phone="+ noteslist.get(position).getPhone() +"&text=" + URLEncoder.encode("hi", "UTF-8");
                        i.setPackage("com.whatsapp");
                        i.setData(Uri.parse(url));
                        if (i.resolveActivity(packageManager) != null) {
                            mCtx.startActivity(i);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }


        else if(key.contentEquals("parmament")){

            holder.s_no.setText("*");
            holder.plane_name_pm.setText(noteslist.get(position).getPlane_name());
            holder.donation_amt_pm.setText(noteslist.get(position).getDonation_amount());
        }
        else if(key.contentEquals("admin_plane_list_proof")){

            holder.glide.with(mCtx).load(noteslist.get(position).getPlane_image()).into(holder.plane_image);

            holder.plane_Name.setText(noteslist.get(position).getPlane_name());
            holder.donate_amt.setText("₹"+noteslist.get(position).getDonation_amount());
            holder.number_of_people_donate.setText(noteslist.get(position).getNumber_pepople_donate());

            holder.donate_bt.setVisibility(View.GONE);
            holder.deleat_bt.setVisibility(View.VISIBLE);
            holder.edit_bt.setVisibility(View.GONE);


            holder.date_layout_my_donation.setVisibility(View.GONE);
            holder.name_layout_my_donation.setVisibility(View.GONE);
            holder.view.setVisibility(View.GONE);
            holder.delet_bt_txt.setText("Add Proof");


            holder.deleat_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(mCtx, Add_proof_form.class);
                    intent.putExtra("plane_name",noteslist.get(position).getPlane_name());
                    intent.putExtra("item_id",noteslist.get(position).getItemId());
                    intent.putExtra("donation_amount",noteslist.get(position).getDonation_amount());
                    intent.putExtra("plane_image",noteslist.get(position).getPlane_image());
                    intent.putExtra("number_pepople_donate",noteslist.get(position).getNumber_pepople_donate());
                    mCtx.startActivity(intent);

                }
            });



        }else if(key.contentEquals("user_proof_list")){

            holder.glide.with(mCtx).load(noteslist.get(position).getProof_image_url()).into(holder.proof_image);
            holder.glide.with(mCtx).load(noteslist.get(position).getPlane_image()).into(holder.proof_plane_image);

            holder.plane_name_proof.setText(noteslist.get(position).getPlane_name());
            holder.spending_proof.setText(noteslist.get(position).getProof_spending());
            holder.date_proof.setText(noteslist.get(position).getDate());
            holder.proof_heading.setText(noteslist.get(position).getProof_heading());
            holder.proof_dicreption.setText(noteslist.get(position).getProof_discreption());

            holder.proof_delete_bt.setVisibility(View.GONE);
            holder.proof_edit_bt.setVisibility(View.GONE);



            holder.proof_read_more_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent=new Intent(mCtx, Read_more_proof.class);
                    intent.putExtra("titel",noteslist.get(position).getProof_heading());
                    intent.putExtra("dis",noteslist.get(position).getProof_discreption());
                    intent.putExtra("image",noteslist.get(position).getProof_image_url());
                    mCtx.startActivity(intent);


                }
            });




        }



        else if(key.contentEquals("user_plane_list_proof")){

            holder.glide.with(mCtx).load(noteslist.get(position).getPlane_image()).into(holder.plane_image);

            holder.plane_Name.setText(noteslist.get(position).getPlane_name());
            holder.donate_amt.setVisibility(View.GONE);
            holder.number_of_people_donate.setVisibility(View.GONE);

            holder.donate_bt.setVisibility(View.GONE);
            holder.deleat_bt.setVisibility(View.VISIBLE);
            holder.edit_bt.setVisibility(View.GONE);
            holder.donation_amt_txt.setVisibility(View.GONE);
            holder.number_pepople_donate_txt.setVisibility(View.GONE);


            holder.date_layout_my_donation.setVisibility(View.GONE);
            holder.name_layout_my_donation.setVisibility(View.GONE);
            holder.view.setVisibility(View.GONE);
            holder.delet_bt_txt.setText("View Proofs");


            holder.deleat_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(mCtx, user_old_proofs_list.class);
                    intent.putExtra("plane_name",noteslist.get(position).getPlane_name());
                        mCtx.startActivity(intent);

                }
            });



        }else if(key.contentEquals("user_proof_list")){

            holder.glide.with(mCtx).load(noteslist.get(position).getProof_image_url()).into(holder.proof_image);
            holder.glide.with(mCtx).load(noteslist.get(position).getPlane_image()).into(holder.proof_plane_image);

            holder.plane_name_proof.setText(noteslist.get(position).getPlane_name());
            holder.spending_proof.setText(noteslist.get(position).getProof_spending());
            holder.date_proof.setText(noteslist.get(position).getDate());
            holder.proof_heading.setText(noteslist.get(position).getProof_heading());
            holder.proof_dicreption.setText(noteslist.get(position).getProof_discreption());

            holder.proof_delete_bt.setVisibility(View.GONE);
            holder.proof_edit_bt.setVisibility(View.GONE);



            holder.proof_read_more_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent=new Intent(mCtx, Read_more_proof.class);
                    intent.putExtra("titel",noteslist.get(position).getProof_heading());
                    intent.putExtra("dis",noteslist.get(position).getProof_discreption());
                    intent.putExtra("image",noteslist.get(position).getProof_image_url());
                    mCtx.startActivity(intent);


                }
            });




        }

        else if (key.contentEquals("admin_proof_list")){



            holder.glide.with(mCtx).load(noteslist.get(position).getProof_image_url()).into(holder.proof_image);
            holder.glide.with(mCtx).load(noteslist.get(position).getPlane_image()).into(holder.proof_plane_image);

            holder.plane_name_proof.setText(noteslist.get(position).getPlane_name());
            holder.spending_proof.setText(noteslist.get(position).getProof_spending());
            holder.date_proof.setText(noteslist.get(position).getDate());
            holder.proof_heading.setText(noteslist.get(position).getProof_heading());
            holder.proof_dicreption.setText(noteslist.get(position).getProof_discreption());


            holder.proof_edit_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mCtx, Add_proof_form.class);
                    intent.putExtra("amt_spend",noteslist.get(position).getProof_spending());
                    intent.putExtra("titel",noteslist.get(position).getProof_heading());
                    intent.putExtra("dis",noteslist.get(position).getProof_discreption());
                    intent.putExtra("image",noteslist.get(position).getProof_image_url());
                   intent.putExtra("itemId",noteslist.get(position).getItemId());
                    mCtx.startActivity(intent);

                }
            });

            holder.proof_read_more_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent=new Intent(mCtx, Read_more_proof.class);
                    intent.putExtra("titel",noteslist.get(position).getProof_heading());
                    intent.putExtra("dis",noteslist.get(position).getProof_discreption());
                    intent.putExtra("image",noteslist.get(position).getProof_image_url());
                    mCtx.startActivity(intent);


                }
            });
            holder.proof_delete_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    new AlertDialog.Builder(mCtx)
                            .setTitle("Delete entry")
                            .setMessage("Are you sure you want to delete this Plane?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation

                                    progressDialog.setMessage("Please Wait....");
                                    progressDialog.setCancelable(false);
                                    progressDialog.setCanceledOnTouchOutside(false);
                                    progressDialog.show();
                                    firebaseFirestore.collection("proof_list")
                                            .document(noteslist.get(position).getItemId())
                                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                            progressDialog.dismiss();
                                            noteslist.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, noteslist.size());



                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            progressDialog.dismiss();
                                            Toast.makeText(mCtx, "try aging not deleted", Toast.LENGTH_SHORT).show();


                                        }
                                    });

                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation

                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();



                }
            });






        }

        else if(key.contentEquals("admin_plane_list")){


            holder.glide.with(mCtx).load(noteslist.get(position).getPlane_image()).into(holder.plane_image);

            holder.plane_Name.setText(noteslist.get(position).getPlane_name());
            holder.donate_amt.setText("₹"+noteslist.get(position).getDonation_amount());
            holder.number_of_people_donate.setText(noteslist.get(position).getNumber_pepople_donate());

             holder.donate_bt.setVisibility(View.GONE);

            holder.name_my_donation.setText(noteslist.get(position).getName());
            holder.date_my_donation.setText(noteslist.get(position).getDonation_date());
            holder.date_layout_my_donation.setVisibility(View.GONE);
            holder.name_layout_my_donation.setVisibility(View.GONE);
            holder.view.setVisibility(View.GONE);


            holder.donate_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(mCtx, User_Donation_Payment_act.class);
                    intent.putExtra("plane_name",noteslist.get(position).getPlane_name());
                    intent.putExtra("donation_amount",noteslist.get(position).getDonation_amount());
                    intent.putExtra("plane_image",noteslist.get(position).getPlane_image());
                    intent.putExtra("number_pepople_donate",noteslist.get(position).getNumber_pepople_donate());
                    intent.putExtra("item_id",noteslist.get(position).getItemId());
                    mCtx.startActivity(intent);

                }
            });

            holder.edit_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent=new Intent(mCtx, Edit_Plane_act.class);
                    intent.putExtra("plane_image",noteslist.get(position).getPlane_image());
                    intent.putExtra("donation_amt",noteslist.get(position).getDonation_amount());
                    intent.putExtra("number_of_people_donate",noteslist.get(position).getNumber_pepople_donate());
                    intent.putExtra("item_id",noteslist.get(position).getItemId());
                    intent.putExtra("plan_name",noteslist.get(position).getPlane_name());
                    mCtx.startActivity(intent);

                }
            });




            holder.deleat_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new AlertDialog.Builder(mCtx)
                            .setTitle("Delete entry")
                            .setMessage("Are you sure you want to delete this Plane?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation

                                    progressDialog.setMessage("Please Wait....");
                                    progressDialog.setCancelable(false);
                                    progressDialog.setCanceledOnTouchOutside(false);
                                    progressDialog.show();
                                    firebaseFirestore.collection("plane_list")
                                            .document(noteslist.get(position).getItemId())
                                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                            progressDialog.dismiss();
                                            noteslist.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, noteslist.size());



                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            progressDialog.dismiss();
                                            Toast.makeText(mCtx, "try aging not deleted", Toast.LENGTH_SHORT).show();


                                        }
                                    });

                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation

                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();



                }
            });




        }else if(key.contentEquals("admin_plane_wise_colection")){




            holder.glide.with(mCtx).load(noteslist.get(position).getPlane_image()).into(holder.plane_image);

            holder.plane_Name.setVisibility(View.VISIBLE);
            holder.number_of_people_donate.setVisibility(View.GONE);
            holder.no_people_donate_layout.setVisibility(View.GONE);
            holder.donate_bt.setVisibility(View.GONE);


            holder.date_layout_my_donation.setVisibility(View.GONE);
            holder.name_layout_my_donation.setVisibility(View.GONE);
            holder.view.setVisibility(View.GONE);

            holder.edit_bt.setVisibility(View.GONE);
            holder.deleat_bt.setVisibility(View.GONE);

            holder.plane_Name.setText(noteslist.get(position).getPlane_name());
            holder.donation_amt_txt.setText("Total Collection");



            firebaseFirestore.collection("donation_list").whereEqualTo("plane_name",noteslist.get(position).getPlane_name())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {


                            QuerySnapshot documentSnapshot=task.getResult();

                            int total_donation_amt=0;

                            for(int i = 0; i < documentSnapshot.getDocuments().size(); i++){


                                total_donation_amt +=  Integer.parseInt((String) documentSnapshot.getDocuments().get(i).get("donation_amount"));

                            }

                            holder.donate_amt.setText("₹"+String.valueOf(total_donation_amt));






                        }
                    });













        }




        else if(key.contentEquals("memberlist")){



            holder.name_txt.setText(noteslist.get(position).getName()+" Ji");
            holder.name_txt.setAllCaps(true);
            holder.phone_txt.setText(noteslist.get(position).getPhone());
            holder.state_txt.setText(noteslist.get(position).getState());
            holder.code_txt.setVisibility(View.GONE);


            holder.delet_icon_meberlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new AlertDialog.Builder(mCtx)
                            .setTitle("Delete Member")
                            .setMessage("Are you sure you want to delete this Member?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation

                                    progressDialog.setMessage("Please Wait....");
                                    progressDialog.setCancelable(false);
                                    progressDialog.setCanceledOnTouchOutside(false);
                                    progressDialog.show();
                                    firebaseFirestore.collection("user_list")
                                            .document(noteslist.get(position).getItemId())
                                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                            progressDialog.dismiss();
                                            noteslist.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, noteslist.size());



                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            progressDialog.dismiss();
                                            Toast.makeText(mCtx, "try aging not deleted", Toast.LENGTH_SHORT).show();


                                        }
                                    });

                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation

                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();




                }
            });

            holder.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+noteslist.get(position).getPhone().toString()));
                    mCtx.startActivity(intent);

                }
            });


            holder.whats_app.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PackageManager packageManager = mCtx.getPackageManager();
                    Intent i = new Intent(Intent.ACTION_VIEW);

                    try {
                        String url = "https://api.whatsapp.com/send?phone="+ noteslist.get(position).getPhone() +"&text=" + URLEncoder.encode("hi", "UTF-8");
                        i.setPackage("com.whatsapp");
                        i.setData(Uri.parse(url));
                        if (i.resolveActivity(packageManager) != null) {
                            mCtx.startActivity(i);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
///////////

        }







    }

    @Override
    public int getItemCount() {
        return noteslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView plane_Name,number_of_people_donate,donate_amt,name_my_donation,date_my_donation;
        View mview;
        ImageView plane_image,delet_icon_meberlist;
        Glide glide;
        CardView deleat_bt,edit_bt,donate_bt,whats_app,call;
        View view;
        TextView number_pepople_donate_txt,name_txt,phone_txt,state_txt,code_txt,edit_bt_txt,delet_bt_txt,donation_amt_txt;

        //////////////////
        TextView s_no,plane_name_pm,donation_amt_pm,state_name;

        ////////////////proof

        TextView plane_name_proof,date_proof,spending_proof ,proof_heading,proof_dicreption;

        ImageView proof_image;

        CircleImageView proof_plane_image;

        CardView proof_delete_bt,proof_edit_bt,proof_read_more_bt;

////////////////////////
        LinearLayout name_layout_my_donation,date_layout_my_donation,state_layout,no_people_donate_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mview=itemView;


            if(key.contentEquals("parmament")){

                s_no=mview.findViewById(R.id.s_no);
                plane_name_pm=mview.findViewById(R.id.plane_name_pm);
                donation_amt_pm=mview.findViewById(R.id.donation_amt_pm);



            }else if(key.contentEquals("admin_proof_list")){

                proof_plane_image=mview.findViewById(R.id.plane_image_pr);

                proof_image=mview.findViewById(R.id.proof_image);
                plane_name_proof=mview.findViewById(R.id.plane_name_proof);
                date_proof=mview.findViewById(R.id.date_proof);
                spending_proof=mview.findViewById(R.id.spending_proof);
                proof_heading=mview.findViewById(R.id.proof_heading);
                proof_dicreption=mview.findViewById(R.id.proof_dicreption);

                proof_delete_bt=mview.findViewById(R.id.proof_delete_bt);
                proof_edit_bt=mview.findViewById(R.id.proof_edit_bt);
                proof_read_more_bt=mview.findViewById(R.id.proof_read_more_bt);


            }else if(key.contentEquals("user_proof_list")){

                proof_plane_image=mview.findViewById(R.id.plane_image_pr);

                proof_image=mview.findViewById(R.id.proof_image);
                plane_name_proof=mview.findViewById(R.id.plane_name_proof);
                date_proof=mview.findViewById(R.id.date_proof);
                spending_proof=mview.findViewById(R.id.spending_proof);
                proof_heading=mview.findViewById(R.id.proof_heading);
                proof_dicreption=mview.findViewById(R.id.proof_dicreption);

                proof_delete_bt=mview.findViewById(R.id.proof_delete_bt);
                proof_edit_bt=mview.findViewById(R.id.proof_edit_bt);
                proof_read_more_bt=mview.findViewById(R.id.proof_read_more_bt);



            }

            else if(key.contentEquals("memberlist")){

                name_txt=mview.findViewById(R.id.name);
                phone_txt=mview.findViewById(R.id.phone_number);
                state_txt=mview.findViewById(R.id.state);
                code_txt=mview.findViewById(R.id.pin);
                whats_app=mview.findViewById(R.id.whats_app);
                call=mview.findViewById(R.id.call);
                delet_icon_meberlist=mview.findViewById(R.id.delet_icon_meberlist);


            } else {


                plane_Name=mview.findViewById(R.id.plane_name);
                number_of_people_donate=mview.findViewById(R.id.number_pepople_donate);
                donate_amt=mview.findViewById(R.id.donation_amt);
                plane_image=mview.findViewById(R.id.plan_image);

                state_layout=mview.findViewById(R.id.state_layout);
                state_name=mview.findViewById(R.id.state_name);
                donation_amt_txt=mview.findViewById(R.id.donation_amt_txt);

                no_people_donate_layout=mview.findViewById(R.id.no_people_donate_layout);
                deleat_bt=mview.findViewById(R.id.deleat_bt);
                edit_bt=mview.findViewById(R.id.edit_bt);
                donate_bt=mview.findViewById(R.id.donate_bt);
                name_layout_my_donation=mview.findViewById(R.id.name_layout);
                date_layout_my_donation=mview.findViewById(R.id.date_layout);
                name_my_donation=mview.findViewById(R.id.name);
                date_my_donation=mview.findViewById(R.id.date);
                view=mview.findViewById(R.id.view);
                edit_bt_txt=mview.findViewById(R.id.edit_bt_txt);
                delet_bt_txt=mview.findViewById(R.id.delet_bt_txt);
                number_pepople_donate_txt=mview.findViewById(R.id.number_pepople_donate_txt);

            }






        }
    }
}
