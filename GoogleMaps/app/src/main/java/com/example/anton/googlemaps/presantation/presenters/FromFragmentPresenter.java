package com.example.anton.googlemaps.presantation.presenters;

import com.example.anton.googlemaps.domain.adapters.AutoCompleteAdapter;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Anton on 07.03.2016.
 */
public interface FromFragmentPresenter {
    void moveToFirstPosition();

    void setFromCoordinates(int position, AutoCompleteAdapter adapter);
}
