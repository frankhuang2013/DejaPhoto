package com.example.jeffphung.dejaphoto;

import android.location.Address;
import android.location.Location;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by kaijiecai on 4/29/17.
 */

public class Photo implements Comparable<Photo> {


    private String imgPath;
    private int imgWidth;
    private int imgLength;
    private GregorianCalendar calendar;
    private List<Address> locationName;
    private Location location;
    private Boolean karma = false;
    private Boolean released = false;
    private int points = 0;

    public Photo(){

    }

    public Photo(
            String imgPath,
            int imgWidth,
            int imgLength,
            GregorianCalendar calendar,
            Location location,
            List<Address> locationName,
            Boolean karma,
            Boolean released){

        this.imgPath = imgPath;
        this.imgWidth = imgWidth;
        this.imgLength = imgLength;
        this.calendar = calendar;
        this.location = location;
        this.locationName = locationName;
        this.karma = karma;
        this.released = released;
    }

    public void addPoints(int points){
        this.points +=points;
    }




    public Boolean getKarma() {
        return karma;
    }

    public void setKarma(Boolean karma) {
        this.karma = karma;
    }

    public Boolean isReleased() {
        return released;
    }

    public void setReleased(Boolean released) {
        this.released = released;
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

    public int getImgWidth() { return imgWidth; }

    public int getImgLength() { return imgLength;}


    @Override
    public int compareTo(Photo o) {
        if(getPoints() > o.getPoints()){
            return 1;
        }
        else if(getPoints() < o.getPoints())
            return -1;

        else if (getCalendar().compareTo(o.getCalendar()) > 0){
            return 1;
        }
        else
            return -1;

    }

    public Location getLocation() {
        return location;
    }


}
