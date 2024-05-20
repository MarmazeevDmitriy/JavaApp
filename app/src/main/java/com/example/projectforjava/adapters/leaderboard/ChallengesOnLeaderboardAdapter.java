package com.example.projectforjava.adapters.leaderboard;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectforjava.R;
import com.example.projectforjava.templates.leaderboard.ChallengeOnLeaderboard;

import java.util.List;

public class ChallengesOnLeaderboardAdapter extends RecyclerView.Adapter<ChallengesOnLeaderboardAdapter.ViewHolder> {

    private List<ChallengeOnLeaderboard> challengeList;

    public ChallengesOnLeaderboardAdapter(List<ChallengeOnLeaderboard> challengeList) {
        this.challengeList = challengeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_challenge_on_leaderboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChallengeOnLeaderboard challenge = challengeList.get(position);
        holder.imageView.setImageDrawable(challenge.getImage());
        holder.titleView.setText(challenge.getTitle());
        holder.descriptionView.setText(challenge.getDescription());
        holder.completionsView.setText(String.valueOf(challenge.getCompletions()));
    }

    @Override
    public int getItemCount() {
        return challengeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleView;
        public TextView descriptionView;
        public TextView completionsView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.onLeaderboardChallengeImage);
            titleView = itemView.findViewById(R.id.onLeaderboardChallengeTitle);
            descriptionView = itemView.findViewById(R.id.onLeaderboardChallengeDescription);
            completionsView = itemView.findViewById(R.id.onLeaderboardChallengeTimesCompleted);
        }
    }
}
