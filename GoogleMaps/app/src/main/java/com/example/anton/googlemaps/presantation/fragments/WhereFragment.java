package com.example.anton.googlemaps.presantation.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.anton.googlemaps.R;
import com.example.anton.googlemaps.domain.adapters.AutoCompleteAdapter;
import com.example.anton.googlemaps.presantation.presenters.WhereFragmentPresenter;
import com.example.anton.googlemaps.presantation.presenters.WhereFragmentPresenterImpl;
import com.example.anton.googlemaps.presantation.view.WhereView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by Anton on 07.03.2016.
 */
public class WhereFragment extends Fragment implements WhereView{
    private AutoCompleteAdapter adapter;
    private AutoCompleteTextView autoCompleteTextView;
    private GoogleMap googleMap;
    private WhereFragmentPresenter whereFragmentPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_where, null);
        adapter = new AutoCompleteAdapter(getContext(), 1, null);
        autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.auto_complete_tv_where);
        autoCompleteTextView.setAdapter(adapter);
        whereFragmentPresenter = new WhereFragmentPresenterImpl(this);
        createMapView();
        return view;
    }

    private void createMapView() {
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.my_container_where, mapFragment);
        fragmentTransaction.commit();
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                WhereFragment.this.googleMap = googleMap;
                whereFragmentPresenter.moveToFirstPosition();
            }
        });
    }

    @Override
    public void moveToPosition(LatLng latLng, int zoom) {
        if (googleMap != null) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(zoom)
                    .tilt(20)
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.animateCamera(cameraUpdate);
        }
    }
}
