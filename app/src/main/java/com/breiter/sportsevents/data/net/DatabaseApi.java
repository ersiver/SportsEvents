package com.breiter.sportsevents.data.net;

import com.breiter.sportsevents.data.model.DatabaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DatabaseApi {

    @GET("eventslast.php?")
    Call<DatabaseResponse> getResults(@Query ("id") String teamId);

    @GET("eventsnext.php?")
    Call<DatabaseResponse> getEvents(@Query ("id") String teamId);

    @GET("searchteams.php?")
    Call<DatabaseResponse> getTeam(@Query ("t") String query);

    @GET("lookupteam.php?")
    Call<DatabaseResponse> getTeamById(@Query ("id") String query);

}
