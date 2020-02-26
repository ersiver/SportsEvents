package com.breiter.sportsevents.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.breiter.sportsevents.data.db.TeamDao;
import com.breiter.sportsevents.data.db.TeamDatabase;
import com.breiter.sportsevents.data.model.DatabaseResponse;
import com.breiter.sportsevents.data.model.Match;
import com.breiter.sportsevents.data.model.Team;
import com.breiter.sportsevents.data.net.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    private static final String TAG = "MainRepository";
    private TeamDao teamDao;

    public MainRepository(Application application) {
        TeamDatabase teamDatabase = TeamDatabase.getInstance(application);
        teamDao = teamDatabase.teamDao();
    }

    //Retrofit call to get team list
    public LiveData<List<Team>> getTeamByName(String teamName) {
        final MutableLiveData<List<Team>> teamByName = new MutableLiveData<>();
        Call<DatabaseResponse> call = RetrofitClient.getTeam(teamName);
        call.enqueue(new Callback<DatabaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<DatabaseResponse> call, @NonNull Response<DatabaseResponse> response) {
                if (response.isSuccessful()) {
                    DatabaseResponse databaseResponse = response.body();

                    if (databaseResponse != null)
                        teamByName.setValue(databaseResponse.getTeams());
                } else
                    Log.i(TAG, "Error: " + response.errorBody());
            }

            @Override
            public void onFailure(@NonNull Call<DatabaseResponse> call, @NonNull Throwable t) {
                Log.i(TAG, "Error " + t.getMessage());

            }
        });

        return teamByName;
    }

    //Retrofit call to get team by SportDatabase id
    public LiveData<List<Team>> searchTeamById(String teamId) {

        final MutableLiveData<List<Team>> teamsById = new MutableLiveData<>();

        Call<DatabaseResponse> call = RetrofitClient.getTeamById(teamId);
        call.enqueue(new Callback<DatabaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<DatabaseResponse> call, @NonNull Response<DatabaseResponse> response) {
                if (response.isSuccessful()) {
                    DatabaseResponse databaseResponse = response.body();

                    if (databaseResponse != null)
                        teamsById.setValue(databaseResponse.getTeams());
                } else
                    Log.i(TAG, "Error: " + response.errorBody());
            }

            @Override
            public void onFailure(@NonNull Call<DatabaseResponse> call,@NonNull Throwable t) {
                Log.i(TAG, "Error " + t.getMessage());
            }
        });

        return teamsById;
    }

    //Retrofit call to get match results
    public LiveData<List<Match>> getResults(String teamId) {

        final MutableLiveData<List<Match>> results = new MutableLiveData<>();

        Call<DatabaseResponse> call = RetrofitClient.getResults(teamId);
        call.enqueue(new Callback<DatabaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<DatabaseResponse> call, @NonNull Response<DatabaseResponse> response) {
                if (response.isSuccessful()) {
                    DatabaseResponse databaseResponse = response.body();

                    if (databaseResponse != null)
                        results.setValue(databaseResponse.getResults());
                } else
                    Log.i(TAG, "Error: " + response.errorBody());
            }

            @Override
            public void onFailure(@NonNull Call<DatabaseResponse> call, @NonNull Throwable t) {
                Log.i(TAG, "Error " + t.getMessage());
            }
        });

        return results;
    }

    //Retrofit call to get incoming events
    public LiveData<List<Match>> getEvents(String teamId) {

        final MutableLiveData<List<Match>> events = new MutableLiveData<>();

        Call<DatabaseResponse> call = RetrofitClient.getEvents(teamId);
        call.enqueue(new Callback<DatabaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<DatabaseResponse> call,@NonNull  Response<DatabaseResponse> response) {
                if (response.isSuccessful()) {
                    DatabaseResponse databaseResponse = response.body();

                    if (databaseResponse != null)
                        events.setValue(databaseResponse.getEvents());
                } else
                    Log.i(TAG, "Error: " + response.errorBody());
            }

            @Override
            public void onFailure(@NonNull Call<DatabaseResponse> call,@NonNull Throwable t) {
                Log.i(TAG, "Error " + t.getMessage());
            }
        });

        return events;
    }


    //1. Inserting new team to db
    public void addTeam(Team team){
        new AddTeamAsyncTask(teamDao).execute(team);

    }
    private static class AddTeamAsyncTask extends AsyncTask<Team, Void, Void> {
        private TeamDao teamDao;
         AddTeamAsyncTask(TeamDao teamDao) {
            this.teamDao = teamDao;
        }

        @Override
        protected Void doInBackground(Team... teams) {
            teamDao.addTeam(teams[0]);
            return null;
        }
    }

    //2. Deleting team from db
    public void deleteTeam(Team team){
        new DeleteAsyncTask(teamDao).execute(team);
    }

    private static class DeleteAsyncTask extends AsyncTask<Team, Void, Void> {
        private TeamDao teamDao;
         DeleteAsyncTask(TeamDao teamDao) {
            this.teamDao = teamDao;
        }

        @Override
        protected Void doInBackground(Team... teams) {
            teamDao.deleteTeam(teams[0]);
            return null;
        }
    }

    //3. Updating the team
    public void updateTeam(Team team){
        new UpdateTeamAsyncTask(teamDao).execute(team);
    }

    private static class UpdateTeamAsyncTask extends AsyncTask<Team, Void, Void> {
        private TeamDao teamDao;
         UpdateTeamAsyncTask(TeamDao teamDao) {
            this.teamDao = teamDao;
        }

        @Override
        protected Void doInBackground(Team... teams) {
            teamDao.updateTeam(teams[0]);
            return null;
        }
    }

    //4. Getting all saved teams
    public LiveData<List<Team>> getSavedTeams(){
        return teamDao.getSavedTeams();
    }

    //5. Getting team by its id from local database
    public LiveData<Team> getTeam(String id){
        return teamDao.getTeamById(id);
    }
}


