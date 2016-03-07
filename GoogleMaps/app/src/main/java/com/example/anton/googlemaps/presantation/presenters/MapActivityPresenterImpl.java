package com.example.anton.googlemaps.presantation.presenters;

import android.util.Log;

import com.example.anton.googlemaps.data.RequestMaker;
import com.example.anton.googlemaps.domain.events.GetRouteResponse;
import com.example.anton.googlemaps.presantation.view.MapView;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.util.AsyncExecutor;

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
                new RequestMaker().getRouteResponse(new LatLng(30, 46), new LatLng(31, 46));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetRouteResponse(GetRouteResponse response) {
        Log.d("My App", "onGetRouteResponce");
    }
}
