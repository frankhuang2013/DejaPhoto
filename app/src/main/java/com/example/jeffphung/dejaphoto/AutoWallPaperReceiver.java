package com.example.jeffphung.dejaphoto;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by kaijiecai on 5/13/17.
 */

public class AutoWallPaperReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        ComponentName receiver = new ComponentName(context, AutoWallPaperReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);


        Log.i("------------","------------");
        MyWallPaperManager myWallPaperManager = new MyWallPaperManager(context);
        myWallPaperManager.setWallPaper(PhotoList.getPhotoListInstance().next());
    }
}
