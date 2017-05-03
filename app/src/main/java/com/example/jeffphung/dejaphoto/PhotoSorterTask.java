package com.example.jeffphung.dejaphoto;

import android.location.Location;
import android.os.AsyncTask;

import java.util.GregorianCalendar;

/**
 * Created by kaijiecai on 4/30/17.
 */

public class PhotoSorterTask extends AsyncTask<PhotoList,String,String>{
    private final int KARMA_POINT = 10;
    private final int LOCATION_POINT = 10;
    private final int TIME_POINT = 10;
    private final int DAY_POINT = 10;
    private final int WITHINTIME = 2*3600; // within 2 hours
    private final double WITHINRANGE = 304.8; // within 1000 feet/304.8 meters

    GregorianCalendar currentCalendar;
    DejaVuMode dejaVuMode;
    Location currentLocation;


    /* photoSorterTask constructor, pass current dejaVuMode as parmater */
    public PhotoSorterTask(DejaVuMode dejaVuMode, Location location){
        this.dejaVuMode = dejaVuMode;
        currentLocation = location;


    }


    @Override
    protected String doInBackground(PhotoList...photoLists) {
        PhotoList list = photoLists[0];
        list.clear(); //clear all points
        if(dejaVuMode.isDejaVuModeOn()) {
            currentCalendar = new GregorianCalendar();

            for (int i = 0; i < list.size(); i++) {
                Photo photo = list.get(i);
                /* check if photo is null or if the photo is released by user */
                if (photo != null && ! photo.isReleased()) {
                    GregorianCalendar calendar = photo.getCalendar(); //photo's calendar
                    Location location = photo.getLocation(); //photo's location

                    //check day of week
                    if (calendar != null && dejaVuMode.isDayModeOn()) {
                        if(currentCalendar.DAY_OF_WEEK == calendar.DAY_OF_WEEK){
                            photo.addPoints(DAY_POINT);
                        }
                    }

                    //check within two hours
                    if (calendar != null && dejaVuMode.isTimeModeOn()){

                        int currentTime = calendarToSecond(currentCalendar);
                        int calendarTime = calendarToSecond(calendar);

                        if (Math.abs(currentTime - calendarTime) <= WITHINTIME) {
                            photo.addPoints(TIME_POINT);
                        }
                    }

                    //check withinLocation
                    if (location != null && dejaVuMode.isLocationModeOn()) {
                        if(currentLocation.distanceTo(location) <= WITHINRANGE){
                            photo.addPoints(LOCATION_POINT);

                        }



                    }


                    //check Karma
                    if (photo.getKarma()) {
                        photo.addPoints(KARMA_POINT);
                    }

                }
            }

        }

        //sort the list according to points
        list.sort();
        return "";
    }


    public int calendarToSecond(GregorianCalendar calendar){
        int hour = calendar.HOUR;
        int minute = calendar.MINUTE;
        int second = calendar.SECOND;

        return hour*3600+minute*60+second;
    }

}
