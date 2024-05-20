package com.example.projectforjava.adapters.leaderboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectforjava.R;
import com.example.projectforjava.templates.leaderboard.UserOnLeaderboard;

import java.util.List;

public class UserOnLeaderboardAdapter extends RecyclerView.Adapter<UserOnLeaderboardAdapter.ViewHolder> {

    private List<UserOnLeaderboard> users;

    public UserOnLeaderboardAdapter(List<UserOnLeaderboard> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person_on_leaderboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserOnLeaderboard user = users.get(position);

        holder.positionOnLeaderboard.setText(String.valueOf(position + 1));
        holder.personOnLeaderboardIcon.setImageDrawable(user.getDrawable());
        holder.personOnLeaderboardName.setText(user.getName());
        holder.personalLvl.setText("Level: " + user.getPersonalLvl());
        holder.totalGlobalChallengesCompleted.setText("Challenges Completed: " + user.getTotalGlobalChallengesCompleted());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView positionOnLeaderboard;
        ImageView personOnLeaderboardIcon;
        TextView personOnLeaderboardName;
        TextView personalLvl;
        TextView totalGlobalChallengesCompleted;

        public ViewHolder(View itemView) {
            super(itemView);
            positionOnLeaderboard = itemView.findViewById(R.id.positionOnLeaderboard);
            personOnLeaderboardIcon = itemView.findViewById(R.id.personOnLeaderboardIcon);
            personOnLeaderboardName = itemView.findViewById(R.id.personOnLeaderboardName);
            personalLvl = itemView.findViewById(R.id.personalLvl);
            totalGlobalChallengesCompleted = itemView.findViewById(R.id.totalGlobalChallengesCompleted);
        }
    }
}

