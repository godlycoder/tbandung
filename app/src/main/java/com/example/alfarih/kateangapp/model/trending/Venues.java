package com.example.alfarih.kateangapp.model.trending;

/**
 * Created by alfarih on 18/11/17.
 */

public class Venues {
    String id, name;
    Location location;

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
