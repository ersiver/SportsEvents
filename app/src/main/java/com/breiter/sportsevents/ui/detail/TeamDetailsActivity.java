package com.breiter.sportsevents.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breiter.sportsevents.R;
import com.breiter.sportsevents.data.model.Match;
import com.breiter.sportsevents.data.model.Team;
import com.breiter.sportsevents.ui.search.SearchActivity;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TeamDetailsActivity extends AppCompatActivity {
    private Team team;
    private TeamDetailsViewModel viewModel;
    private RecyclerView recyclerView;
    private TeamDetailsAdapter adapter;
    private ImageView saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);
        if(getSupportActionBar()!=null)
        getSupportActionBar().hide();

        viewModel = new ViewModelProvider(this).get(TeamDetailsViewModel.class);
        saveButton = findViewById(R.id.saveButton);

        getTeamFromIntent();   //1
        initRecyclerView();    //2
        setupToolbar();        //3
        getTeamEvents();       //4
        manageSaveButton();    //5
        clickToSaveTeam();     //6
        clickToGoBack();       //7
        clickToSearchTeam();   //8
    }

    //1
    private void getTeamFromIntent() {
        Intent intent = getIntent();
        Type type = new TypeToken<Team>() {
        }.getType();
        Gson gson = new Gson();
        team = gson.fromJson(intent.getStringExtra("TEAM"), type);
    }

    //2
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.eventsResultsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(TeamDetailsActivity.this));
        recyclerView.setHasFixedSize(true);
        adapter = new TeamDetailsAdapter(TeamDetailsActivity.this, team);
        recyclerView.setAdapter(adapter);
    }

    //3. Setup team badge and name in toolbar
    private void setupToolbar() {
        TextView teamNameTextView = findViewById(R.id.thisNameTextView);
        ImageView badgeImageView = findViewById(R.id.badgeImageView);

        teamNameTextView.setText(team.getName()!=null ? team.getName(): "");

        if (team.getBadge() != null)
            Glide.with(TeamDetailsActivity.this).load(team.getBadge()).into(badgeImageView);
        else
            badgeImageView.setImageResource(R.drawable.default_badge);
    }

    //4 Search all past and incoming events by SportDatabse teamId.
    private void getTeamEvents() {
        viewModel.getTeamEvents(team.getTeamId()).observe(this, eventResults -> {
            if (eventResults != null) {
                adapter.setList(eventResults);
                addBadges(eventResults); //4b
            }
        });
    }

    //4b
    public void addBadges(List<Match> matchList) {
        for (Match match : matchList) {
            viewModel.getTeamById(match.getIdHomeTeam()).observe(this, teams -> {
                if (teams != null && !teams.isEmpty()) {
                    Team homeTeam = teams.get(0);
                    if (homeTeam != null) {
                        match.setHomeBadge(homeTeam.getBadge());
                        adapter.updateMatch(match);
                    }
                }
            });

            viewModel.getTeamById(match.getIdGuestTeam()).observe(this, teams -> {
                if (teams != null && !teams.isEmpty()) {
                    Team guestTeam = teams.get(0);
                    if (guestTeam != null) {
                        match.setGuestBadge(guestTeam.getBadge());
                        adapter.updateMatch(match);
                    }
                }
            });
        }
    }

    //5 Hide if the team is already saved
    private void manageSaveButton() {
        if (team.isSaved())
            saveButton.setVisibility(View.INVISIBLE);
        else
            saveButton.setVisibility(View.VISIBLE);
    }

    //6
    private void clickToSaveTeam() {
        saveButton.setOnClickListener(v -> {
            saveTeam(); //6b
        });
    }

    //6b
    private void saveTeam() {
        viewModel.saveTeam(team);
        team.setSaved(true);
        viewModel.updateTeam(team);
        manageSaveButton();
        Toast.makeText(this, "Team saved!", Toast.LENGTH_SHORT).show();
    }

    //7
    private void clickToGoBack() {
        ImageView backImageView = findViewById(R.id.backImageView);
        backImageView.setOnClickListener(v -> {
            goToSavedActivity(); //6a
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToSavedActivity(); //7a
    }

    //7a
    private void goToSavedActivity() {
        NavUtils.navigateUpFromSameTask(this);
    }

    //8
    private void clickToSearchTeam() {
        ImageView searchImageView = findViewById(R.id.searchImageView);

        searchImageView.setOnClickListener(v -> {
            goToSearchActivity(); //8b
        });
    }

    //8b
    private void goToSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}
