package com.example.watch_master.DataManager;

import com.example.watch_master.models.Movie;

import java.util.List;

public class MovieDiscoverResponse {
    private List<Movie> results;

    public  List<Movie> getResults() {
        return results;
    }

    public void setResults( List<Movie> movie) {
        this.results = results;
    }
}

