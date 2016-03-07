package com.example.anton.googlemaps.presantation.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.anton.googlemaps.R;
import com.example.anton.googlemaps.domain.adapters.AutoCompleteAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Anton on 07.03.2016.
 */
public class FromFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;
    private AutoCompleteAdapter adapter;

    private static final LatLngBounds BOUNDS_GREATER_MOSCOW = new LatLngBounds(
            new LatLng(54, 37), new LatLng(56, 38));

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_from, null);
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), 0, this)
                .addApi(Places.GEO_DATA_API)
                .build();
        adapter = new AutoCompleteAdapter(getContext(), googleApiClient, BOUNDS_GREATER_MOSCOW, null);

        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.auto_complete_tv_from);
        autoCompleteTextView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(getContext(), "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }
}
