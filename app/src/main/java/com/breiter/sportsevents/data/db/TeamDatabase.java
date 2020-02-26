package com.breiter.sportsevents.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.breiter.sportsevents.data.model.Team;

@Database(entities = {Team.class}, version=1, exportSchema = false)
public abstract class TeamDatabase extends RoomDatabase {
    private static TeamDatabase instance;
    public abstract TeamDao teamDao();

    public static synchronized TeamDatabase getInstance(Context context){
        if(instance==null)
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TeamDatabase.class,
                    "team_table")
                    .fallbackToDestructiveMigration()
                    .build();

        return instance;
    }

}
