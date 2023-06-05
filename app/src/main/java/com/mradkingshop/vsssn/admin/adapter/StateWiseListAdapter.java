package com.mradkingshop.vsssn.admin.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mradkingshop.vsssn.R;
import com.mradkingshop.vsssn.admin.activity.donation_list_acording_state;
import com.mradkingshop.vsssn.admin.activity.member_list_acording_state;
import com.mradkingshop.vsssn.admin.modal.StateWiseModal;

import java.util.List;

public class StateWiseListAdapter extends RecyclerView.Adapter<StateWiseListAdapter.ViewHolder> {

    private Context mCtx;

    List<StateWiseModal> noteslist;
    ProgressDialog progressDialog;
    private FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    String key;

    public StateWiseListAdapter(Context mCtx, List<StateWiseModal> noteslist) {
        this.mCtx = mCtx;
        this.noteslist = noteslist;
    }

    @NonNull
    @Override
    public StateWiseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_state_wise_row, parent, false);
        mCtx = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth= FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(mCtx);
        return new ViewHolder(view);



    }

    @Override
    public void onBindViewHolder(@NonNull StateWiseListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {





            if(noteslist.get(position).getTotal_collection().contentEquals("no")){
                holder.state_name.setText(noteslist.get(position).getState_name());
                holder.number_donator.setText(noteslist.get(position).getNumber_donator());

                holder.mem_txt.setText("Total Members:");
                holder.total_collection.setVisibility(View.GONE);
                holder.total_collection_ly.setVisibility(View.GONE);

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent =new Intent(mCtx, member_list_acording_state.class);
                        intent.putExtra("key",noteslist.get(position).getState_name());
                        mCtx.startActivity(intent);


                    }
                });
            }else {
                holder.state_name.setText(noteslist.get(position).getState_name());
                holder.number_donator.setText(noteslist.get(position).getNumber_donator());
                holder.total_collection.setText(noteslist.get(position).getTotal_collection());


                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent =new Intent(mCtx, donation_list_acording_state.class);
                        intent.putExtra("key",noteslist.get(position).getState_name());
                        mCtx.startActivity(intent);


                    }
                });
            }











    }

    @Override
    public int getItemCount() {
        return noteslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView number_donator,total_collection,state_name;
        CardView donation_state_wise_ly;
        TextView total_collection_ly;
        TextView mem_txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            view=itemView;
            number_donator=view.findViewById(R.id.number_donator);
            total_collection=view.findViewById(R.id.total_collection);
            state_name=view.findViewById(R.id.state_name);
            donation_state_wise_ly=view.findViewById(R.id.donation_state_wise_ly);
            total_collection_ly=view.findViewById(R.id.total_collection_ly);
            mem_txt=view.findViewById(R.id.mem_txt);
        }
    }
}
