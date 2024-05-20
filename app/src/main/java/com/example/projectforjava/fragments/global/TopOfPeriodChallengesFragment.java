package com.example.projectforjava.fragments.global;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectforjava.R;

public class TopOfPeriodChallengesFragment extends Fragment {
    public TopOfPeriodChallengesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_of_period_challenges, container, false);
        view.findViewById(R.id.topOfDay).findViewById(R.id.topOfPeriodGlobalChallengeImage).setBackgroundResource(R.drawable.its_me);
        view.findViewById(R.id.topOfWeek).findViewById(R.id.topOfPeriodGlobalChallengeImage).setBackgroundResource(R.drawable.random);
        view.findViewById(R.id.topOfMonth).findViewById(R.id.topOfPeriodGlobalChallengeImage).setBackgroundResource(R.drawable.my_friend);
        return view;
    }
}