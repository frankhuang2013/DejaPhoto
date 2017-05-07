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

    static boolean karmaGiven = false;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        // Intent for Right Button
        Intent leftButtonIntent = new Intent(context, NewAppWidget.class);
        leftButtonIntent.setAction(leftButtonClicked);
        leftButtonIntent.putExtra("appWidgetId", appWidgetId);
        PendingIntent leftButtonPendingIntent = PendingIntent.getBroadcast(context, appWidgetId, leftButtonIntent, 0);

        // Intent for Right Button
        Intent rightButtonIntent = new Intent(context, NewAppWidget.class);
        rightButtonIntent.setAction(rightButtonClicked);
        rightButtonIntent.putExtra("appWidgetId", appWidgetId);
        PendingIntent rightButtonPendingIntent = PendingIntent.getBroadcast(context, appWidgetId, rightButtonIntent, 0);

        // Intent for Karma Button
        Intent karmaButtonIntent = new Intent(context, NewAppWidget.class);
        karmaButtonIntent.setAction(karmaButtonClicked);
        karmaButtonIntent.putExtra("appWidgetId", appWidgetId);
        PendingIntent karmaButtonPendingIntent = PendingIntent.getBroadcast(context, appWidgetId, karmaButtonIntent, 0);

        views.setOnClickPendingIntent(R.id.buttonRight, rightButtonPendingIntent);
        views.setOnClickPendingIntent(R.id.buttonLeft, leftButtonPendingIntent);
        views.setOnClickPendingIntent(R.id.buttonKarma, karmaButtonPendingIntent);

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
        int appWidgetId;
        super.onReceive(context, intent);
        Photo photo = null;
        if (intent.getAction().equals(rightButtonClicked)) {

            Log.i("start get img","start");
            photo = PhotoList.getPhotoListInstance().next();
            if(photo!=null) {
                String path = photo.getImgPath();
                Log.i("finish get img", "finished");
                setWallPaper(context, path);
            }
            appWidgetId = intent.getIntExtra("appWidgetId", -1);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
            if (photo.getKarma()) {
                views.setImageViewResource(R.id.buttonKarma, R.drawable.karma2);
            }
            else {
                views.setImageViewResource(R.id.buttonKarma, R.drawable.karma);
            }
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        else if (intent.getAction().equals(leftButtonClicked)) {

            photo = PhotoList.getPhotoListInstance().previous();
            if(photo != null) {
                Log.i("start get img", "start");
                String path = photo.getImgPath();
                Log.i("finish get img", "finished");
                setWallPaper(context, path);
                // put behavior here:
            }
            appWidgetId = intent.getIntExtra("appWidgetId", -1);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
            if (photo.getKarma()) {
                views.setImageViewResource(R.id.buttonKarma, R.drawable.karma2);
            }
            else {
                views.setImageViewResource(R.id.buttonKarma, R.drawable.karma);
            }
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        else if (intent.getAction().equals(karmaButtonClicked))
        {
            Photo currentPhoto = PhotoList.getPhotoListInstance().getCurrentPhoto();
            if (!currentPhoto.getKarma()) {
                currentPhoto.setKarma(true);
            }

            appWidgetId = intent.getIntExtra("appWidgetId", -1);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
            views.setImageViewResource(R.id.buttonKarma, R.drawable.karma2);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }


    public void setWallPaper(Context mContext, String path){

        WallpaperManager myWallpaperManager = WallpaperManager.getInstance(mContext);

        // put behavior here:
        System.out.println("fghdgfhrsjkgfjhgjkrsjkghrjkgjkehfjkgaerhkjjhgjjjghvhjvhjvhjhjvjhgjhgkjgjhhfgh");
        try {

            Bitmap bitmap = null;

            if(path == null){
                Toast.makeText(mContext, "Error setting wallpaper", Toast.LENGTH_SHORT).show();
            }
            else {

                Log.i("start set","start");
                bitmap = BitmapFactory.decodeFile(path);
                myWallpaperManager.setBitmap(bitmap);

                Log.i("finish set img","finished");
            }
            Toast.makeText(mContext, "Wallpaper set", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(mContext, "Error setting wallpaper", Toast.LENGTH_SHORT).show();
        }

    }
}

