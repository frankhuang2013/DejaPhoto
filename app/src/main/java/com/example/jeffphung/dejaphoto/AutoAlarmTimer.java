package com.example.jeffphung.dejaphoto;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by kaijiecai on 5/5/17.
 */

public class AutoAlarmTimer extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
