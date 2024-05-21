package com.example.projectforjava.fragments.friends;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.projectforjava.R;
import com.example.projectforjava.activities.MainActivity;
import com.example.projectforjava.adapters.friends.FriendsAdapter;
import com.example.projectforjava.adapters.friends.FriendsChallengesAdapter;
import com.example.projectforjava.templates.friends.Friend;
import com.example.projectforjava.utils.ImgUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
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

        RecyclerView recyclerView = view.findViewById(R.id.friendsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Friend> friendList = new ArrayList<>();
        // Заполните friendList вашими данными
        friendList.add(new Friend(ImgUtils.BitmapToDrawable(ImgUtils.getRoundedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.my_friend)), requireContext()),
                "John Doe", 5, 3));
        friendList.add(new Friend(ImgUtils.BitmapToDrawable(ImgUtils.getRoundedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.my_friend)), requireContext()),
                "Steel wolf", 107, 10));
        friendList.add(new Friend(ImgUtils.BitmapToDrawable(ImgUtils.getRoundedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.my_friend)), requireContext()),
                "Soup lover", 20, 40));
        // Добавьте другие объекты Friend в friendList

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FriendsAdapter adapter = new FriendsAdapter(friendList, false);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new FriendsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });

        incomingFriendsRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflateIncomingFriendsRequestsFragment();
            }
        });

        EditText editTextSearch = view.findViewById(R.id.editTextSearchFriends);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
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