package com.example.watch_master.interfaces;

import com.example.watch_master.models.Movie;
import com.example.watch_master.models.TvShow;

import java.util.HashMap;

public interface DataFetchCallback {
    void onDataFetched(HashMap<String, TvShow> tvShows, HashMap<String, Movie> movies);


}
