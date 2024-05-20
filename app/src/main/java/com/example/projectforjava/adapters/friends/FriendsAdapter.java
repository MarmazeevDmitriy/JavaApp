package com.example.projectforjava.adapters.friends;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectforjava.R;
import com.example.projectforjava.templates.friends.Friend;

import java.util.ArrayList;
import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendViewHolder> {

    private List<Friend> friends;
    private List<Friend> filteredList;
    private List<String> chosenFriends = new ArrayList<>();

    private OnItemClickListener listener;

    private boolean chooseFriends;

    public FriendsAdapter(List<Friend> friends, boolean chooseFriends) {
        this.friends = friends;
        this.filteredList = new ArrayList<>(friends);
        this.chooseFriends = chooseFriends;
    }

    public FriendsAdapter(List<Friend> friends, boolean chooseFriends, List<String> chosenFriends) {
        this.friends = friends;
        this.filteredList = new ArrayList<>(friends);
        this.chooseFriends = chooseFriends;
        this.chosenFriends = chosenFriends;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        return new FriendViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Friend friend = filteredList.get(position);
        holder.icon.setImageDrawable(friend.getIcon());
        holder.name.setText(friend.getName());
        holder.challengesSent.setText("Challenges Sent: " + friend.getChallengesSent());
        holder.challengesCompleted.setText("Challenges Completed: " + friend.getChallengesCompleted());
        if(chooseFriends){
            holder.removeFriend.setVisibility(View.GONE);
            if(chosenFriends.contains(friend.getName())){
                holder.itemView.setBackgroundResource(R.drawable.friend_challeng_completed);
            }
        }
        else {
            holder.removeFriend.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    friends.remove(filteredList.get(position));
                    filteredList.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public Friend getItem(int position){
        return filteredList.get(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filter(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(friends);
        } else {
            text = text.toLowerCase();
            for (Friend item : friends) {
                if (item.getName().toLowerCase().contains(text)) {
                    filteredList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        TextView challengesSent;
        TextView challengesCompleted;
        Button removeFriend;

        public FriendViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            icon = itemView.findViewById(R.id.friendsIcon);
            name = itemView.findViewById(R.id.friendsName);
            challengesSent = itemView.findViewById(R.id.challengesSent);
            challengesCompleted = itemView.findViewById(R.id.challengesCompleted);
            removeFriend = itemView.findViewById(R.id.removeFriend);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
