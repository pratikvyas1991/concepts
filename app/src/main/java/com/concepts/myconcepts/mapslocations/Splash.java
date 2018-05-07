package com.concepts.myconcepts.mapslocations;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.concepts.myconcepts.R;
import com.google.android.gms.maps.MapView;


/**
 * Created by tasol on 24/4/18.
 */

public class Splash extends Activity {

    private String[] permissionsRequired = new String[]{
            android.Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int LOCATION_PERMISSION_CALLBACK_CONSTANT = 100;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkForRequiredPermissions();

            if(checkRunTimePermissionsLocation()){
                //Checking Location Permission
                proceedAfterPermissionLocation();
            }else {
                askRuntimePermissionsLocations();
            }

        } else {
            proceedAfterPermissionLocation();
        }

        startMapInitTechnique();
    }

    public boolean checkRunTimePermissionsLocation(){
        boolean retVal = false;
        if (ActivityCompat.checkSelfPermission(Splash.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED) {
            retVal = false;
        }else {
            retVal = true;
        }
        return retVal;
    }

    private void proceedAfterPermissionLocation() {

        if(getGPSStatus()== 0){
//            Forcefully start location here , as dialogue will look weired
            Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(viewIntent);
        }
        Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent =new Intent(Splash.this, MapsACtivityHome.class);
                startActivity(intent);
                finish();

            }
        },5000);
    }

    public void askRuntimePermissionsLocations(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(Splash.this, permissionsRequired[0])) {
            //Show Information about why you need the permission
            AlertDialog.Builder builder = new AlertDialog.Builder(Splash.this);
            builder.setTitle("Need Permission");
            builder.setMessage("This app needs Location permission.");
            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ActivityCompat.requestPermissions(Splash.this, permissionsRequired, LOCATION_PERMISSION_CALLBACK_CONSTANT);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else {
            //just request the permission
            ActivityCompat.requestPermissions(Splash.this, permissionsRequired, LOCATION_PERMISSION_CALLBACK_CONSTANT);
        }
    }

    public int getGPSStatus() {
        LocationManager locationManager;
        boolean gpsStatus;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return gpsStatus ? 1 : 0;
    }

    private void startMapInitTechnique() {
        // Fixing Later Map loading Delay
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MapView mv = new MapView(getApplicationContext());
                    mv.onCreate(null);
                    mv.onPause();
                    mv.onDestroy();
                }catch (Exception ignored){

                }
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }
            if (allgranted) {
                proceedAfterPermissionLocation();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(Splash.this, permissionsRequired[0])) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Splash.this);
                builder.setTitle("Need Permission");
                builder.setMessage("This app needs Location permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(Splash.this, permissionsRequired, LOCATION_PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        }
    }
}
