package com.example.anton.googlemaps.presantation.presenters;

import android.content.Context;

import com.example.anton.googlemaps.domain.adapters.AutoCompleteAdapter;

/**
 * Created by Anton on 07.03.2016.
 */
public interface WhereFragmentPresenter {
    void moveToFirstPosition();

    void startMapActivity(Context context);

    void setWhereCoordinates(int position, AutoCompleteAdapter adapter);
}
