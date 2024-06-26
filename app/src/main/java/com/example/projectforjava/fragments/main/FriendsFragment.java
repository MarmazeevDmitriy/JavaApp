package com.example.projectforjava.fragments.main;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectforjava.fragments.friends.AddFriendsFragment;
import com.example.projectforjava.fragments.friends.ChallengesFromFriendsFragment;
import com.example.projectforjava.fragments.friends.ChallengesToFriendsFragment;
import com.example.projectforjava.fragments.friends.FriendsContainerFragment;
import com.example.projectforjava.fragments.friends.FriendsListFragment;
import com.example.projectforjava.R;
import com.example.projectforjava.customElements.CustomViewPager;
import com.example.projectforjava.preferences.PreferencesManager;
import com.example.projectforjava.utils.ImgUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

@SuppressLint("ClickableViewAccessibility")
public class FriendsFragment extends Fragment {

    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends_fragment, container, false);

        CustomViewPager viewPager = view.findViewById(R.id.viewPagerFriends);
        TabLayout tabLayout = view.findViewById(R.id.tabLayoutFriends);

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
                        return new FriendsContainerFragment();
                    case 1:
                        return new ChallengesFromFriendsFragment();
                    case 2:
                        return new ChallengesToFriendsFragment();
                    case 3:
                        return new AddFriendsFragment();
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
        Objects.requireNonNull(tabLayout.getTabAt(0)).setText("Friends");
        Objects.requireNonNull(tabLayout.getTabAt(1)).setText("From friends");
        Objects.requireNonNull(tabLayout.getTabAt(2)).setText("To friends");
        Objects.requireNonNull(tabLayout.getTabAt(3)).setText("Add friends");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                removeNestedFragmentIfPresent();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                removeNestedFragmentIfPresent();
            }
        });

        return view;
    }

    private void removeNestedFragmentIfPresent() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("FRIEND_REQUESTS");
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
            fragmentManager.popBackStack("FRIEND_REQUESTS", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}