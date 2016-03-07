package com.example.anton.googlemaps.presantation.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.anton.googlemaps.R;
import com.example.anton.googlemaps.presantation.presenters.MapActivityPresenter;
import com.example.anton.googlemaps.presantation.presenters.MapActivityPresenterImpl;
import com.example.anton.googlemaps.presantation.view.MapView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

/**
 * Created by Anton on 07.03.2016.
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, MapView {
    private MapActivityPresenter mapActivityPresenter;
    private GoogleMap googleMap;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapActivityPresenter = new MapActivityPresenterImpl(this);
        textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createMap();
    }

    private void createMap() {
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.my_container_map, mapFragment);
        fragmentTransaction.commit();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        mapActivityPresenter.searchDirection();
        mapActivityPresenter.getMyLocation(this);
    }

    @Override
    public void paintDirection(PolylineOptions line) {
        googleMap.addPolyline(line);
    }

    @Override
    public void moveCamera(LatLngBounds latLngBounds) {
        int size = getResources().getDisplayMetrics().widthPixels;
        CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, size, size, 50);
        googleMap.moveCamera(track);
    }

    @Override
    public void addMarker(LatLng latLng, String title) {
        if (googleMap != null) {
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(title));
        }
    }

    @Override
    public void setText(String string) {
        textView.setText(string);
    }
}
