package com.breiter.sportsevents.ui.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.breiter.sportsevents.data.MainRepository;
import com.breiter.sportsevents.data.model.Match;
import com.breiter.sportsevents.data.model.Team;

import java.util.List;

public class TeamDetailsViewModel extends AndroidViewModel {
    private MainRepository mainRepository;
    private MediatorLiveData<List<Match>> mediatorLiveData;

    public TeamDetailsViewModel(@NonNull Application application) {
        super(application);
        mainRepository = new MainRepository(application);
        mediatorLiveData = new MediatorLiveData<>();
    }

     LiveData<List<Team>> getTeamById(String teamId) {
        return mainRepository.searchTeamById(teamId);
    }

    private void mergeLists(String id) {

        mediatorLiveData.addSource(mainRepository.getEvents(id), events -> mediatorLiveData.setValue(events));

        mediatorLiveData.addSource(mainRepository.getResults(id), results -> mediatorLiveData.setValue(results));
    }

     LiveData<List<Match>> getTeamEvents(String teamId) {
        mergeLists(teamId);
        return mediatorLiveData;
    }

     void saveTeam (Team team){
        mainRepository.addTeam(team);
    }

     void updateTeam(Team team){
        mainRepository.updateTeam(team);
    }
}
