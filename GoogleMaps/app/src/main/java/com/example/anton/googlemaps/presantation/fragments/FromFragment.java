package com.example.anton.googlemaps.presantation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.example.anton.googlemaps.R;
import com.example.anton.googlemaps.domain.adapters.AutoCompleteAdapter;
import com.example.anton.googlemaps.presantation.presenters.FromFragmentPresenter;
import com.example.anton.googlemaps.presantation.presenters.FromFragmentPresenterImpl;
import com.example.anton.googlemaps.presantation.view.FromView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Anton on 07.03.2016.
 */
public class FromFragment extends Fragment implements FromView {
    private AutoCompleteAdapter adapter;
    private AutoCompleteTextView autoCompleteTextView;
    private GoogleMap googleMap;
    private FromFragmentPresenter fromFragmentPresenter;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_from, null);
        adapter = new AutoCompleteAdapter(getContext(), 0, null);
        autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.auto_complete_tv_from);
        autoCompleteTextView.setAdapter(adapter);
        fromFragmentPresenter = new FromFragmentPresenterImpl(this);
        createMapView();
        return view;
    }

    private void createMapView() {
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        final FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.my_container_from, mapFragment);
        fragmentTransaction.commit();
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                FromFragment.this.googleMap = googleMap;
                fromFragmentPresenter.moveToFirstPosition();
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
