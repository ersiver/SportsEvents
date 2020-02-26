package com.breiter.sportsevents.ui.search;

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

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private List<Team> teamList;
    private OnTeamClickListener listener;


     SearchAdapter(Context context) {
        this.context = context;
        teamList = new ArrayList<>();
    }

     void setTeamList(List<Team> teamList){
        this.teamList = teamList;
        notifyDataSetChanged();
    }

     void clearList(){
     teamList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Team team = teamList.get(position);
        holder.bind(team);


    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }


     class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView badgeImageView;
        private ImageView sportLogo;
        private TextView teamNameTextView;


         ViewHolder(@NonNull View itemView) {
            super(itemView);
            badgeImageView = itemView.findViewById(R.id.badgeImageView);
            teamNameTextView = itemView.findViewById(R.id.thisNameTextView);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(listener!=null && position != RecyclerView.NO_POSITION )
                    listener.onTeamClicked(teamList.get(position));
            });

        }

        private void bind(Team team) {

            if(team.getBadge()!=null)
                Glide.with(context).load(team.getBadge()).into(badgeImageView);
            else
                badgeImageView.setImageResource(R.drawable.default_badge);

            String teamName = team.getName() != null ? team.getName() : "";
            teamNameTextView.setText(teamName);
        }
    }


    public interface OnTeamClickListener {
        void onTeamClicked(Team team);
    }

     void setOnTeamClickListener(OnTeamClickListener listener){
        this.listener = listener;
    }
}
