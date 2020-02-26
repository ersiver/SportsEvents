package com.breiter.sportsevents.data.model;

import java.util.List;

public class DatabaseResponse {
    private List<Team> teams;
    private List<Match> events;
    private List<Match> results;

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Match> getEvents() {
        return events;
    }

    public void setEvents(List<Match> events) {
        this.events = events;
    }

    public List<Match> getResults() {
        return results;
    }

    public void setResults(List<Match> results) {
        this.results = results;
    }
}
