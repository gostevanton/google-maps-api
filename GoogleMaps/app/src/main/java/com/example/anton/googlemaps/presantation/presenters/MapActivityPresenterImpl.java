package com.example.anton.googlemaps.presantation.presenters;

import android.util.Log;

import com.example.anton.googlemaps.data.RequestMaker;
import com.example.anton.googlemaps.domain.Points;
import com.example.anton.googlemaps.domain.events.GetRouteResponse;
import com.example.anton.googlemaps.presantation.view.MapView;
import com.google.android.gms.maps.model.LatLng;
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

    public MapActivityPresenterImpl(MapView mapView) {
        this.mapView = mapView;
        asyncExecutor = AsyncExecutor.create();
        eventBus = EventBus.getDefault();
        eventBus.register(this);
    }

    @Override
    public void searchDirection() {
        asyncExecutor.execute(new AsyncExecutor.RunnableEx() {
            @Override
            public void run() throws Exception {
                new RequestMaker().getRouteResponse(Points.getInstance().getFromCoordinates(), new LatLng(57, 36));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetRouteResponse(GetRouteResponse response) {
        Log.d("My App", "onGetRouteResponce");
        if (response.routeResponse.getPoints() != null) {
            List<LatLng> points = PolyUtil.decode(response.routeResponse.getPoints());
            mapView.direction(points);
        } else {
            Log.d("My App", "Error");
        }
    }
}
