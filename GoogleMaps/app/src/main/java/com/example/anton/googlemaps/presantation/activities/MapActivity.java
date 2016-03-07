package com.example.anton.googlemaps.presantation.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapActivityPresenter = new MapActivityPresenterImpl(this);
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
    }

    @Override
    public void direction(List<LatLng> points) {
        PolylineOptions line = new PolylineOptions();
        line.width(4f).color(Color.BLACK);
        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
        for (int i = 0; i < points.size(); i++) {
            if (i == 0) {
                MarkerOptions startMarkerOptions = new MarkerOptions()
                        .position(points.get(i))
                        .title("Отсюда");
                googleMap.addMarker(startMarkerOptions);
            } else if (i == points.size() - 1) {
                MarkerOptions endMarkerOptions = new MarkerOptions()
                        .position(points.get(i))
                        .title("Сюда");
                googleMap.addMarker(endMarkerOptions);
            }
            line.add(points.get(i));
            latLngBuilder.include(points.get(i));
        }
        googleMap.addPolyline(line);
        int size = getResources().getDisplayMetrics().widthPixels;
        LatLngBounds latLngBounds = latLngBuilder.build();
        CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, size, size, 50);
        googleMap.moveCamera(track);
    }
}
