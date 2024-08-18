package com.example.watch_master.interfaces;

import com.example.watch_master.models.Episode;

import java.util.HashMap;

public interface EpisodeFetchCallback {
    void onEpisodesFetched(HashMap<String, Episode> episodes);
}
