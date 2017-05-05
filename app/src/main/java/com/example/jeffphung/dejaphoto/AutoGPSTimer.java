package com.example.jeffphung.dejaphoto;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by kaijiecai on 5/4/17.
 */

public class AutoGPSTimer extends Service implements LocationListener {

    Location location;
    // Declaring a Location Manager
    protected LocationManager locationManager;
    private Context mContext;
    final long TIMER = 1; //60mins
    final float LOCATIONCHANGE = 152.4f; // 500 ft/152.4 meters


    public AutoGPSTimer(){

    }
    public AutoGPSTimer(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate(){
        Toast.makeText(mContext, "running gps in the background", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy(){
        Toast.makeText(mContext, "background gps stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return START_NOT_STICKY;
        }
        else if(locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    TIMER, LOCATIONCHANGE, this);
            if(locationManager!= null){
                location= locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if(location != null) {
                    callPhotoSorter();
                }
            }
        }

        else if(locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    TIMER, LOCATIONCHANGE, this);
            if(locationManager!= null){
                location= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location != null) {
                    callPhotoSorter();
                }
            }
        }


        return START_STICKY;

    }

    private void callPhotoSorter() {
        PhotoSorterTask photoSorterTask = new PhotoSorterTask(location);
        photoSorterTask.execute();

    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
