package com.example.projectforjava.fragments.leaderboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectforjava.R;
import com.example.projectforjava.adapters.leaderboard.ChallengesOnLeaderboardAdapter;
import com.example.projectforjava.templates.leaderboard.ChallengeOnLeaderboard;

import java.util.List;

public class TopChallengesOfAllTimeFragment extends Fragment {

    public TopChallengesOfAllTimeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_challenges_of_all_time, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.topChallengesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Генерация случайных данных
        List<ChallengeOnLeaderboard> challengeList = DataGenerator.generateChallenges(getContext(), 10); // генерируем 10 случайных челленджей

        // Инициализация адаптера
        ChallengesOnLeaderboardAdapter adapter = new ChallengesOnLeaderboardAdapter(challengeList);
        recyclerView.setAdapter(adapter);
        return view;
    }
}