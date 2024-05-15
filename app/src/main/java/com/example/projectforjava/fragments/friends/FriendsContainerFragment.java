package com.example.projectforjava.fragments.friends;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectforjava.R;

public class FriendsContainerFragment extends Fragment {
    public FriendsContainerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends_container, container, false);
        inflateFriendsListFragment();
        return view;
    }

    private void inflateFriendsListFragment() {
        FriendsListFragment friendsListFragment = new FriendsListFragment();

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.friendsFragmentContainer, friendsListFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}