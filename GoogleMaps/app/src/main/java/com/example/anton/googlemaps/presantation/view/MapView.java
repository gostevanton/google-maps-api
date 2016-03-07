package com.example.anton.googlemaps.presantation.view;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

/**
 * Created by Anton on 07.03.2016.
 */
public interface MapView {
    void paintDirection(PolylineOptions line);

    void moveCamera(LatLngBounds latLngBounds);

    void addMarker(LatLng latLng, String title);
}
