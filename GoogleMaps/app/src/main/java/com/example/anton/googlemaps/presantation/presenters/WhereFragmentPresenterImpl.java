package com.example.anton.googlemaps.presantation.presenters;

import com.example.anton.googlemaps.presantation.view.FromView;
import com.example.anton.googlemaps.presantation.view.WhereView;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Anton on 07.03.2016.
 */
public class WhereFragmentPresenterImpl implements WhereFragmentPresenter {
    private WhereView whereView;

    public WhereFragmentPresenterImpl(WhereView whereView) {
        this.whereView = whereView;
    }

    @Override
    public void moveToFirstPosition() {
        whereView.moveToPosition(new LatLng(55.75222, 37.61556), 10);
    }
}
