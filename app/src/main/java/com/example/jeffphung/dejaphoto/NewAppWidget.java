package com.example.jeffphung.dejaphoto;

//import android.app.PendingIntent;

import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.IOException;

/**
 * Implementation of App Widget functionality.
 */

public class NewAppWidget extends AppWidgetProvider {
    private static  String rightButtonClicked = "rightButtonClicked";
    private static  String leftButtonClicked = "leftButtonClicked";
    private static  String karmaButtonClicked = "karmaButtonClicked";
    private static  String releaseButtonClicked = "releaseButtonClicked";


    static boolean noPhotos = false;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        // Intent for Right Button
        Intent leftButtonIntent = new Intent(context, NewAppWidget.class);
        leftButtonIntent.setAction(leftButtonClicked);
        leftButtonIntent.putExtra("appWidgetId", appWidgetId);
        PendingIntent leftButtonPendingIntent = PendingIntent.getBroadcast(context, appWidgetId, leftButtonIntent, 0);

        // Intent for Left Button
        Intent rightButtonIntent = new Intent(context, NewAppWidget.class);
        rightButtonIntent.setAction(rightButtonClicked);
        rightButtonIntent.putExtra("appWidgetId", appWidgetId);
        PendingIntent rightButtonPendingIntent = PendingIntent.getBroadcast(context, appWidgetId, rightButtonIntent, 0);

        // Intent for Karma Button
        Intent karmaButtonIntent = new Intent(context, NewAppWidget.class);
        karmaButtonIntent.setAction(karmaButtonClicked);
        karmaButtonIntent.putExtra("appWidgetId", appWidgetId);
        PendingIntent karmaButtonPendingIntent = PendingIntent.getBroadcast(context, appWidgetId, karmaButtonIntent, 0);

        // Intent for Release Button
        Intent releaseButtonIntent = new Intent(context, NewAppWidget.class);
        releaseButtonIntent.setAction(releaseButtonClicked);
        releaseButtonIntent.putExtra("appWidgetId", appWidgetId);
        PendingIntent releaseButtonPendingIntent = PendingIntent.getBroadcast(context, appWidgetId, releaseButtonIntent, 0);

        views.setOnClickPendingIntent(R.id.buttonRight, rightButtonPendingIntent);
        views.setOnClickPendingIntent(R.id.buttonLeft, leftButtonPendingIntent);
        views.setOnClickPendingIntent(R.id.buttonKarma, karmaButtonPendingIntent);
        views.setOnClickPendingIntent(R.id.buttonRelease, releaseButtonPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //if there are no photos, then nothing can be done from the widget
        MyWallPaperManager myWallPaperManager = new MyWallPaperManager(context);
        int appWidgetId;
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetId = intent.getIntExtra("appWidgetId", -1);

        super.onReceive(context, intent);
        Photo photo = null;
        if (intent.getAction().equals(rightButtonClicked)) {

            Log.i("start get img","start");
            photo = PhotoList.getPhotoListInstance().next();
            if(photo!=null) {
                Log.i("finish get img", "finished");
                myWallPaperManager.setWallPaper(photo);
                if (photo.getKarma()) {
                    views.setImageViewResource(R.id.buttonKarma, R.drawable.karma2);
                }
                else {
                    views.setImageViewResource(R.id.buttonKarma, R.drawable.karma);
                }
            }
            //case in which there are no photos in list



        }
        else if (intent.getAction().equals(leftButtonClicked)) {

            photo = PhotoList.getPhotoListInstance().previous();
            if(photo != null) {
                Log.i("start get img", "start");
                Log.i("finish get img", "finished");
                myWallPaperManager.setWallPaper(photo);
                // put behavior here:
                //case in which there are no photos in list
                if (photo.getKarma()) {
                    views.setImageViewResource(R.id.buttonKarma, R.drawable.karma2);
                }
                else {
                    views.setImageViewResource(R.id.buttonKarma, R.drawable.karma);
                }
            }


        }
        else if (intent.getAction().equals(karmaButtonClicked))
        {
            photo = PhotoList.getPhotoListInstance().getCurrentPhoto();
            if (!photo.getKarma()) {
                photo.setKarma(true);
            }


            views.setImageViewResource(R.id.buttonKarma, R.drawable.karma2);

        }
        else if (intent.getAction().equals(releaseButtonClicked))
        {
            // switch to the next photo
            photo = PhotoList.getPhotoListInstance().removeCurrentPhoto();
            if(photo!=null) {
                Log.i("finish get img", "finished");
                myWallPaperManager.setWallPaper(photo);
                views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
                if (photo.getKarma()) {
                    views.setImageViewResource(R.id.buttonKarma, R.drawable.karma2);
                }
                else {
                    views.setImageViewResource(R.id.buttonKarma, R.drawable.karma);
                }
            }

        }
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }



}

