package com.example.anton.googlemaps.presantation.presenters;

import com.example.anton.googlemaps.domain.Points;
import com.example.anton.googlemaps.domain.adapters.AutoCompleteAdapter;
import com.example.anton.googlemaps.presantation.view.FromView;
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
public class FromFragmentPresenterImpl implements FromFragmentPresenter {
    private FromView fromView;

    public FromFragmentPresenterImpl(FromView fromView) {
        this.fromView = fromView;
    }

    @Override
    public void moveToFirstPosition() {
        fromView.moveToPosition(new LatLng(55.75222, 37.61556), 10);
    }

    @Override
    public void setFromCoordinates(int position, AutoCompleteAdapter adapter) {
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
            Points.getInstance().setFromCoordinates(latLng);
            fromView.moveToPosition(latLng, 15);
            fromView.addMarker(latLng);
            places.release();
        }
    };
}
