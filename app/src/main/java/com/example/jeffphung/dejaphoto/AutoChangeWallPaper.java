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
 * Created by kaijiecai on 5/13/17.
 */

public class AutoChangeWallPaper extends Service {
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private long INTERVAL = 1000*60; //TODO set it 5 minutes
    private int id = 1;
    private String alarmName = "Auto change photo alarm";


    @Override
    public int onStartCommand(Intent intent, int flags, int startID){


        Log.i(alarmName, "start "+alarmName);
        //Toast.makeText(this, alarmName + "running in the background", Toast.LENGTH_SHORT).show();

        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        Intent mIntent = new Intent(this,AutoWallPaperReceiver.class);

        alarmIntent = PendingIntent.getBroadcast(this, id, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Log.i(alarmName, alarmName+ " set timer");

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime()+INTERVAL, alarmIntent);
        /*
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime()+INTERVAL,
                INTERVAL, alarmIntent);*/
        Log.i(alarmName, alarmName+" ends setting timer");

        return START_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
