package com.example.projectforjava.adapters.friends;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectforjava.R;
import com.example.projectforjava.templates.friends.AddFriend;

import java.util.ArrayList;
import java.util.List;

public class AddFriendsAdapter extends RecyclerView.Adapter<AddFriendsAdapter.AddFriendViewHolder>  {
    private List<AddFriend> addFriends;
    private List<AddFriend> filteredList;

    private Context context;

    public AddFriendsAdapter(List<AddFriend> addFriends, Context context) {
        this.addFriends = addFriends;
        this.filteredList = new ArrayList<>(addFriends);
        this.context = context;
    }

    @NonNull
    @Override
    public AddFriendsAdapter.AddFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_friend, parent, false);
        return new AddFriendsAdapter.AddFriendViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddFriendsAdapter.AddFriendViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AddFriend addFriend = filteredList.get(position);
        holder.icon.setImageDrawable(addFriend.getIcon());
        holder.name.setText(addFriend.getName());
        holder.friendsTotal.setText("Friends Total: " + addFriend.getFriendsTotal());
        holder.globalChallengesCompleted.setText("Challenges Completed: " + addFriend.getGlobalChallengesCompleted());
        if(addFriend.isRequestSent()){
            holder.sendFriendRequest.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        }
        holder.sendFriendRequest.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                if(!addFriend.isRequestSent()){
                    holder.sendFriendRequest.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
                    Toast.makeText(context, "Friend request sent", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filter(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(addFriends);
        } else {
            text = text.toLowerCase();
            for (AddFriend item : addFriends) {
                if (item.getName().toLowerCase().contains(text)) {
                    filteredList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class AddFriendViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        TextView friendsTotal;
        TextView globalChallengesCompleted;
        ImageButton sendFriendRequest;

        public AddFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.personsIcon);
            name = itemView.findViewById(R.id.addFriendPersonsName);
            friendsTotal = itemView.findViewById(R.id.friendsAmount);
            globalChallengesCompleted = itemView.findViewById(R.id.globalChallengesAmount);
            sendFriendRequest = itemView.findViewById(R.id.sendFriendRequest);
        }
    }
}
