package com.example.jeffphung.dejaphoto;

//import android.app.PendingIntent;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */

public class NewAppWidget extends AppWidgetProvider {
    private static  String nextButtonClicked = "nextButtonClicked";
    private static  String prevButtonClicked = "prevButtonClicked";
    private static  String karmaButtonClicked = "karmaButtonClicked";
    private static  String releaseButtonClicked = "releaseButtonClicked";

    static boolean noPhotos = false;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        // Intent for Right Button
        Intent leftButtonIntent = new Intent(context, NewAppWidget.class);
        leftButtonIntent.setAction(prevButtonClicked);
        leftButtonIntent.putExtra("appWidgetId", appWidgetId);
        PendingIntent leftButtonPendingIntent = PendingIntent.getBroadcast(context, appWidgetId, leftButtonIntent, 0);

        // Intent for Left Button
        Intent rightButtonIntent = new Intent(context, NewAppWidget.class);
        rightButtonIntent.setAction(nextButtonClicked);
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

        views.setOnClickPendingIntent(R.id.buttonNext, rightButtonPendingIntent);
        views.setOnClickPendingIntent(R.id.buttonPrevious, leftButtonPendingIntent);
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
        /*System.out.println(PhotoList.getPhotoListInstance().size());
        if (PhotoList.getPhotoListInstance().size() == 0) {
            Log.i("check photo list size", "buttons should not work when list is empty");
            return;
        }*/
        int appWidgetId;
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetId = intent.getIntExtra("appWidgetId", -1);
        Log.i("appWidgetId", appWidgetId + "");
        super.onReceive(context, intent);
        Photo photo = null;
        if (intent.getAction().equals(nextButtonClicked)) {

            Log.i("start get img","start");
            photo = PhotoList.getPhotoListInstance().next();
            if (photo == null) return;
            Log.i("picPath",photo.getImgPath()+"");
            String path = photo.getImgPath();
            Log.i("finish get img", "finished");
            MyWallPaperManager myWallPaperManager = new MyWallPaperManager(context);
            myWallPaperManager.setWallPaper(photo);
            setKarmImage(context, views, photo);

        }
        else if (intent.getAction().equals(prevButtonClicked)) {

            photo = PhotoList.getPhotoListInstance().previous();
            if (photo == null) return;
            Log.i("picPath",photo.getImgPath()+"");
            Log.i("start get img", "start");
            String path = photo.getImgPath();
            Log.i("finish get img", "finished");
            MyWallPaperManager myWallPaperManager = new MyWallPaperManager(context);
            myWallPaperManager.setWallPaper(photo);
            // put behavior here:
            setKarmImage(context, views, photo);
        }
        else if (intent.getAction().equals(karmaButtonClicked))
        {
            photo = PhotoList.getPhotoListInstance().getCurrentPhoto();
            if (photo == null) return;
            if (!photo.getKarma()) {
                photo.setKarma(true);
            }
            views.setImageViewResource(R.id.buttonKarma, R.drawable.karma_colored);

        }
        else if (intent.getAction().equals(releaseButtonClicked))
        {
            photo = PhotoList.getPhotoListInstance().removeCurrentPhoto();
            Log.i("finish get img", "finished");
            MyWallPaperManager myWallPaperManager = new MyWallPaperManager(context);
            myWallPaperManager.setWallPaper(photo);
            if (photo != null) {
                setKarmImage(context, views, photo);
            }
            else views.setImageViewResource(R.id.buttonKarma, R.drawable.karma_greyed);

        }
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }




    public void setKarmImage(Context context, RemoteViews v, Photo p){

        RemoteViews views = v;
        if (p.getKarma()) {
            views.setImageViewResource(R.id.buttonKarma, R.drawable.karma_colored);
        }
        else {
            views.setImageViewResource(R.id.buttonKarma, R.drawable.karma_greyed);
        }

    }
}

