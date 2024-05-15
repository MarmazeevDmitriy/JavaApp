package com.example.projectforjava.fragments.friends;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.projectforjava.R;
import com.example.projectforjava.activities.EditChallengeActivity;
import com.example.projectforjava.activities.IncomingFriendsRequestsActivity;
import com.example.projectforjava.activities.MainActivity;

import java.util.Objects;

public class FriendsListFragment extends Fragment {

    public FriendsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends_list, container, false);

        LinearLayout incomingFriendsRequests = view.findViewById(R.id.incomingFriendsRequests);

        incomingFriendsRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IncomingFriendsRequestsActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        return view;
    }
}