package com.example.watch_master.DataManager;

import com.example.watch_master.models.Movie;
import com.example.watch_master.models.TvShow;

import java.util.List;

public class TvShowDiscoverResponse {

        private List<TvShow> results;

        public  List<TvShow> getResults() {
            return results;
        }

        public void setResults( List<TvShow> tvShow) {
            this.results = results;
        }

}
