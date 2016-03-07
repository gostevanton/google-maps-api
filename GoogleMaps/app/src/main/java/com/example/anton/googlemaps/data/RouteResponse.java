package com.example.anton.googlemaps.data;

import java.util.List;

/**
 * Created by Anton on 07.03.2016.
 */
public class RouteResponse {
    public List<Route> routes;

    public String getPoints() {
        if (this.routes.size() ==0) return null;
        return this.routes.get(0).overview_polyline.points;
    }

    class Route {
        OverviewPolyline overview_polyline;
    }

    class OverviewPolyline {
        String points;
    }
}
