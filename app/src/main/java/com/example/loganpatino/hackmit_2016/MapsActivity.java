package com.example.loganpatino.hackmit_2016;

import android.*;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;

public class MapsActivity extends FragmentActivity implements OnMapLongClickListener, OnMapClickListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, Serializable {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private View mView;
    private LatLng mNewLocation;
    private Firebase mEventsRef;

    private final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private final String COLLEGE = "college";
    private final String UNIVERSITY = "university";
    private final String INSTITUTE = "institute";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mEventsRef = new Firebase("https://hackmit-2016-1742c.firebaseio.com/events");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .build();
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(10, 10))
                .title("Hello world"));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        // Add a marker at current location and move the camera
        LatLng currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        Log.d("Location Test", String.valueOf(mLastLocation.getLatitude()) + ", " + String.valueOf(mLastLocation.getLongitude()));

        getSchool();

        getMarkers();


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onMapLongClick(LatLng point) {
        Log.d("click test", "tapped, point=" + point);
        mNewLocation = point;
        DialogFrag prompt = new DialogFrag();
        Bundle bundle = new Bundle();
        bundle.putSerializable("test", this);
        prompt.setArguments(bundle);
        prompt.show(getFragmentManager(), null);

    private void getSchool() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(@NonNull PlaceLikelihoodBuffer likelyPlaces) {
                Place mostLikely = null;
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                    String placeName = String.valueOf(placeLikelihood.getPlace().getName()).toLowerCase();
                    if (placeName.contains(COLLEGE) || placeName.contains(UNIVERSITY) || placeName.contains(INSTITUTE)) {
                        mostLikely = placeLikelihood.getPlace();
                    }
                }

                if (mostLikely != null) {
                    Log.d("Place Test", String.format("Current university: %s", mostLikely.getName()));
                }
                likelyPlaces.release();
            }
        });
    }

    private void getMarkers() {
        Query query = mEventsRef;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    Event event = eventSnapshot.getValue(Event.class);
                    Log.d("Data pull test - lat", event.getLatitude());
                    Log.d("Data pull test - long", event.getLongitude());
                    Log.d("Data pull test - name", event.getName());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void startCreateMarker() {
        mMap.addMarker(new MarkerOptions()
                .position(mNewLocation)
                .title("New Marker"));
    }

    @Override
    public void onMapClick(LatLng latLng) {
    }
}
