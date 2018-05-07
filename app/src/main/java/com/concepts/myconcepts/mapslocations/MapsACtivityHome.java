package com.concepts.myconcepts.mapslocations;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.concepts.myconcepts.R;
import com.concepts.myconcepts.mapslocations.model.ClusterRenderer;
import com.concepts.myconcepts.mapslocations.model.Users;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MapsACtivityHome extends AppCompatActivity {

    private GoogleMap map;
    private SupportMapFragment mapFragment;

    private ClusterManager<Users> mClusterManagerPlaces;
    private List<Users> usersList = new ArrayList<>();

    Marker mCurrLocationMarker;

    private LocationRequest mLocationRequest;

    private long UPDATE_INTERVAL = 60000;  /* 60 secs */
    private long FASTEST_INTERVAL = 15000; /* 15 secs */
    private boolean isLoadedAtOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        populateUsers();
        setContentView(R.layout.maps_activity);


        loadMap();
    }

    private void populateUsers() {
        usersList.add(new Users("1", "Mr A", 23.0371571, 72.5100053));
        usersList.add(new Users("2", "Mr B", 23.038026, 72.5140929));
        usersList.add(new Users("3", "Mr C", 23.038026, 72.5140929));
    }

    private void loadMap() {
        mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    loadMap(map);
                }
            });
        } else {
            Toast.makeText(this, "Error - Map Fragment was null!!", Toast.LENGTH_SHORT).show();
        }
    }

    protected void loadMap(GoogleMap googleMap) {
        map = googleMap;
        if (map != null) {
            // Map is ready
            setUpClusterer();

            getMyLocation();

            startLocationUpdates();
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    private void setUpClusterer() {
        // Position the map.
        mClusterManagerPlaces = new ClusterManager<Users>(this, map);
        mClusterManagerPlaces.setAnimation(true);


        ClusterRenderer clusterRenderer = new ClusterRenderer(MapsACtivityHome.this, map, mClusterManagerPlaces, true, usersList);


        mClusterManagerPlaces.setRenderer(clusterRenderer);
        map.setOnCameraIdleListener(mClusterManagerPlaces);
        map.setOnMarkerClickListener(mClusterManagerPlaces);
        map.setOnInfoWindowClickListener(mClusterManagerPlaces);
//        map.getUiSettings().setMapToolbarEnabled(false);


        if (usersList != null && usersList.size() > 0) {
            mClusterManagerPlaces.addItems(usersList);
        }

        mClusterManagerPlaces.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<Users>() {
            @Override
            public boolean onClusterItemClick(Users users) {
                return false;
            }
        });

        mClusterManagerPlaces.setOnClusterItemInfoWindowClickListener(new ClusterManager.OnClusterItemInfoWindowClickListener<Users>() {
            @Override
            public void onClusterItemInfoWindowClick(Users users) {
                Toast.makeText(MapsACtivityHome.this, " You have clicked ", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void onLocationChanged(Location location) {

        // GPS may be turned off
        if (location == null) {
            return;
        }
        // Report to the UI that the location was updated

        if (location != null) {

            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
            }


            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);


            if (latLng != null) {
                if (!isLoadedAtOnce) {
                    CameraUpdate updateLatLong = CameraUpdateFactory.newLatLngZoom(latLng, 3f);
                    map.moveCamera(updateLatLong);
                    map.animateCamera(updateLatLong);
                    isLoadedAtOnce = true;
                }
            }
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));

            markerOptions.anchor(0.5f, 1);

//            if(loggedUserData!=null){
//                try {
//                    String name = loggedUserData.getSurveyorDisplayName();
//                    markerOptions.title(name);
//                }catch (Exception je){
//                    je.printStackTrace();
//                }
//
//            }else {
//                markerOptions.title("Current Position");
//            }

//            mCurrLocationMarker = map.addMarker(markerOptions);

        }
    }

    void getMyLocation() {
        //noinspection MissingPermission
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);

        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);
        //noinspection MissingPermission
        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("@@@", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);
        //noinspection MissingPermission
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }
}
