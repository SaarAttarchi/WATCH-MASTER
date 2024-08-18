package com.example.watch_master.models;

public class Episode {
    private int id;
    private String name;
    private int episode_number;
    private int season_number;
    private String air_date;
    private String overview;
    private String runtime;
    private float vote_average;

    public Episode() {

    }

    public int getId() {
        return id;
    }

    public Episode setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Episode setName(String name) {
        this.name = name;
        return this;
    }

    public int getEpisode_number() {
        return episode_number;
    }

    public Episode setEpisode_number(int episode_number) {
        this.episode_number = episode_number;
        return this;
    }

    public int getSeason_number() {
        return season_number;
    }

    public Episode setSeason_number(int season_number) {
        this.season_number = season_number;
        return this;
    }

    public String getAir_date() {
        return air_date;
    }

    public Episode setAir_date(String air_date) {
        this.air_date = air_date;
        return this;
    }

    public String getOverview() {
        return overview;
    }

    public Episode setOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public String getRuntime() {
        return runtime;
    }

    public Episode setRuntime(String runtime) {
        this.runtime = runtime;
        return this;
    }

    public float getVote_average() {
        return vote_average;
    }

    public Episode setVote_average(float vote_average) {
        this.vote_average = vote_average;
        return this;
    }
}
