package com.example.jeffphung.dejaphoto;

import android.location.Location;
import android.os.AsyncTask;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

    GregorianCalendar currentCalendar;
    DejaVuMode dejaVuMode;
    Location currentLocation;
    PhotoList list;


    /* photoSorterTask constructor, pass current dejaVuMode as parmater */
    public PhotoSorterTask(){


    }

    public PhotoSorterTask(Location currentLocation){
        this.currentLocation = currentLocation;
    }

    @Override
    protected void onPreExecute(){
        dejaVuMode = DejaVuMode.getDejaVuModeInstance();
        currentCalendar = new GregorianCalendar();
        list = PhotoList.getPhotoListInstance();

    }


    @Override
    protected String doInBackground(Void...params) {
        list.clear(); //clear all points
        if(dejaVuMode.isDejaVuModeOn()) {

            for (int i = 0; i < list.size(); i++) {
                Photo photo = list.getPhoto(i);
                /* check if photo is null or if the photo is released by user */
                if (photo != null && ! photo.isReleased()) {
                    GregorianCalendar calendar = photo.getCalendar(); //photo's calendar
                    Location location = photo.getLocation(); //photo's location

                    //check day of week
                    if (calendar != null && dejaVuMode.isDayModeOn()) {
                        if(sameDayOfWeek(currentCalendar,calendar)){
                            photo.addPoints(DAY_POINTS);
                        }
                    }

                    //check within two hours
                    if (calendar != null && dejaVuMode.isTimeModeOn()){
                        if (withinHours(currentCalendar,calendar)) {
                            photo.addPoints(TIME_POINTS);
                        }
                    }

                    //check withinLocation
                    if (location != null && dejaVuMode.isLocationModeOn()) {
                        if(isLocationClose(currentLocation, location)){
                            photo.addPoints(LOCATION_POINTS);

                        }


                    }
                    //check Karma
                    if (photo.getKarma()) {
                        photo.addPoints(KARMA_POINTS);
                    }

                }
            }

        }

        //sort the list according to points
        list.sort();
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
        if(currentCalendar.get(Calendar.DAY_OF_WEEK)
                == calendar.get(Calendar.DAY_OF_WEEK))
            return true;

        return false;
    }

    /* convert calendar time to second */
    public int calendarToSecond(GregorianCalendar calendar){
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        return hour*3600+minute*60+second;
    }

    /* check if two calendar time are close */
    public boolean withinHours(GregorianCalendar currentCalendar,GregorianCalendar calendar){
        int currentTime = calendarToSecond(currentCalendar);
        int calendarTime = calendarToSecond(calendar);
        if(Math.abs(currentTime - calendarTime) <= WITHINTIME){
            return true;
        }
        return false;
    }
}
