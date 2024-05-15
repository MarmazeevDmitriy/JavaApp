package com.example.projectforjava.fragments.friends;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.projectforjava.R;
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
                inflateIncomingFriendsRequestsFragment();
            }
        });

        return view;
    }

    private void inflateIncomingFriendsRequestsFragment() {
        IncomingFriendsRequestsFragment incomingFriendsRequestsFragment = new IncomingFriendsRequestsFragment();

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.friendsFragmentContainer, incomingFriendsRequestsFragment, "FRIEND_REQUESTS");
        transaction.addToBackStack("FRIEND_REQUESTS");
        transaction.commit();
    }
}