package com.breiter.sportsevents.ui.saved;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.breiter.sportsevents.R;
import com.breiter.sportsevents.data.model.Team;
import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder> {
    private Context context;
    private List<Team> savedTeams;
    private OnTeamClickListener listener;

     SavedAdapter(Context context) {
        this.context = context;
        savedTeams = new ArrayList<>();
    }

     void setSavedTeams(List<Team> savedTeams) {
        this.savedTeams = savedTeams;
        notifyDataSetChanged();
    }

     Team getTeamAt(int position) {
        return savedTeams.get(position);
    }

     void setOnTeamListener(OnTeamClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Team savedTeam = savedTeams.get(position);
        holder.bind(savedTeam);
    }

    @Override
    public int getItemCount() {
        return savedTeams.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView teamBadgeImageView;
        private TextView teamNameTextView;
        private SpinKitView badgeLoader;

         ViewHolder(@NonNull View itemView) {
            super(itemView);

            teamBadgeImageView = itemView.findViewById(R.id.teamBadge);
            teamNameTextView = itemView.findViewById(R.id.teamNameTextView);
            badgeLoader = itemView.findViewById(R.id.badgeLoader);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position!= RecyclerView.NO_POSITION)
                    listener.onClick(savedTeams.get(position));
            });
        }

        private void bind(Team savedTeam) {
            String name = savedTeam.getName() != null ? savedTeam.getName() : "";
            String badgePath = savedTeam.getBadge() != null ? savedTeam.getBadge() : "";

            teamNameTextView.setText(name);
            loadBadge(teamBadgeImageView, badgePath, badgeLoader);
        }

        private void loadBadge(ImageView imageView, String badgePath, SpinKitView loader) {
            if (!badgePath.isEmpty()) {
                loader.setVisibility(View.GONE);
                Glide.with(context).load(badgePath).into(imageView);
            } else
                loader.setVisibility(View.VISIBLE);
        }
    }

    public interface OnTeamClickListener {
         void onClick(Team team);
    }
}
