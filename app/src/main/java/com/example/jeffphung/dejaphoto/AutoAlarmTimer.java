package com.example.jeffphung.dejaphoto;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by kaijiecai on 5/5/17.
 */

public class AutoAlarmTimer extends Service {
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private long INTERVAL_HOURS = AlarmManager.INTERVAL_HOUR;
    public AutoAlarmTimer(){

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        /*
        SharedPreferences sharedPreferences = getSharedPreferences("DejaVuMode",1);
        Boolean boo = sharedPreferences.getBoolean("Location",false);
        Log.i("Boolean value",boo+"");*/



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



    @Nullable
    @Override public IBinder onBind(Intent intent) {
        return null;
    }




}
