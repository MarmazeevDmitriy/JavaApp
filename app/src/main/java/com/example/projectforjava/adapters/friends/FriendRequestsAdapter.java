package com.example.projectforjava.adapters.friends;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectforjava.R;
import com.example.projectforjava.templates.friends.Friend;
import com.example.projectforjava.templates.friends.FriendRequest;

import java.util.List;

public class FriendRequestsAdapter extends RecyclerView.Adapter<FriendRequestsAdapter.FriendRequestsViewHolder> {

    private List<FriendRequest> friendRequests;

    public FriendRequestsAdapter(List<FriendRequest> friendRequests) {
        this.friendRequests = friendRequests;
    }

    @NonNull
    @Override
    public FriendRequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_request, parent, false);
        return new FriendRequestsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FriendRequest friendRequest = friendRequests.get(position);
        holder.icon.setImageDrawable(friendRequest.getIcon());
        holder.name.setText(friendRequest.getName());
        holder.friendsTotal.setText("Friends Total: " + friendRequest.getFriendsTotal());
        holder.globalChallengesCompleted.setText("Challenges Completed: " + friendRequest.getGlobalChallengesCompleted());
        holder.deleteRequest.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Friend request rejected", Toast.LENGTH_SHORT).show();
                friendRequests.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.acceptRequest.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Friend request accepted", Toast.LENGTH_SHORT).show();
                friendRequests.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendRequests.size();
    }

    public static class FriendRequestsViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        TextView friendsTotal;
        TextView globalChallengesCompleted;
        ImageButton deleteRequest;
        ImageButton acceptRequest;

        public FriendRequestsViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.personsIcon);
            name = itemView.findViewById(R.id.personsName);
            friendsTotal = itemView.findViewById(R.id.friendsTotal);
            globalChallengesCompleted = itemView.findViewById(R.id.globalChallengesCompleted);
            deleteRequest = itemView.findViewById(R.id.deleteRequest);
            acceptRequest = itemView.findViewById(R.id.acceptRequest);
        }
    }
}
