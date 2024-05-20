package com.example.projectforjava.fragments.friends;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.projectforjava.R;
import com.example.projectforjava.adapters.friends.AddFriendsAdapter;
import com.example.projectforjava.adapters.friends.FriendRequestsAdapter;
import com.example.projectforjava.adapters.friends.FriendsAdapter;
import com.example.projectforjava.templates.friends.AddFriend;
import com.example.projectforjava.templates.friends.Friend;
import com.example.projectforjava.templates.friends.FriendRequest;
import com.example.projectforjava.utils.ImgUtils;

import java.util.ArrayList;

public class AddFriendsFragment extends Fragment {

    public AddFriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_friends, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.peopleRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<AddFriend> fList = new ArrayList<>();
        // Заполните friendList вашими данными
        fList.add(new AddFriend(ImgUtils.BitmapToDrawable(ImgUtils.getRoundedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.my_friend)), requireContext()),
                "Baboba", 10, 6, false));
        fList.add(new AddFriend(ImgUtils.BitmapToDrawable(ImgUtils.getRoundedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.my_friend)), requireContext()),
                "Angry Rhino", 70, 1, false));
        fList.add(new AddFriend(ImgUtils.BitmapToDrawable(ImgUtils.getRoundedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.my_friend)), requireContext()),
                "Handsome guy", 15, 125, false));
        // Добавьте другие объекты Friend в friendList

        AddFriendsAdapter adapter = new AddFriendsAdapter(fList, getContext());
        recyclerView.setAdapter(adapter);

        EditText editTextSearch = view.findViewById(R.id.editTextSearchPeople);
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
}