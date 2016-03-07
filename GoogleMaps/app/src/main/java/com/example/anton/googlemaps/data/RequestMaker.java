package com.example.anton.googlemaps.data;

import com.example.anton.googlemaps.domain.events.GetRouteResponse;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;

/**
 * Created by Anton on 07.03.2016.
 */
public class RequestMaker {
    public void getRouteResponse(LatLng origin, LatLng destination) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://maps.googleapis.com")
                .build();
        RouteApi routeService = restAdapter.create(RouteApi.class);
        Map<String, String> params = new HashMap<String, String>();
        params.put("origin", String.valueOf(origin.latitude) + "," + String.valueOf(origin.longitude));
        params.put("destination", String.valueOf(destination.latitude) + "," + String.valueOf(destination.longitude));
        params.put("sensor", "false");
        params.put("language", "ru");
        RouteResponse routeResponse = routeService.getRoute(params);
        EventBus.getDefault().postSticky(new GetRouteResponse(routeResponse));
    }
}
