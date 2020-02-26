package com.breiter.sportsevents.ui.saved;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.breiter.sportsevents.data.MainRepository;
import com.breiter.sportsevents.data.model.Team;

import java.util.List;

public class SavedViewModel extends AndroidViewModel {
    private MainRepository mainRepository;
    private MediatorLiveData<List<Team>> allTeams;

    public SavedViewModel(@NonNull Application application) {
        super(application);
        mainRepository = new MainRepository(application);
        allTeams = new MediatorLiveData<>();
        getAllTeams();
    }

    private void getAllTeams() {
        allTeams.addSource(mainRepository.getSavedTeams(), teams -> allTeams.postValue(teams));
    }

    LiveData<List<Team>> getSavedTeams() {
        return allTeams;
    }

    void deleteFromSaved(Team team) {
        mainRepository.deleteTeam(team);
    }
}
