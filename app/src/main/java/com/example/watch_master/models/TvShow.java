package com.example.watch_master.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TvShow {
    public boolean isAdded;
    private int id;
    private String original_name;
    private String original_language;
    private String overview;
    private String poster_path;
    private String episodeName;
    private String episodeNumber;
    private String first_air_date;
    private String duration;
    private String vote_average;
    private ArrayList<String> genre_ids = null;
    private HashMap<String, Episode> episodes = new HashMap<>();


    public TvShow() {
    }

    public int getId() {
        return id;
    }

    public TvShow setId(int id) {
        this.id = id;
        return this;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public TvShow setOriginal_name(String original_name) {
        this.original_name = original_name;
        return this;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public TvShow setOriginal_language(String original_language) {
        this.original_language = original_language;
        return this;
    }

    public String getOverview() {
        return overview;
    }

    public TvShow setOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public TvShow setPoster_path(String poster_path) {
        this.poster_path = poster_path;
        return this;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public TvShow setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
        return this;
    }

    public String getEpisodeNumber() {
        return episodeNumber;
    }

    public TvShow setEpisodeNumber(String episodeNumber) {
        this.episodeNumber = episodeNumber;
        return this;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public TvShow setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
        return this;
    }

    public String getDuration() {
        return duration;
    }

    public TvShow setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public String getVote_average() {
        return vote_average;
    }

    public TvShow setVote_average(String vote_average) {
        this.vote_average = vote_average;
        return this;
    }

    public ArrayList<String> getGenre_ids() {
        return genre_ids;
    }

    public TvShow setGenre_ids(ArrayList<String> genre_ids) {
        this.genre_ids = genre_ids;
        return this;
    }

    public HashMap<String, Episode> getEpisodes() {
        return episodes;
    }

    public TvShow setEpisodes(HashMap<String, Episode> episodes) {
        this.episodes = episodes;
        return this;
    }
}



