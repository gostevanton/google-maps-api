package com.example.anton.googlemaps.data;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by Anton on 07.03.2016.
 */
public interface RouteApi {
    @GET("/maps/api/directions/json?")
    RouteResponse getRoute(@QueryMap Map<String, String> map);
}
