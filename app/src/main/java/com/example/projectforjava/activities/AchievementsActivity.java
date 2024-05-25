package com.example.projectforjava.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.projectforjava.R;
import com.example.projectforjava.customElements.CustomStatusBar;

public class AchievementsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomStatusBar.changeStatusBar(this);
        setContentView(R.layout.activity_achievements);
    }
}