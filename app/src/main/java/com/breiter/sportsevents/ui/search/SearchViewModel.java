package com.breiter.sportsevents.ui.search;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.breiter.sportsevents.data.MainRepository;
import com.breiter.sportsevents.data.model.Team;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {
    private MainRepository mainRepository;

    public SearchViewModel(Application application) {
        super(application);
        mainRepository = new MainRepository(application);
    }

    LiveData<List<Team>> getTeamList(String teamName) {
        return mainRepository.getTeamByName(teamName);
    }

    LiveData<Team> getTeamById(String id) {
        return mainRepository.getTeam(id);
    }
}
