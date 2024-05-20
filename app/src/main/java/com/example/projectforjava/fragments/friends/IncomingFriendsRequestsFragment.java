package com.example.projectforjava.fragments.friends;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projectforjava.R;
import com.example.projectforjava.activities.MainActivity;
import com.example.projectforjava.adapters.friends.FriendRequestsAdapter;
import com.example.projectforjava.adapters.friends.FriendsAdapter;
import com.example.projectforjava.templates.friends.Friend;
import com.example.projectforjava.templates.friends.FriendRequest;
import com.example.projectforjava.utils.ImgUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public class IncomingFriendsRequestsFragment extends Fragment {
    IncomingFriendsRequestsFragment incomingFriendsRequestsFragment;
    public IncomingFriendsRequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incoming_friends_requests, container, false);
        incomingFriendsRequestsFragment = this;

        RecyclerView recyclerView = view.findViewById(R.id.incomingFriendsRequestsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<FriendRequest> friendRequestsList = new ArrayList<>();
        // Заполните friendList вашими данными
        friendRequestsList.add(new FriendRequest(ImgUtils.BitmapToDrawable(ImgUtils.getRoundedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wants_to_be_friend)), requireContext()),
                "John Davis", 10, 6));
        friendRequestsList.add(new FriendRequest(ImgUtils.BitmapToDrawable(ImgUtils.getRoundedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wants_to_be_friend)), requireContext()),
                "Dave Rapiros", 70, 1));
        friendRequestsList.add(new FriendRequest(ImgUtils.BitmapToDrawable(ImgUtils.getRoundedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.wants_to_be_friend)), requireContext()),
                "Sarah Halley", 15, 125));
        // Добавьте другие объекты Friend в friendList

        FriendRequestsAdapter adapter = new FriendRequestsAdapter(friendRequestsList);
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.friendRequestsBackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(incomingFriendsRequestsFragment).commit();
                fragmentManager.popBackStack("FRIEND_REQUESTS", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        return view;
    }
}