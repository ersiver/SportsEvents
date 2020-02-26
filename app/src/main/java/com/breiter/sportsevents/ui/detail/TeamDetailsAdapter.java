package com.breiter.sportsevents.ui.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.breiter.sportsevents.R;
import com.breiter.sportsevents.data.model.Match;
import com.breiter.sportsevents.data.model.Team;
import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamDetailsAdapter extends RecyclerView.Adapter<TeamDetailsAdapter.ViewHolder> {
    private Context context;
    private Team team;
    private List<Match> matchList;

     TeamDetailsAdapter(Context context, Team team) {
        this.context = context;
        this.team = team;
        matchList = new ArrayList<>();
    }

    public void setList(List<Match> matchList) {
        this.matchList.addAll(matchList);
        Collections.sort(this.matchList); //date order
        notifyDataSetChanged();
    }

     void updateMatch(Match match) {
        matchList.set(matchList.indexOf(match), match);
        notifyItemChanged(matchList.indexOf(match));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Match match = matchList.get(position);
        holder.bind(match);
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        private SpinKitView thisBadgeLoader;
        private SpinKitView otherBadgeLoader;

        private TextView leagueTextView;
        private TextView dateTextView;
        private TextView timeTextView;

        private ImageView thisBadgeImageView;
        private TextView thisNameTextView;
        private TextView thisScoreTextView;

        private TextView otherScoreTextView;
        private TextView otherNameTextView;
        private ImageView otherBadgeImageView;


         ViewHolder(@NonNull View itemView) {
            super(itemView);

            thisBadgeLoader = itemView.findViewById(R.id.thisBadgeLoader);
            otherBadgeLoader = itemView.findViewById(R.id.otherBadgeLoader);

            leagueTextView = itemView.findViewById(R.id.leagueTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);

            thisBadgeImageView = itemView.findViewById(R.id.thisBadgeImageView);
            thisNameTextView = itemView.findViewById(R.id.thisNameTextView);
            thisScoreTextView = itemView.findViewById(R.id.thisScoreTextView);

            otherScoreTextView = itemView.findViewById(R.id.otherScoreTextView);
            otherNameTextView = itemView.findViewById(R.id.otherNameTextView);
            otherBadgeImageView = itemView.findViewById(R.id.otherBadgeImageView);
        }

         void bind(Match match) {
            String league = match.getLeague() != null ? match.getLeague() : "";
            String matchDate = match.getDate() != null ? match.getDate() : "";
            String matchTime = match.getTime() != null ? match.getMatchTime() : "";
            String homeTeam = match.getHomeTeam() != null ? match.getHomeTeam() : "";
            String homeScore = match.getHomeScore() != null ? match.getHomeScore() : "";
            String homeBadgePath = match.getHomeBadge() != null ? match.getHomeBadge() : "";
            String guestTeam = match.getGuestTeam() != null ? match.getGuestTeam() : "";
            String guestScore = match.getGueastScore() != null ? match.getGueastScore() : "";
            String guestBadge = match.getGuestBadge() != null ? match.getGuestBadge() : "";

            leagueTextView.setText(league);
            dateTextView.setText(matchDate);
            timeTextView.setText(matchTime);

            if (team.getTeamId().equals(match.getIdHomeTeam())) {
                thisNameTextView.setText(homeTeam);
                thisScoreTextView.setText(homeScore);
                loadBadge(thisBadgeImageView, homeBadgePath, thisBadgeLoader);
                otherNameTextView.setText(guestTeam);
                otherScoreTextView.setText(guestScore);
                loadBadge(otherBadgeImageView, guestBadge, otherBadgeLoader);

            } else {
                thisNameTextView.setText(guestTeam);
                thisScoreTextView.setText(guestScore);
                loadBadge(thisBadgeImageView, guestBadge, thisBadgeLoader);
                otherNameTextView.setText(homeTeam);
                otherScoreTextView.setText(homeScore);
                loadBadge(otherBadgeImageView, homeBadgePath, otherBadgeLoader);
            }
        }

        private void loadBadge(ImageView imageView, String badgePath, SpinKitView loader) {
            if (!badgePath.isEmpty()) {
                loader.setVisibility(View.GONE);
                Glide.with(context).load(badgePath).into(imageView);
            } else
                loader.setVisibility(View.VISIBLE);
        }
    }
}
