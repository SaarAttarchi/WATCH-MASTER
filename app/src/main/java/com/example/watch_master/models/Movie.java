package com.example.watch_master.models;

import java.util.ArrayList;

public class Movie {
    public boolean isAdded;
    private int id;
    private String title;
    private String overview;
    private String release_date;
    private String runtime;
    private float vote_average = 0.0f;
    private ArrayList<String> genre_ids = null;
    private String poster_path;


    public Movie(){

    }

    public int getId() {
        return id;
    }

    public Movie setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getOverview() {
        return overview;
    }

    public Movie setOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public String getRelease_date() {
        return release_date;
    }

    public Movie setRelease_date(String release_date) {
        this.release_date = release_date;
        return this;
    }

    public String getRuntime() {
        return runtime;
    }

    public Movie setRuntime(String runtime) {
        this.runtime = runtime;
        return this;
    }

    public float getVote_average() {
        return vote_average;
    }

    public Movie setVote_average(float vote_average) {
        this.vote_average = vote_average;
        return this;
    }

    public ArrayList<String> getGenre_ids() {
        return genre_ids;
    }

    public Movie setGenre_ids(ArrayList<String> genre_ids) {
        this.genre_ids = genre_ids;
        return this;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public Movie setPoster_path(String poster_path) {
        this.poster_path = poster_path;
        return this;
    }
}
