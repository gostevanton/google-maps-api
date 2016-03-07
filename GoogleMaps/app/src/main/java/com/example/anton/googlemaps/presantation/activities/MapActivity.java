package com.example.anton.googlemaps.presantation.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.anton.googlemaps.R;
import com.example.anton.googlemaps.presantation.presenters.MapActivityPresenter;
import com.example.anton.googlemaps.presantation.presenters.MapActivityPresenterImpl;
import com.example.anton.googlemaps.presantation.view.MapView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by Anton on 07.03.2016.
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, MapView {
    private MapActivityPresenter mapActivityPresenter;

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
        mapActivityPresenter.searchDirection();
    }
}
