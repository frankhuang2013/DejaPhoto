package com.example.jeffphung.dejaphoto;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by kaijiecai on 4/30/17.
 */

public class PhotoFilter {
    final int KARMA_POINT = 10;
    final int LOCATION_POINT = 10;
    final int TIME_POINT = 10;
    final int DAY_POINT = 10;
    final int WITHINTIME = 2*3600;

    GregorianCalendar current;


    public PhotoFilter(){

    }

    public void filter(PhotoList list){
        list.clear(); //clear all points
        current = new GregorianCalendar();

        for(int i = 0; i < list.size(); i++){
            Photo photo = list.get(i);
            if(photo!= null){
                GregorianCalendar calendar = photo.getCalendar();

                if(calendar!= null){
                    //check day of week
                    String currentDay = current.getDisplayName(
                            Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
                    String calendarDay = calendar.getDisplayName(
                            Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
                    if(currentDay.equals(calendarDay)){
                        photo.addPoints(DAY_POINT);
                    }

                    //check within two hours
                    int currentTime = calendarToSecond(current);
                    int calendarTime = calendarToSecond(calendar);
                    if(Math.abs(currentTime-calendarTime) <=WITHINTIME){
                        photo.addPoints(TIME_POINT);
                    }
                }

                //check withinLocation
                if(true){
                    photo.addPoints(LOCATION_POINT);

                }


                //check Karma
                if(photo.getKarma()){
                    photo.addPoints(KARMA_POINT);
                }

            }
        }





    }


    public int calendarToSecond(GregorianCalendar calendar){
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        return hour*3600+minute*60+second;
    }

}
