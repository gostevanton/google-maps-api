package com.example.anton.googlemaps.domain.events;

import com.example.anton.googlemaps.data.RouteResponse;

/**
 * Created by Anton on 07.03.2016.
 */
public class GetRouteResponse {
    public final RouteResponse routeResponse;

    public GetRouteResponse(RouteResponse routeResponse) {
        this.routeResponse = routeResponse;
    }
}
