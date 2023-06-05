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
import com.mradkingshop.vsssn.admin.adapter.PlaneListAdapter;
import com.mradkingshop.vsssn.admin.modal.PlanModal;
import com.mradkingshop.vsssn.admin.modal.Recently_donation;

import java.util.List;
import java.util.Random;

public class RecentDonation_adapter extends RecyclerView.Adapter<RecentDonation_adapter.ViewHolder> {

    private Context mCtx;

    List<Recently_donation> noteslist;
    ProgressDialog progressDialog;
    private FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String key;


    public RecentDonation_adapter(Context mCtx, List<Recently_donation> noteslist, String key) {
        this.mCtx = mCtx;
        this.noteslist = noteslist;
        this.key = key;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_donation_recived_row, parent, false);
        mCtx = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth= FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(mCtx);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String []radom_name={"Aadi","Aarav","Aarnav","Aarush","Aayush","Abdul",
                "Abeer","Abhimanyu","Abhiramnew","Aditya","Advaith","Advay","Advik","Agastya",
                "Akshay","Amol","Anay","Anirudh","Anmol","Ansh","Arin","Arjun","Arnav","Aryan","Atharv",
                "Avi","Ayaan","Ayush","Ayushman","Azad",
                "Brijesh","Bachittar","Bahadurjit","Bakhshi","Balendra","Baljiwan","Balvan","Balveer","Banjeet","Chaitanya","Chakradev","Chakradhar","Chandresh","Daksh",
                "Darsh","Dev","Devansh","Dhruv","Dakshesh","Dalbir","Ekbal","Farhan","Faqid","Fariq","Fiyaz","Gautam",
                "Gagan","Gaurang","Gopal","Gaurav","Harsh","Hardik","Harish","Hritik","Hitesh","Ishaan","Imaran","Indrajit","Jagdish","Jatin","Jeet","Kabir","Karan","Kiaan","Krish","Krishna","Laksh","Lakshay","Lucky","Lakshit","Mohammed","Maanav","Nachiket",
                "Naksh","Nakul","Naveen","Nihal","Nitesh","Ojas"," Onveer","Parth","Pranav","Pranit","Pratyush","Qasim","Rachit","Raghav","Ranbir","Ranveer","Rishi","Rohan","Ryan","Saksham","Samar","Sarthak","Shaurya","Siddharth","Tejas","Tanay","Tanish","Tanveer","Utkarsh","Umang","Viraj","Wazir","Wridesh","Yash","Yug","Yuvraj","Zayan"
                ,"kajal"};

        String []plane_name={"पर्यावरण तथा वृक्षारोपण के लिए "
                ,"गरीबों की मदद के लिए "
                ,"सनातन धर्म प्रचार "
                ,"वृद्ध सहायक सेवा के लिए"
                ,"जरूरतमंदों की स्वास्थ्य सेवाओं के लिए"
                ,"पशु सेवा तथा पालन के लिए "
                ,"गरीब बच्चों की शिक्षा के लिए "
        };
        String []sates_name= { "Andhra Pradesh",
                "Assam",
                "Bihar",
                "Chhattisgarh",
                "Goa",
                "Gujarat",
                "Haryana",
                "Jharkhand",
                "Karnataka",
                "Kerala",
                "Madhya Pradesh",
                "Maharashtra",
                "Manipur",
                "Meghalaya",
                "Mizoram",
                "Nagaland",
                "Odisha",
                "Punjab",
                "Rajasthan",
                "Sikkim",
                "Tamil Nadu",
                "Telangana",
                "Tripura",
                "Uttarakhand",
                "Uttar Pradesh",
                "West Bengal",
                "Chandigarh",
                "Delhi",
        };

        final int min3 = 0;
        final int max3 = 27;
        final int random3 = new Random().nextInt((max3 - min3) + 1) + min3;



        String []amount={"25","40","15","50","10","20","25"};

        final int min5 = 0;
        final int max5 = 6;
        final int random5 = new Random().nextInt((max5 - min5) + 1) + min5;



        final int min4 = 0;
        final int max4 = 6;
        final int random4 = new Random().nextInt((max4 - min4) + 1) + min4;


        final int min = 1;
        final int max = 98;
        final int random = new Random().nextInt((max - min) + 1) + min;

        final int min6 = 80;
        final int max6 = 98;
        final int random6 = new Random().nextInt((max6 - min6) + 1) + min6;

        final int min7 = 10;
        final int max7 = 98;
        final int random7 = new Random().nextInt((max7 - min7) + 1) + min7;


        holder.name.setText("THANKS " +String.valueOf(radom_name[random])+ " JI");
        holder.name.setAllCaps(true);

        holder.plane_name.setText(String.valueOf(plane_name[random4]));
        holder.donation_amt.setText(String.valueOf("₹"+amount[random5]));
        holder.state_name.setText(String.valueOf(sates_name[random3]));
        holder.phone_number.setText(String.valueOf(random6)+"XXXXXXX"+String.valueOf(random7));



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
