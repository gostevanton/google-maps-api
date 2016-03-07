package com.example.anton.googlemaps.presantation.presenters;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.anton.googlemaps.data.RequestMaker;
import com.example.anton.googlemaps.domain.Points;
import com.example.anton.googlemaps.domain.events.GetRouteResponse;
import com.example.anton.googlemaps.presantation.view.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.util.AsyncExecutor;

import java.util.List;

/**
 * Created by Anton on 07.03.2016.
 */
public class MapActivityPresenterImpl implements MapActivityPresenter {
    private MapView mapView;
    private AsyncExecutor asyncExecutor;
    private EventBus eventBus;
    private LocationManager locationManager;
    LatLngBounds.Builder latLngBuilder;

    public MapActivityPresenterImpl(MapView mapView) {
        this.mapView = mapView;
        asyncExecutor = AsyncExecutor.create();
        eventBus = EventBus.getDefault();
        eventBus.register(this);
    }

    @Override
    public void searchDirection() {
        latLngBuilder = new LatLngBounds.Builder();
        asyncExecutor.execute(new AsyncExecutor.RunnableEx() {
            @Override
            public void run() throws Exception {
                new RequestMaker().getRouteResponse(Points.getInstance().getFromCoordinates(), Points.getInstance().getToCoordinates());
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetRouteResponse(GetRouteResponse response) {
        Log.d("My App", "onGetRouteResponce");
        if (response.routeResponse.getPoints() != null) {
            paintDirection(response);
            moveCamera();
            mapView.setText("Found");
        } else {
            mapView.setText("Unknown");
        }
    }

    @Override
    public void getMyLocation(Context context) {
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, listener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 10, listener);
    }

    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            latLngBuilder.include(latLng);
            mapView.addMarker(latLng, "Я здесь");
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    };

    private void paintDirection(GetRouteResponse response) {
        List<LatLng> points = PolyUtil.decode(response.routeResponse.getPoints());
        PolylineOptions line = new PolylineOptions();
        line.width(4f).color(Color.BLACK);
        for (int i = 0; i < points.size(); i++) {
            if (i == 0) {
                mapView.addMarker(points.get(i), "Отсюда");
            } else if (i == points.size() - 1) {
                mapView.addMarker(points.get(i), "Сюда");
            }
            line.add(points.get(i));
            latLngBuilder.include(points.get(i));
        }
        mapView.paintDirection(line);
    }

    private void moveCamera() {
        LatLngBounds latLngBounds = latLngBuilder.build();
        mapView.moveCamera(latLngBounds);
    }
}
