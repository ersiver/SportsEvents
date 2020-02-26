package com.breiter.sportsevents.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.breiter.sportsevents.data.model.Team;

import java.util.List;

@Dao
public interface TeamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTeam(Team team);

    @Delete
    void deleteTeam(Team team);

    @Update
    void updateTeam(Team team);

    //Get all saved items, the newest top
    @Query("SELECT * FROM team_table ORDER BY id DESC")
    LiveData<List<Team>> getSavedTeams();

    //To check, if the item is already in database
    @Query("SELECT * FROM team_table WHERE teamId= :id")
    LiveData<Team> getTeamById(String id);
}
