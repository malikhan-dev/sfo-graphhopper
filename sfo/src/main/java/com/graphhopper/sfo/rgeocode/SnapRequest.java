package com.graphhopper.sfo.rgeocode;

import static com.graphhopper.util.Helper.round;

public class SnapRequest {
    private double lat;
    private double lon;

    public SnapRequest() {
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = round(lat, 7);
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = round(lon, 7);
    }
}
