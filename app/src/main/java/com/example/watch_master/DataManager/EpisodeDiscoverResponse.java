package com.example.watch_master.DataManager;

import com.example.watch_master.models.Episode;
import com.example.watch_master.models.Movie;

import java.util.List;

public class EpisodeDiscoverResponse {
    private List<Episode> results;

    public  List<Episode> getResults() {
        return results;
    }

    public void setResults( List<Episode> episode) {
        this.results = results;
    }
}
