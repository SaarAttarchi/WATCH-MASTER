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
    private int number_of_seasons;
    private int number_of_episodes;
    private int current_episode;
    private int current_season;
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

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public TvShow setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
        return this;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public TvShow setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
        return this;
    }

    public int getCurrent_episode() {
        return current_episode;
    }

    public TvShow setCurrent_episode(int current_episode) {
        this.current_episode = current_episode;
        return this;
    }

    public int getCurrent_season() {
        return current_season;
    }

    public TvShow setCurrent_season(int current_season) {
        this.current_season = current_season;
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



