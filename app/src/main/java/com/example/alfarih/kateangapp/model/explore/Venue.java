package com.example.alfarih.kateangapp.model.explore;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.DrawableRes;

import com.example.alfarih.kateangapp.DetailMainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alfarih on 14/11/17.
 */

public class Venue {

    private String id;
    private String name;
    private Boolean verified;
    private String url;
    private Double rating;
    private Location location;
    private List<Categories> categories = new ArrayList<Categories>();
    public static String ID = "Id", NAME = "Name", STREET = "Street", DISTANCE = "Distance", ADDRESS = "Address", RATING = "Rating", TYPE = "Type";

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public static Intent starter(Context context, String id, String name, double rating, String address, String distance, String type){
        Intent detailIntent = new Intent(context, DetailMainActivity.class);
        detailIntent.putExtra(ID, id);
        detailIntent.putExtra(NAME, name);
        detailIntent.putExtra(RATING, rating);
        detailIntent.putExtra(ADDRESS, address);
        detailIntent.putExtra(DISTANCE, distance);
        detailIntent.putExtra(TYPE, type);

        return detailIntent;
    }
}
