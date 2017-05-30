package com.mousebelly.app.userapp.orderFood;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mousebelly.app.userapp.APIs;
import com.mousebelly.app.userapp.GPSTracker;
import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.MainActivity;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


public class ChangeLocation extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    Marker Marker;
    Circle mCircle;
    LatLng currentLoc;
    GPSTracker gps;
    Button findMyLocation;
    ProgressBar pg;
    Geocoder geocoder;
    String address = "";
    List<Address> addresses;
    RelativeLayout mapLayout;

    public static HashMap<String,ArrayList<Double>> Hwflocations = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_location);

        geocoder = new Geocoder(this, Locale.getDefault());
        /*Dialog locationChange = new Dialog(MainActivity.context);

        locationChange.setTitle("Change Location");
        locationChange.setContentView(R.layout.change_location_dialog_box);*/

        findMyLocation = (Button) findViewById(R.id.find_my_location);
        pg = (ProgressBar)findViewById(R.id.pg);
        mapLayout = (RelativeLayout)findViewById(R.id.map);

        new LoadHWF().execute();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        Iterator it = Hwflocations.keySet().iterator();
        while (it.hasNext()){
            String key = it.next().toString();
            ArrayList<Double> location = Hwflocations.get(key);
            LatLng loc = new LatLng(location.get(0), location.get(1));
            String add ="";
            try {
                addresses = geocoder.getFromLocation(location.get(0), location.get(1),1);
                add = addresses.get(0).getAddressLine(1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mMap.addMarker(new MarkerOptions().position(loc).title(key).snippet(add).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        }


        findMyLocation.setVisibility(View.VISIBLE);
        // Add a Marker in Sydney and move the camera
        Double Lat = Double.parseDouble(LoginActivity.user.getLat());
        Double Long = Double.parseDouble(LoginActivity.user.getLongitude());
        currentLoc = new LatLng(Lat, Long);

        try {
            addresses = geocoder.getFromLocation(Lat, Long,1);
             address = addresses.get(0).getAddressLine(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCircle = mMap.addCircle(new CircleOptions().center(new LatLng(Lat, Long)).radius(10000).strokeColor(ContextCompat.getColor(ChangeLocation.this, R.color.Amulet)).fillColor((Color.parseColor("#4DD50000")))); //#4DD50000 //#1B5E20  // (Color.parseColor("#f24848")));
        Marker = mMap.addMarker(new MarkerOptions().position(currentLoc).title("Drag to change Location").draggable(true).snippet(address));
        Marker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 10.0f));

        findMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gps = new GPSTracker(MainActivity.context);

                if (gps.canGetLocation()) {
                    Double latitude = gps.getLatitude();
                    Double longitude = gps.getLongitude();

                    mCircle.remove();
                    Marker.remove();
                    currentLoc = new LatLng(latitude , longitude );

                    mCircle = mMap.addCircle(new CircleOptions().center(new LatLng(latitude, longitude )).radius(10000).strokeColor(ContextCompat.getColor(ChangeLocation.this, R.color.Amulet)).fillColor((Color.parseColor("#4DD50000")))); //#4DD50000 //#1B5E20  // (Color.parseColor("#f24848")));
                    Marker = mMap.addMarker(new MarkerOptions().position(currentLoc).title("Drag to change Location").draggable(true).snippet(address));
                    Marker.showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 10.0f));

                    LoginActivity.user.setLongitude(String.valueOf(longitude));
                    LoginActivity.user.setLat(String.valueOf(latitude));

                } else {
                    gps.showSettingsAlert();
                }
            }
        });


        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {
                mCircle.remove();
                currentLoc = marker.getPosition();
                mCircle = mMap.addCircle(new CircleOptions().center(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude)).radius(10000).strokeColor(ContextCompat.getColor(ChangeLocation.this, R.color.Roman)).fillColor((Color.parseColor("#4DD50000"))));

                // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 10.0f));
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng ll = marker.getPosition();

                LoginActivity.user.setLongitude(String.valueOf(ll.longitude));
                LoginActivity.user.setLat(String.valueOf(ll.latitude));



                Marker = mMap.addMarker(new MarkerOptions().position(currentLoc).title("MouseBelly").draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                marker.remove();

                Intent returnToMainActivity = new Intent(ChangeLocation.this, MainActivity.class);
                startActivity(returnToMainActivity);

            }
        });
    }


    public class LoadHWF extends AsyncTask<Void, Void, Void> {

        String resp;
        GetProductsData getProductsData = new GetProductsData();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //pg.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            resp = Server.s.get(APIs.housewifesign_housewifesign );
            getProductsData.convertDataToArrayList(resp);
            System.out.println(resp);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            pg.setVisibility(View.GONE);

            try {
                JSONArray jsonArray = new JSONArray(resp);

                for(int i = 0 ; i<jsonArray.length() ; i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String hwfname = jsonObject.getString("HWF_Name");

                    JSONArray latlongArray = jsonObject.getJSONArray("Cordinates");
                    JSONObject latlong = latlongArray.getJSONObject(0);

                    ArrayList<Double> latlongArrayList  = new ArrayList<>();
                    Double lat = Double.parseDouble(latlong.getString("lat"));
                    Double lon = Double.parseDouble(latlong.getString("long"));
                    latlongArrayList.add(lat);
                    latlongArrayList.add(lon);

                    Hwflocations.put(hwfname,latlongArrayList);


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            System.out.println("HWF Locatins : "  + Hwflocations);


            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.change_locaton_map);
            mapFragment.getMapAsync(ChangeLocation.this);

            mapLayout.setVisibility(View.VISIBLE);
            findMyLocation.setVisibility(View.VISIBLE);

          //  pg.setVisibility(View.GONE);


        }

    }
}