package com.breiter.sportsevents.data.net;

import com.breiter.sportsevents.data.model.DatabaseResponse;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final static String BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/";
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;

    private static Retrofit getClient() {
        if(okHttpClient==null)
            buildOkHttpClient();

        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return retrofit;
    }

    private static void buildOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        okHttpClient = builder.build();
    }

    private static DatabaseApi databaseApi = getClient().create(DatabaseApi.class);

    public static Call<DatabaseResponse> getTeam(String query) {
        return databaseApi.getTeam(query);
    }

    public static Call<DatabaseResponse> getEvents(String teamId) {
        return databaseApi.getEvents(teamId);
    }

    public static Call<DatabaseResponse> getResults(String teamId) {
        return databaseApi.getResults(teamId);
    }

    public static Call<DatabaseResponse> getTeamById(String teamId) {
        return databaseApi.getTeamById(teamId);
    }
}
