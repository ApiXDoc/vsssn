package com.mradkingshop.vsssn.user.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mradkingshop.vsssn.R;
import com.mradkingshop.vsssn.admin.modal.Real_donation_Modal;
import com.mradkingshop.vsssn.admin.modal.Recently_donation;

import java.util.List;
import java.util.Random;

public class Real_RecentDonation_adapter extends RecyclerView.Adapter<Real_RecentDonation_adapter.ViewHolder>{

    private Context mCtx;

    List<Real_donation_Modal> noteslist;
    ProgressDialog progressDialog;
    private FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String key;

    public Real_RecentDonation_adapter(Context mCtx, List<Real_donation_Modal> noteslist, String key) {
        this.mCtx = mCtx;
        this.noteslist = noteslist;
        this.key = key;
    }

    @NonNull
    @Override
    public Real_RecentDonation_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_donation_recived_row, parent, false);
        mCtx = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth= FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(mCtx);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Real_RecentDonation_adapter.ViewHolder holder, int position) {

        holder.name.setText("THANKS " +noteslist.get(position).getName()+ " JI");

        final int min6 = 80;
        final int max6 = 98;
        final int random6 = new Random().nextInt((max6 - min6) + 1) + min6;

        final int min7 = 10;
        final int max7 = 98;
        final int random7 = new Random().nextInt((max7 - min7) + 1) + min7;

        holder.plane_name.setText(noteslist.get(position).getPlane_name());
        holder.donation_amt.setText("₹"+noteslist.get(position).getDonation_amount());
        holder.state_name.setText(noteslist.get(position).getState());
        String phone_number=noteslist.get(position).getPhone();
        holder.phone_number.setText(phone_number.substring(0,4)+"XXXXXX");


    }

    @Override
    public int getItemCount() {
        return noteslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mview;
        TextView name,plane_name,donation_amt,state_name,phone_number;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mview=itemView;

            name=mview.findViewById(R.id.plane_name);
            plane_name=mview.findViewById(R.id.pl_name);
            donation_amt=mview.findViewById(R.id.donation_amt);
            state_name=mview.findViewById(R.id.state_name);

            phone_number=mview.findViewById(R.id.phone_number);

        }




    }
}
