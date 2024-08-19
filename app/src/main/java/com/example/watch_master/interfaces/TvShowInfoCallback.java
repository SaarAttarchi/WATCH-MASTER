package com.example.watch_master.interfaces;

import com.example.watch_master.models.Episode;
import com.example.watch_master.models.TvShow;

import java.util.HashMap;

public interface TvShowInfoCallback {
    void onInfoFetched(TvShow tvShow);
}
