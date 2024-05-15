package com.example.projectforjava.fragments.friends;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projectforjava.R;
import com.example.projectforjava.activities.MainActivity;

import java.util.Objects;

public class IncomingFriendsRequestsFragment extends Fragment {
    public IncomingFriendsRequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incoming_friends_requests, container, false);
        return view;
    }
}