package com.breiter.sportsevents.ui.saved;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breiter.sportsevents.R;
import com.breiter.sportsevents.data.model.Team;
import com.breiter.sportsevents.ui.detail.TeamDetailsActivity;
import com.breiter.sportsevents.ui.search.SearchActivity;
import com.google.gson.Gson;

import java.util.List;

public class SavedActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SavedAdapter adapter;
    private SavedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        viewModel = new ViewModelProvider(this).get(SavedViewModel.class);

        initRecyclerView();       //1
        getSavedTeams();          //2
        clickToShowTeamDetails(); //3
        clickToSearchTeam();      //4
        swipeToDeleteMovie();     //5
    }

    //1
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.savedRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new SavedAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    //2
    private void getSavedTeams() {
        viewModel.getSavedTeams().observe(this, teams -> adapter.setSavedTeams(teams));
    }

    //3
    private void clickToShowTeamDetails() {
        adapter.setOnTeamListener(team -> {
            goToTeamDetailsActivity(team); //3b
        });
    }

    //3b
    private void goToTeamDetailsActivity(Team team) {
        Intent intent = new Intent(SavedActivity.this, TeamDetailsActivity.class);
        Gson gson = new Gson();
        String json = gson.toJson(team);
        intent.putExtra("TEAM", json);
        startActivity(intent);
    }

    //4
    private void clickToSearchTeam() {
        ImageView searchImageView = findViewById(R.id.searchImageView);

        searchImageView.setOnClickListener(v -> {
            goToSearchActivity(); //4b
        });
    }

    //4b
    private void goToSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    //5
    private void swipeToDeleteMovie() {
        ItemSwipeHelper itemSwipeHelper = new ItemSwipeHelper(this, recyclerView) {

            @Override
            public void initUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new UnderlayButton(
                        getApplicationContext(),
                        R.drawable.ic_delete,
                        Color.parseColor("#FF3C30"), //red
                        position -> viewModel.deleteFromSaved(adapter.getTeamAt(position))
                ));
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemSwipeHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
