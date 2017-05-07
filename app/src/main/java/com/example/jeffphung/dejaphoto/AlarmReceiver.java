package com.example.jeffphung.dejaphoto;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

/**
 * Created by kaijiecai on 5/6/17.
 */


public class AlarmReceiver extends BroadcastReceiver {
    Location location;
    public AlarmReceiver(){

    }
    public AlarmReceiver(Location location){

        this.location = location;
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        ComponentName receiver = new ComponentName(context, AlarmReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        Log.i("AlarmReceiver1","Start sort list");
        PhotoSorterTask photoSorterTask = new PhotoSorterTask();
        photoSorterTask.execute();
    }
}
