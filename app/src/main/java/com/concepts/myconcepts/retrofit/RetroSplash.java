package com.concepts.myconcepts.retrofit;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.concepts.myconcepts.R;

/**
 * Created by tasol on 10/5/18.
 */

public class RetroSplash extends Activity {
    private String[] permissionsRequired = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int STORAGE_PERMISSION_CALLBACK_CONSTANT = 100;
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
    }

    public boolean checkRunTimePermissionsLocation(){
        boolean retVal = false;
        if (ActivityCompat.checkSelfPermission(RetroSplash.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED) {
            retVal = false;
        }else {
            retVal = true;
        }
        return retVal;
    }

    private void proceedAfterPermissionLocation() {
        Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent =new Intent(RetroSplash.this, RetrofitActivity.class);
                startActivity(intent);
                finish();

            }
        },5000);
    }

    public void askRuntimePermissionsLocations(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(RetroSplash.this, permissionsRequired[0])) {
            //Show Information about why you need the permission
            AlertDialog.Builder builder = new AlertDialog.Builder(RetroSplash.this);
            builder.setTitle("Need Permission");
            builder.setMessage("This app needs Location permission.");
            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ActivityCompat.requestPermissions(RetroSplash.this, permissionsRequired, STORAGE_PERMISSION_CALLBACK_CONSTANT);
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
            ActivityCompat.requestPermissions(RetroSplash.this, permissionsRequired, STORAGE_PERMISSION_CALLBACK_CONSTANT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CALLBACK_CONSTANT) {
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
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(RetroSplash.this, permissionsRequired[0])) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RetroSplash.this);
                builder.setTitle("Need Permission");
                builder.setMessage("This app needs Location permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(RetroSplash.this, permissionsRequired, STORAGE_PERMISSION_CALLBACK_CONSTANT);
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
