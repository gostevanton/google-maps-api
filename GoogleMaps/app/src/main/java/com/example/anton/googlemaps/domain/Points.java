package com.example.anton.googlemaps.domain;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Anton on 07.03.2016.
 */
public class Points {
    private static Points getInstance;
    private LatLng fromCoordinates;
    private LatLng toCoordinates;

    private Points() {
    }

    public static Points getInstance() {
        if (getInstance == null) {
            getInstance = new Points();
        }
        return getInstance;
    }

    public LatLng getFromCoordinates() {
        return fromCoordinates;
    }

    public void setFromCoordinates(LatLng fromCoordinates) {
        this.fromCoordinates = fromCoordinates;
    }

    public LatLng getToCoordinates() {
        return toCoordinates;
    }

    public void setToCoordinates(LatLng toCoordinates) {
        this.toCoordinates = toCoordinates;
    }
}
