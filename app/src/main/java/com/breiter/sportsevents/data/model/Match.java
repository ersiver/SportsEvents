package com.breiter.sportsevents.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Match implements Comparable<Match> {

    @SerializedName("strLeague")
    private String league;

    @SerializedName("dateEvent")
    private String date;

    @SerializedName("strTime")
    private String time;

    @SerializedName("strHomeTeam")
    private String homeTeam;

    @SerializedName("strAwayTeam")
    private String guestTeam;

    @SerializedName("intHomeScore")
    private String homeScore;

    @SerializedName("intAwayScore")
    private String gueastScore;

    @SerializedName("idHomeTeam")
    private String idHomeTeam;

    @SerializedName("idAwayTeam")
    private String idGuestTeam;

    private String homeBadge;

    private String guestBadge;


    //Getters & Setters
    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getGuestTeam() {
        return guestTeam;
    }

    public void setGuestTeam(String guestTeam) {
        this.guestTeam = guestTeam;
    }

    public String getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(String homeScore) {
        this.homeScore = homeScore;
    }

    public String getGueastScore() {
        return gueastScore;
    }

    public void setGueastScore(String gueastScore) {
        this.gueastScore = gueastScore;
    }

    public String getIdHomeTeam() {
        return idHomeTeam;
    }

    public void setIdHomeTeam(String idHomeTeam) {
        this.idHomeTeam = idHomeTeam;
    }

    public String getIdGuestTeam() {
        return idGuestTeam;
    }

    public void setIdGuestTeam(String idGuestTeam) {
        this.idGuestTeam = idGuestTeam;
    }

    public String getHomeBadge() {
        return homeBadge;
    }

    public void setHomeBadge(String homeBadge) {
        this.homeBadge = homeBadge;
    }

    public String getGuestBadge() {
        return guestBadge;
    }

    public void setGuestBadge(String guestBadge) {
        this.guestBadge = guestBadge;
    }

    public String getMatchTime() {
        if (getTime().length() > 5)
            return getTime().substring(0, 5);
        else
            return getTime();
    }

    @Override
    public int compareTo(@NonNull Match o) {
        return this.date.compareTo(o.date);
    }
}
