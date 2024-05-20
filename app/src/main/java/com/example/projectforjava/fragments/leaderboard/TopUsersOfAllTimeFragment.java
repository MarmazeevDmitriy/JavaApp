package com.example.projectforjava.fragments.leaderboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectforjava.R;
import com.example.projectforjava.adapters.leaderboard.UserOnLeaderboardAdapter;
import com.example.projectforjava.templates.leaderboard.UserOnLeaderboard;

import java.util.List;

public class TopUsersOfAllTimeFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserOnLeaderboardAdapter adapter;
    private List<UserOnLeaderboard> users;

    public TopUsersOfAllTimeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_users_of_all_time, container, false);
        recyclerView = view.findViewById(R.id.topUsersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        users = DataGenerator.generateUsers(getContext(), 10);
        adapter = new UserOnLeaderboardAdapter(users);
        recyclerView.setAdapter(adapter);

        return view;
    }
}