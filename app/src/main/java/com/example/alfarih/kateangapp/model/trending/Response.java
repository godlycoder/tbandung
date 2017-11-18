package com.example.alfarih.kateangapp.model.trending;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alfarih on 18/11/17.
 */

public class Response {
    List<Venues> venues = new ArrayList<Venues>();

    public List<Venues> getVenues() {
        return venues;
    }

    public void setVenues(List<Venues> venues) {
        this.venues = venues;
    }
}
