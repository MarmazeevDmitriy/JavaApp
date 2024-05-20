package com.example.projectforjava.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.projectforjava.customElements.CustomViewPager;
import com.example.projectforjava.alerts.alertDialogs.ExitorAlert;
import com.example.projectforjava.fragments.main.FriendsFragment;
import com.example.projectforjava.fragments.main.GlobalFragment;
import com.example.projectforjava.fragments.main.HomeFragment;
import com.example.projectforjava.fragments.main.LeaderBoardFragment;
import com.example.projectforjava.R;
import com.example.projectforjava.customElements.CustomStatusBar;
import com.example.projectforjava.services.Notificator;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private boolean isLogined = true;
    private String username = "user";
    private String password = "password";

    public static final String CHANNEL_ID = "";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomStatusBar.changeStatusBar(this);
        setContentView(R.layout.activity_main);

        ActivityResultLauncher<String[]> multiPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                        callback -> {
                        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            multiPermissionLauncher.launch(new String[]{Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.SCHEDULE_EXACT_ALARM, Manifest.permission.RECEIVE_BOOT_COMPLETED,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS});
        }

        Notificator.scheduleNotification(this, Calendar.getInstance());

        if (!isLogined) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivityForResult(loginIntent, 1);
        }

        CustomViewPager viewPager = findViewById(R.id.viewPagerMain);
        TabLayout tabLayout = findViewById(R.id.tabLayoutMain);

        viewPager.setSwipeEnabled(false);

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new LeaderBoardFragment();
                    case 1:
                        return new GlobalFragment();
                    case 2:
                        return new FriendsFragment();
                    case 3:
                        return new HomeFragment();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 4; // Количество ваших фрагментов
            }
        });

        viewPager.setCurrentItem(3);
        tabLayout.setupWithViewPager(viewPager);

        // Установите векторные изображения на вкладках
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.leaderboard_fragment);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.global_fragment);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.friends_fragment);
        Objects.requireNonNull(tabLayout.getTabAt(3)).setIcon(R.drawable.home_fragment);

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            isLogined = true;
        }
    }

    public boolean checkCredentials(String enteredUsername, String enteredPassword) {
        return enteredUsername.equals(username) && enteredPassword.equals(password);
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setIsLogined(boolean newStatus) {
        this.isLogined = newStatus;
    }

    private void removeNestedFragmentIfPresent() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("FRIEND_REQUESTS");
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
            fragmentManager.popBackStack("FRIEND_REQUESTS", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("FRIEND_REQUESTS");
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
            fragmentManager.popBackStack("FRIEND_REQUESTS", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        else{
            ExitorAlert.showExitDialog(this);
        }
    }
}