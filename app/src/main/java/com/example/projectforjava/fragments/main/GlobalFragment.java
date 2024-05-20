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

import com.example.projectforjava.fragments.global.AcceptedChallengesFragment;
import com.example.projectforjava.fragments.global.CreateGlobalChallengeFragment;
import com.example.projectforjava.fragments.global.GlobalChallengesFragment;
import com.example.projectforjava.R;
import com.example.projectforjava.customElements.CustomViewPager;
import com.example.projectforjava.fragments.global.TopOfPeriodChallengesFragment;
import com.example.projectforjava.utils.ImgUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class GlobalFragment extends Fragment {
    public GlobalFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.global_fragment, container, false);

        CustomViewPager viewPager = view.findViewById(R.id.viewPagerGlobal);
        TabLayout tabLayout = view.findViewById(R.id.tabLayoutGlobal);

        TextView textView = view.findViewById(R.id.headerUsersName);
        textView.setText("Димастер");
        ImageView profileImageView = view.findViewById(R.id.circleProfileImage);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.its_me);
        bitmap = ImgUtils.scaleSquareBitmap(bitmap, 128);
        bitmap = ImgUtils.getRoundedBitmap(bitmap);
        profileImageView.setImageBitmap(bitmap);

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
                        return new GlobalChallengesFragment();
                    case 1:
                        return new TopOfPeriodChallengesFragment();
                    case 2:
                        return new AcceptedChallengesFragment();
                    case 3:
                        return new CreateGlobalChallengeFragment();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 4; // Количество ваших фрагментов
            }
        });

        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);

        // Установите векторные изображения на вкладках
        Objects.requireNonNull(tabLayout.getTabAt(0)).setText("Challenges");
        Objects.requireNonNull(tabLayout.getTabAt(1)).setText("Top challenge");
        Objects.requireNonNull(tabLayout.getTabAt(2)).setText("Accepted challenges");
        Objects.requireNonNull(tabLayout.getTabAt(3)).setText("Your challenges");

        return view;
    }
}