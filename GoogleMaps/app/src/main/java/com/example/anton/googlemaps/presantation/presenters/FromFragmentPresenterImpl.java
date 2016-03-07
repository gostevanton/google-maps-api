package com.example.anton.googlemaps.presantation.presenters;

import com.example.anton.googlemaps.presantation.view.FromView;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Anton on 07.03.2016.
 */
public class FromFragmentPresenterImpl implements FromFragmentPresenter {
    private FromView fromView;

    public FromFragmentPresenterImpl(FromView fromView) {
        this.fromView = fromView;
    }

    @Override
    public void moveToFirstPosition() {
        fromView.moveToPosition(new LatLng(55.75222, 37.61556), 10);
    }
}
