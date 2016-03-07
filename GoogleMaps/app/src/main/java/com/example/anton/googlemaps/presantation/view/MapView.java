package com.example.anton.googlemaps.presantation.view;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Anton on 07.03.2016.
 */
public interface MapView {
    void direction(List<LatLng> points);
}
