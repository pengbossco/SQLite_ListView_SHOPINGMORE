package com.example.bosscopeng.shopingmore;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.content.Intent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class shop_locationMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder geocoder;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_location_maps);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String SHOP_NAME=bundle.getString("shop_name");
        String SHOP_ADDRESS=bundle.getString("shop_address");
        String SHOP_PHONE=bundle.getString("shop_phone");
        final Float ADDRESS_X=bundle.getFloat("SHOP_ADDRESS_X");
        final Float ADDRESS_Y=bundle.getFloat("SHOP_ADDRESS_Y");
/*
        mMap.clear();
        Geocoder geocoder=new Geocoder(this, Locale.CHINESE);
        List<Address> addressList=null;
        int maxResult=1;
        try{
            addressList=geocoder.getFromLocationName(SHOP_ADDRESS,maxResult);

        }catch (IOException e){
            Log.e("GeocoderActivity",e.toString());
        }
        if(addressList==null||addressList.isEmpty()){
            Toast.makeText(getApplicationContext(),"商店地址錯誤",Toast.LENGTH_SHORT).show();
        }else {
            Address address=addressList.get(0);
            LatLng position=new LatLng(address.getLatitude(),address.getLongitude());
            String address_shop_l=address.getAddressLine(0);
            mMap.addMarker(new MarkerOptions().position(position).title(SHOP_NAME));

            CameraPosition cameraPosition=new CameraPosition.Builder().target(position).zoom(15).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
/*
        Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses= SHOP_ADDRESS;
        List<Address>addressLocation = geoCoder.getFromLocationName(SHOP_ADDRESS, 1);
        double latitude = addressLocation.get(0).getLatitude();
        double longitude = addressLocation.get(0).getLongitude();
*/
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if(ActivityCompat.checkSelfPermission(shop_locationMapsActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(shop_locationMapsActivity.this,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                MarkerOptions m1=new MarkerOptions();
                m1.position(new LatLng(ADDRESS_X,ADDRESS_Y));
                m1.title(SHOP_NAME);
                m1.draggable(true);
                googleMap.addMarker(m1);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ADDRESS_X,ADDRESS_Y),11));






            }
        });
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
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
