package com.example.watch_master.DataManager;

import com.example.watch_master.models.Episode;
import com.example.watch_master.models.Movie;
import com.example.watch_master.models.TvShow;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EpisodeDiscoverResponse {
    @SerializedName("episodes")
    private List<Episode> episodes;

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }
}
