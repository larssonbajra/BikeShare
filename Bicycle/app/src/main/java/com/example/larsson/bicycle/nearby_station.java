package com.example.larsson.bicycle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.Manifest.*;

public class nearby_station extends FragmentActivity implements OnMapReadyCallback {
    Session session;
    private GoogleMap mMap;
    static String IP = "https://paperback-chimpanzee-1561.dataplicity.io/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_station);
        session = new Session(this);
        if (!session.loggedin()) {
            logout();
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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


        // Add a marker in Sydney and move the camera

        LatLng stat1 = new LatLng(35.245296, 128.692131);
        LatLng stat2 = new LatLng(35.241546, 128.689575);
        LatLng stat3 = new LatLng(35.244225, 128.683751);
        LatLng stat4 = new LatLng(35.248816, 128.675940);
        mMap.addMarker(new MarkerOptions().position(stat1).title("Station 1"));
        mMap.addMarker(new MarkerOptions().position(stat2).title("Station 2"));
        mMap.addMarker(new MarkerOptions().position(stat3).title("Station 3"));
        mMap.addMarker(new MarkerOptions().position(stat4).title("Station 4"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stat1, 13));


    }


    private void logout(){


        session.setLoggedin(false);
        finish();
        startActivity(new Intent(nearby_station.this,MainActivity.class));
    }
    public void onClickLogout(View view)
    {
        URLConnector urlConnector2 = new URLConnector(IP + "bicycle/app/app_logout.php?user="+ session.getUser());
        //from here
        urlConnector2.start();
        session.setLoggedin(false);
        finish();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
