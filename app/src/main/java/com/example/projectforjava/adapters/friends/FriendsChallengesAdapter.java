package com.example.projectforjava.adapters.friends;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectforjava.R;
import com.example.projectforjava.templates.friends.FriendsChallenge;

import java.util.ArrayList;
import java.util.List;

public class FriendsChallengesAdapter extends RecyclerView.Adapter<FriendsChallengesAdapter.ViewHolder> {

    private List<FriendsChallenge> friendsChallengeList;
    private List<FriendsChallenge> friendsChallengeListFull;
    private OnItemClickListener listener;

    private String lastSearchText = "";
    private boolean toFriends;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public FriendsChallengesAdapter(List<FriendsChallenge> friendsChallengeList, boolean toFriends) {
        this.friendsChallengeList = new ArrayList<>(friendsChallengeList);
        this.friendsChallengeListFull = new ArrayList<>(friendsChallengeList);
        this.toFriends = toFriends;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friends_challenge, parent, false);
        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FriendsChallenge currentChallenge = friendsChallengeList.get(position);
        holder.imageView.setImageDrawable(currentChallenge.getImage());
        holder.titleView.setText(currentChallenge.getTitle());
        holder.descriptionView.setText(currentChallenge.getDescription());
        if(toFriends){
            holder.toFriendsNames.setText("Получатели: " + String.join(", ", currentChallenge.getReceiversNames()));
            holder.toFriendsNames.setVisibility(View.VISIBLE);
        }
        else {
            holder.fromFriendName.setText("Отправитель: " + currentChallenge.getSenderName());
            holder.fromFriendName.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return friendsChallengeList.size();
    }

    public void addItem(FriendsChallenge friendsChallenge){
        friendsChallengeListFull.add(friendsChallenge);
        if(lastSearchText.isEmpty() || friendsChallenge.getTitle().toLowerCase().contains(lastSearchText)){
            friendsChallengeList.add(friendsChallenge);
            notifyItemInserted(friendsChallengeList.size() - 1);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateItem(FriendsChallenge friendsChallenge, int position){
        deleteItem(position);
        addItem(friendsChallenge);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void deleteItem(int position){
        if(position != -1){
            friendsChallengeListFull.remove(friendsChallengeList.get(position));
            friendsChallengeList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public FriendsChallenge getItem (int pos){
        return friendsChallengeList.get(pos);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filter(String text) {
        lastSearchText = text;
        friendsChallengeList.clear();
        if (text.isEmpty()) {
            friendsChallengeList.addAll(friendsChallengeListFull);
        } else {
            text = text.toLowerCase();
            for (FriendsChallenge item : friendsChallengeListFull) {
                if (item.getTitle().toLowerCase().contains(text)) {
                    friendsChallengeList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleView;
        public TextView descriptionView;
        public TextView toFriendsNames;
        public TextView fromFriendName;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.friendChallengeImage);
            titleView = itemView.findViewById(R.id.friendChallengeTitle);
            descriptionView = itemView.findViewById(R.id.frienChallengeDescription);
            toFriendsNames = itemView.findViewById(R.id.toFriendsNames);
            fromFriendName = itemView.findViewById(R.id.fromFriendName);

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
