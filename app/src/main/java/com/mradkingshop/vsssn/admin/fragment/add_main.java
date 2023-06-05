package com.mradkingshop.vsssn.admin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mradkingshop.vsssn.R;

import java.util.ArrayList;
import java.util.List;

public class add_main extends Fragment {


    BottomNavigationView bottomNavigationView;
    TabLayout tabLayout;


    private ViewPager2 viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.add_main, container, false);


        viewPager = (ViewPager2) root.findViewById(R.id.viewpager);

        tabLayout = (TabLayout)root.findViewById(R.id.tabs);

        setupViewPager(viewPager);

        return root;
    }



    private void setupViewPager(ViewPager2 viewPager) {




        ViewPagerAdapter1 adapter = new ViewPagerAdapter1(getActivity());

        adapter.addFragment(new Add_sub_Plane_list(), "Planes");
        adapter.addFragment(new Add_sub_Add_plane(), "Add Plane");


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

