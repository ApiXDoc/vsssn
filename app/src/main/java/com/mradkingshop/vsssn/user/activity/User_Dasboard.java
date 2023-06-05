package com.mradkingshop.vsssn.user.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.mradkingshop.vsssn.R;
import com.mradkingshop.vsssn.admin.fragment.add_main;
import com.mradkingshop.vsssn.admin.fragment.donation_list_main;
import com.mradkingshop.vsssn.admin.fragment.home_page;
import com.mradkingshop.vsssn.admin.fragment.member_list_main;
import com.mradkingshop.vsssn.admin.fragment.other;
import com.mradkingshop.vsssn.user.fragment.user_account_page;
import com.mradkingshop.vsssn.user.fragment.user_home_page;
import com.mradkingshop.vsssn.user.fragment.user_my_donation;
import com.mradkingshop.vsssn.user.fragment.user_other_act;
import com.mradkingshop.vsssn.user.fragment.user_proofs;

public class User_Dasboard extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;


    user_home_page homePage=new user_home_page();
    user_my_donation add_main=new user_my_donation();
    user_proofs member_list_main=new user_proofs();
    user_account_page donation_list_main=new user_account_page();
    user_other_act user_other_act=new user_other_act();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_dasboard);

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        if(getIntent().getExtras()!=null){

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, member_list_main)
                    .commit();


        }else {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homePage)
                    .commit();

        }



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){


                    case R.id.navigation_1:

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homePage)
                                .commit();
                        return true;

                    case R.id.navigation_2:

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,add_main)
                                .commit();
                        return true;

                    case R.id.navigation_3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,member_list_main).commit();
                        return true;


                    case R.id.navigation_4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,user_other_act).commit();
                        return true;


                }





                return true;
            }
        });



    }
}
