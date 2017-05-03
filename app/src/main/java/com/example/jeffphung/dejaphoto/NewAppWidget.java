package com.example.jeffphung.dejaphoto;

//import android.app.PendingIntent;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */

public class NewAppWidget extends AppWidgetProvider {
    private static  String rightButtonClicked = "rightButtonClicked";
    private static  String leftButtonClicked = "leftButtonClicked";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Intent for Right Button
        Intent leftButtonIntent = new Intent(context, NewAppWidget.class);
        leftButtonIntent.setAction(leftButtonClicked);
        PendingIntent leftButtonPendingIntent = PendingIntent.getBroadcast(context, 0, leftButtonIntent, 0);

        // Intent for Right Button
        Intent rightButtonIntent = new Intent(context, NewAppWidget.class);
        rightButtonIntent.setAction(rightButtonClicked);
        PendingIntent rightButtonPendingIntent = PendingIntent.getBroadcast(context, 0, rightButtonIntent, 0);

        views.setOnClickPendingIntent(R.id.buttonRight, rightButtonPendingIntent);
        views.setOnClickPendingIntent(R.id.buttonLeft, leftButtonPendingIntent);
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
        super.onReceive(context, intent);
        if (intent.getAction().equals(rightButtonClicked)) {
            // put behavior here:
            System.out.println("fghdgfhrsjkgfjhgjkrsjkghrjkgjkehfjkgaerhkjjhgjjjghvhjvhjvhjhjvjhgjhgkjgjhhfgh");
        }
        if (intent.getAction().equals(leftButtonClicked)) {
            // put behavior here:
            System.out.println("????????????????????????????????????????????????????????????????????????????????");
        }
    }
}

