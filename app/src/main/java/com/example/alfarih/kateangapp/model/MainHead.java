package com.example.alfarih.kateangapp.model;

import android.location.Location;
import android.location.LocationManager;

/**
 * Created by alfarih on 16/11/17.
 */

public class MainHead {

    String kota, jalan;

    public MainHead(String kota, String jalan) {
        this.kota = kota;
        this.jalan = jalan;
    }

    public String getKota() {
        return kota;
    }

    public String getJalan() {
        return jalan;
    }
}
