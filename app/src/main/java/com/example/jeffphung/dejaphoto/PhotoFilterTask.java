package com.example.jeffphung.dejaphoto;

import android.location.Location;
import android.os.AsyncTask;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by kaijiecai on 4/30/17.
 */

public class PhotoFilterTask extends AsyncTask<PhotoList,String,String>{
    final int KARMA_POINT = 10;
    final int LOCATION_POINT = 10;
    final int TIME_POINT = 10;
    final int DAY_POINT = 10;
    final int WITHINTIME = 2*3600; // within 2 hours
    final int WITHINRANGE = 1000; // within 1000 feet

    GregorianCalendar currentCalendar;
    DejaVuMode dejaVuMode;


    public PhotoFilterTask(DejaVuMode dejaVuMode){
        this.dejaVuMode = dejaVuMode;

    }

    @Override
    protected String doInBackground(PhotoList...photoLists) {
        PhotoList list = photoLists[0];
        list.clear(); //clear all points
        if(dejaVuMode.isDejaVuModeOn()) {
            currentCalendar = new GregorianCalendar();

            for (int i = 0; i < list.size(); i++) {
                Photo photo = list.get(i);
                if (photo != null) {
                    GregorianCalendar calendar = photo.getCalendar(); //photo's calendar
                    Location location = photo.getLocation(); //photo's location

                    //check day of week
                    if (calendar != null && dejaVuMode.isDayModeOn()) {

                        String currentDay = currentCalendar.getDisplayName(
                                Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
                        String calendarDay = calendar.getDisplayName(
                                Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
                        if (currentDay.equals(calendarDay)) {
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
                        photo.addPoints(LOCATION_POINT);

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

    public void filter(PhotoList list){

    }


    public int calendarToSecond(GregorianCalendar calendar){
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        return hour*3600+minute*60+second;
    }

}
