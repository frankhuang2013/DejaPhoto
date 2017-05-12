package com.example.jeffphung.dejaphoto;

import android.location.Location;
import android.media.ExifInterface;
import android.util.Log;

import java.io.IOException;
import java.util.GregorianCalendar;

/**
 * Created by kaijiecai on 4/29/17.
 */

public class Photo implements Comparable<Photo> {


    final private String TAG_KARMA = ExifInterface.TAG_USER_COMMENT;
    final private String TAG_RELEASED = "TAG_RELEASED";
    private String imgPath;
    private GregorianCalendar calendar;
    private String locationName;
    private Location location;
    private Boolean karma = false;
    private Boolean released = false;
    private int points = 0;

    public Photo(String imgPath){
        this.imgPath = imgPath;
    }

    public Photo(
            String imgPath,
            GregorianCalendar calendar,
            Location location,
            String locationName,
            Boolean karma){

        this.imgPath = imgPath;
        this.calendar = calendar;
        this.location = location;
        this.locationName = locationName;
        this.karma = karma;
    }



    public void addPoints(int points){
        this.points +=points;
    }




    public Boolean getKarma() {
        return karma;
    }

    public void setKarma(Boolean karma) {
        this.karma = karma;
        try {
            ExifInterface exifInterface = new ExifInterface(imgPath);
            exifInterface.setAttribute(TAG_KARMA,"true");
            exifInterface.saveAttributes();
            Log.i("write karma to photo","successfully");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Boolean isReleased() {
        return released;
    }

    public void setReleased(Boolean released) {
        this.released = released;
        try {
            ExifInterface exifInterface = new ExifInterface(imgPath);
            exifInterface.setAttribute(TAG_RELEASED,released+"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


    public GregorianCalendar getCalendar() {
        return calendar;
    }

    public String getImgPath() { return imgPath;}



    @Override
    public int compareTo(Photo o) {
        if(getPoints() > o.getPoints()){
            return 1;
        }
        else if(getPoints() < o.getPoints())
            return -1;

        else if (getCalendar() != null && o.getCalendar() != null) {
            if (getCalendar().compareTo(o.getCalendar())>0) {
                return 1;
            }
            else if(getCalendar().compareTo(o.getCalendar())<0){
                return -1;

            }
        }
        else if( getCalendar() == null){
            return -1;
        }
        else if( o.getCalendar() == null){
            return 1;
        }
        return 0;
    }

    public Location getLocation() {
        return location;
    }


    public String getCityName() {
        return locationName;
    }
}
