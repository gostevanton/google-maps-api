package com.example.anton.googlemaps.presantation.view;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Anton on 07.03.2016.
 */
public interface FromView {
    void moveToPosition(LatLng latLng, int zoom);
}
