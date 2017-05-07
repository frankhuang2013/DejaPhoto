package com.example.jeffphung.dejaphoto;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by kaijiecai on 5/5/17.
 */

public class AutoAlarmTimer extends Service {


    Location location;
    LocationManager locationManager;
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private long INTERVAL_HOURS = AlarmManager.INTERVAL_HOUR;
    public AutoAlarmTimer(){

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){


        Log.i("TIMER", "START TIMER");



        Toast.makeText(this, "running timer in the background", Toast.LENGTH_SHORT).show();
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent mIntent = new Intent(this, AlarmReceiver.class);
        //Intent mIntent = new Intent("android.intent.action.BOOT_COMPLETED");
        alarmIntent = PendingIntent.getBroadcast(this, 0, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Log.i("TIMER", "Set TIMER");
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                INTERVAL_HOURS, alarmIntent);
        Log.i("TIMER", "END set TIMER");

        return START_STICKY;
    }
    public Location getCurrentLocation() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        if(locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            
        }
        return location;
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



}
