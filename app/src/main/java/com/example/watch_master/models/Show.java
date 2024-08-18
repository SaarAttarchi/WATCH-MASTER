package com.example.watch_master.models;

import android.media.Image;

import com.google.gson.JsonObject;

import java.util.List;

public class Show {
    private int id; // Assuming you need an ID for the show
    private String name;
    private String language;
    private String summary;
    private List<String> genres;
    private String  imageUrl;
    private int runtime;


    public int getId() {
        return id;
    }

    public Show setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Show setName(String name) {
        this.name = name;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public Show setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public Show setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public List<String> getGenres() {
        return genres;
    }

    public Show setGenres(List<String> genres) {
        this.genres = genres;
        return this;
    }

    public String  getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getRuntime() {
        return runtime;
    }

    public Show setRuntime(int duration) {
        runtime = runtime;
        return this;
    }



/*

    public double getRating() {
        return rating;
    }

    public void setRating(JsonObject ratingObject) {
        if (ratingObject != null && ratingObject.has("average")) {
            this.rating = ratingObject.get("average").isJsonNull() ? null : ratingObject.get("average").getAsDouble();
        } else {
            this.rating = Double.parseDouble(null); // Handle cases where "average" is missing or null
        }
    }


 */

}

