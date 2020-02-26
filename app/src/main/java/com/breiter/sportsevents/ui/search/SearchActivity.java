package com.breiter.sportsevents.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.breiter.sportsevents.R;
import com.breiter.sportsevents.data.model.Team;
import com.breiter.sportsevents.ui.detail.TeamDetailsActivity;
import com.google.gson.Gson;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private EditText queryEditText;
    private SearchViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        initViews();              //1
        displayListWhileTyping(); //2
        clickToSearch();          //3
        clickToShowTeamDetails(); //4
        clickToClearText();       //5
        clickToGoBack();          //6
    }

    //1
    private void initViews() {
        queryEditText = findViewById(R.id.queryEditText);

        //Recycler & adapter
        recyclerView = findViewById(R.id.teamsRecyclerView);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new SearchAdapter(SearchActivity.this);
        recyclerView.setAdapter(adapter);
    }

    //2
    private void displayListWhileTyping() {

        queryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2)
                    getTeamList(s.toString()); //3b
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //3
    private void clickToSearch() {
        ImageView searchImageView = findViewById(R.id.searchImageView);
        searchImageView.setOnClickListener(v -> {
            String query = queryEditText.getText().toString().trim();
            if (!query.isEmpty())
                getTeamList(query); //3b
            else
                Toast.makeText(SearchActivity.this, "Enter team name", Toast.LENGTH_SHORT).show();
        });
    }

    //3b
    private void getTeamList(String query) {
        viewModel.getTeamList(query).observe(this, teams -> {
            if (teams != null)
                adapter.setTeamList(teams);
        });
    }

    //4
    private void clickToShowTeamDetails() {
        adapter.setOnTeamClickListener(team -> {
            goToTeamDetailsActivity(team); //4b
        });
    }

    //4b Inspect, if the item is already saved in a local db
    private void goToTeamDetailsActivity(Team thisTeam) {
        Intent intent = new Intent(this, TeamDetailsActivity.class);
        Gson gson = new Gson();

        viewModel.getTeamById(thisTeam.getTeamId()).observe(this, new Observer<Team>() {
            String json = "";
            @Override
            public void onChanged(Team team) {
                if (team != null)
                    json = gson.toJson(team);
                else
                    json = gson.toJson(thisTeam);

                intent.putExtra("TEAM", json);
                startActivity(intent);
            }
        });
    }

    //5
    private void clickToClearText() {
        ImageView clearTextImageView = findViewById(R.id.clearTextImageView);

        clearTextImageView.setOnClickListener(v -> {
            queryEditText.getText().clear();
            adapter.clearList();
        });
    }

    //6
    private void clickToGoBack() {
        ImageView backImageView = findViewById(R.id.backImageView);
        backImageView.setOnClickListener(v -> {
            goToSavedActivity(); //6a
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToSavedActivity(); //6a
    }

    //6a
    private void goToSavedActivity() {
        NavUtils.navigateUpFromSameTask(this);
    }
}




