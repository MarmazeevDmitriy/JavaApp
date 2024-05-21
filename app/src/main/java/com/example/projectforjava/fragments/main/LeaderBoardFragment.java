package com.example.projectforjava.fragments.main;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectforjava.R;
import com.example.projectforjava.customElements.CustomViewPager;
import com.example.projectforjava.fragments.leaderboard.TopChallengesOfAllTimeFragment;
import com.example.projectforjava.fragments.leaderboard.TopUsersOfAllTimeFragment;
import com.example.projectforjava.preferences.PreferencesManager;
import com.example.projectforjava.utils.ImgUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class LeaderBoardFragment extends Fragment {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leaderboard_fragment, container, false);

        CustomViewPager viewPager = view.findViewById(R.id.viewPagerLeaders);
        TabLayout tabLayout = view.findViewById(R.id.tabLayoutLeaders);

        TextView textView = view.findViewById(R.id.headerUsersName);
        ImageView profileImageView = view.findViewById(R.id.circleProfileImage);
        PreferencesManager preferencesManager = new PreferencesManager(requireContext());
        if(preferencesManager.getProfileName() != null)
            textView.setText(preferencesManager.getProfileName());
        if(preferencesManager.getProfileIcon() != null)
            profileImageView.setImageBitmap(ImgUtils.getRoundedBitmap(preferencesManager.getProfileIcon()));

        viewPager.setSwipeEnabled(false);

        viewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new TopChallengesOfAllTimeFragment();
                    case 1:
                        return new TopUsersOfAllTimeFragment();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 2; // Количество ваших фрагментов
            }
        });

        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);

        // Установите векторные изображения на вкладках
        Objects.requireNonNull(tabLayout.getTabAt(0)).setText("Top challenges");
        Objects.requireNonNull(tabLayout.getTabAt(1)).setText("Top people");

        return view;
    }
}