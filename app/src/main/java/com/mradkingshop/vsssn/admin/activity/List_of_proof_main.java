package com.mradkingshop.vsssn.admin.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mradkingshop.vsssn.R;
import com.mradkingshop.vsssn.admin.fragment.proof_add_plan_list;
import com.mradkingshop.vsssn.admin.fragment.proof_list;

import java.util.ArrayList;
import java.util.List;

public class List_of_proof_main extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TabLayout tabLayout;


    private ViewPager2 viewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_main);


        viewPager = (ViewPager2) findViewById(R.id.viewpager);

        tabLayout = (TabLayout)findViewById(R.id.tabs);

        setupViewPager(viewPager);


    }


    private void setupViewPager(ViewPager2 viewPager) {




        ViewPagerAdapter1 adapter = new ViewPagerAdapter1(this);

        adapter.addFragment(new proof_list(), "Proof List");
        adapter.addFragment(new proof_add_plan_list(),"Add Proof");


        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) ->
                tab.setText(adapter.mFragmentTitleList.get(position)))).attach();



    }


    public class ViewPagerAdapter1 extends FragmentStateAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<Fragment>();
        private final List<String> mFragmentTitleList = new ArrayList<String>();

        public ViewPagerAdapter1(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }


        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return mFragmentTitleList.size();
        }
    }

}
