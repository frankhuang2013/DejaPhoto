package com.example.jeffphung.dejaphoto;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by kaijiecai on 4/30/17.
 */

public class PhotoSorterTask extends AsyncTask<Void,String,String>{
    private final int KARMA_POINTS = 10;
    private final int LOCATION_POINTS = 10;
    private final int TIME_POINTS = 10;
    private final int DAY_POINTS = 10;
    private final int WITHINTIME = 2*3600; // within 2 hours
    private final double WITHINRANGE = 304.8; // within 1000 feet/304.8 meters
    PhotoList photoList;

    GregorianCalendar currentCalendar;
    DejaVuMode dejaVuMode;
    Location currentLocation;
    Context mContext;


    /* photoSorterTask constructor, pass current dejaVuMode as parmater */
    public PhotoSorterTask(Context context){
        mContext = context;



    }


    public PhotoSorterTask(Location currentLocation, Context context){
        mContext = context;
        this.currentLocation = currentLocation;
        Log.i("current Location",currentLocation+"");
    }

    @Override
    protected void onPreExecute(){
        dejaVuMode = DejaVuMode.getDejaVuModeInstance();
        currentCalendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        photoList= PhotoListManager.getPhotoListManagerInstance().getPhotoList();

    }

    @Override
    public void onPostExecute (String result){

    }


    @Override
    protected String doInBackground(Void...params) {
        Log.i("photo sorter","--------------");
        Log.i("photo sorter", "sorter invoked");
            if (dejaVuMode.isDejaVuModeOn()) {

            for (int i = 0; i < photoList.size(); i++) {
                Photo photo = photoList.getPhoto(i);
                Log.i("Path", photo.getPoints() + "" + photo.getImgPath());
            /* check if photo is null or if the photo is released by user */
                if (photo != null && !photo.isReleased()) {
                    photo.setPoints(0);
                    GregorianCalendar calendar = photo.getCalendar(); //photo's calendar
                    Location location = photo.getLocation(); //photo's location

                    //check day of week
                    if (calendar != null && currentCalendar != null && dejaVuMode.isDayModeOn()) {
                        if (sameDayOfWeek(currentCalendar, calendar)) {
                            photo.addPoints(DAY_POINTS);
                            Log.i("Same Day of Week: ", calendar.get(calendar.DAY_OF_WEEK) + "");
                        }
                    }

                    //check within two hours
                    if (calendar != null && currentCalendar != null && dejaVuMode.isTimeModeOn()) {
                        if (withinHours(currentCalendar, calendar)) {
                            photo.addPoints(TIME_POINTS);
                            Log.i("Hour of Day: ", calendar.get(calendar.HOUR_OF_DAY) + "");
                        }
                    }

                    //check withinLocation
                    if (location != null && currentLocation != null && dejaVuMode.isLocationModeOn()) {
                        if (isLocationClose(currentLocation, location)) {
                            photo.addPoints(LOCATION_POINTS);
                            Log.i("Within Location: ", location + "");

                        }


                    }
                    //check Karma
                    if (photo.getKarma()) {
                        photo.addPoints(KARMA_POINTS);
                        Log.i("Karma Pressed: ", "true");
                    }

                }
                Log.i("Path", photo.getPoints() + "" + photo.getImgPath());
            }

        }


        Log.i("photo sorter ends","--------------");
        //sort the list according to points
        photoList.sort();
        photoList.setIndex(0);
        // set the first photo in the list as background
        MyWallPaperManager myWallPaperManager = new MyWallPaperManager(mContext);
        myWallPaperManager.setWallPaper(photoList.getPhoto(0));

        return "";
    }

    /* check if two location are close */
    public boolean isLocationClose(Location currentLocation, Location location){
        if(currentLocation.distanceTo(location) <= WITHINRANGE) {
            return true;
        }
        return false;
    }

    /* check if same day of week */
    public boolean sameDayOfWeek(GregorianCalendar currentCalendar,GregorianCalendar calendar){
        Log.i("current day of week: ",""+currentCalendar.get(Calendar.DAY_OF_WEEK));
        Log.i("photo's day of week: ",""+calendar.get(Calendar.DAY_OF_WEEK));
        System.out.print(""+currentCalendar.get(Calendar.DAY_OF_WEEK));
        if(currentCalendar.get(Calendar.DAY_OF_WEEK)==
                (calendar.get(Calendar.DAY_OF_WEEK))) {
            return true;
        }

        return false;
    }


    /* convert calendar time to second */
    public int calendarToSecond(GregorianCalendar calendar){
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        return hour*3600+minute*60+second;
    }

    /* check if two calendar time are close */
    public boolean withinHours(GregorianCalendar currentCalendar,GregorianCalendar calendar){
        Log.i("sorter current UTC time", currentCalendar.get(Calendar.HOUR_OF_DAY)+"");
        Log.i("sorter photo's UTC time", calendar.get(Calendar.HOUR_OF_DAY)+"");
        int currentTime = calendarToSecond(currentCalendar);
        int calendarTime = calendarToSecond(calendar);
        if(Math.abs(currentTime - calendarTime) <= WITHINTIME){
            return true;
        }
        return false;
    }
}
