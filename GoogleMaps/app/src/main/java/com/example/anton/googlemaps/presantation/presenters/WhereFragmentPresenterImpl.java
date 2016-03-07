package com.example.anton.googlemaps.presantation.presenters;

import android.content.Context;
import android.content.Intent;

import com.example.anton.googlemaps.domain.Points;
import com.example.anton.googlemaps.domain.adapters.AutoCompleteAdapter;
import com.example.anton.googlemaps.presantation.activities.MapActivity;
import com.example.anton.googlemaps.presantation.view.FromView;
import com.example.anton.googlemaps.presantation.view.WhereView;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
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

    @Override
    public void startMapActivity(Context context) {
        Intent intent = new Intent(context, MapActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setWhereCoordinates(int position, AutoCompleteAdapter adapter) {
        getPlaceResult(position, adapter);
    }

    void getPlaceResult(int position, AutoCompleteAdapter adapter) {
        AutocompletePrediction item = adapter.getItem(position);
        String placeId = item.getPlaceId();
        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(adapter.getGoogleApiClient(), placeId);
        placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
    }

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                places.release();
                return;
            }
            Place place = places.get(0);
            LatLng latLng = place.getLatLng();
            Points.getInstance().setToCoordinates(latLng);
            whereView.moveToPosition(latLng, 15);
            whereView.addMarker(latLng);
            places.release();
        }
    };
}
