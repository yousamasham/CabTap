package com.example.cabtap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.FirebaseApp;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private ViewPager2 viewPager;
    AuthenticationPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.pager);

        pagerAdapter = new AuthenticationPagerAdapter(this);
        // add Fragments in your ViewPagerFragmentAdapter class         myAdapter.addFragment(new first_fragment());
        pagerAdapter.addFragment(new LoginPage());
        pagerAdapter.addFragment(new RegistrationPage());

        viewPager.setAdapter(pagerAdapter);
    }

    private class AuthenticationPagerAdapter extends FragmentStateAdapter {

        private ArrayList<Fragment> fragmentList = new ArrayList<>();

        public AuthenticationPagerAdapter( FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }
        public void addFragment(Fragment fragment) {
            fragmentList.add(fragment);
        }
        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }
}