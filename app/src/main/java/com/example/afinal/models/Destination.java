package com.example.afinal.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Destination {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("country")
    private String country;

    @SerializedName("description")
    private String description;

    @SerializedName("image")
    private String image;

    @SerializedName("language")
    private String language;

    @SerializedName("best_time_to_visit")
    private String bestTimeToVisit;

    @SerializedName("top_attractions")
    private List<String> topAttractions;

    @SerializedName("is_favorite")
    private Boolean isFavorite;

    // Getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLanguage() { return language;
    }

    public void setLanguage(String language) { this.language = language;
    }

    public String getBestTimeToVisit() {
        return bestTimeToVisit;
    }

    public void setBestTimeToVisit(String bestTimeToVisit) {
        this.bestTimeToVisit = bestTimeToVisit;
    }

    public List<String> getTopAttractions() {
        return topAttractions;
    }

    public void setTopAttractions(List<String> topAttractions) {
        this.topAttractions = topAttractions;
    }

    public Boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

}
