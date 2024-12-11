package com.graphhopper.sfo.rgeocode;

import com.graphhopper.util.Helper;

public class SnapRequest {
    private double lat;
    private double lon;

    public SnapRequest() {
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = Helper.round(lat, 7);
    }

    public double getLon() {
        return this.lon;
    }

    public void setLon(double lon) {
        this.lon = Helper.round(lon, 7);
    }
}
