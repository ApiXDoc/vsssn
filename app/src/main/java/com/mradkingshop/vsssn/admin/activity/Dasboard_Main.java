package com.mradkingshop.vsssn.admin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

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


public class Dasboard_Main extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    home_page homePage=new home_page();
    add_main add_main=new add_main();
    member_list_main member_list_main=new member_list_main();
    donation_list_main donation_list_main=new donation_list_main();
    other other=new other();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dasboard_main);

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homePage)
                .commit();


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
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,donation_list_main).commit();
                        return true;

                    case R.id.navigation_5:

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,other).commit();
                        return true;


                }





                return true;
            }
        });



    }
}
