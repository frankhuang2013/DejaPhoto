package com.example.jeffphung.dejaphoto;

import android.support.annotation.NonNull;

/**
 * Created by kaijiecai on 4/29/17.
 */

public class Photo implements Comparable<Photo> {

    private String time;
    private String dayOfWeek;
    private String locationName;
    private Boolean Karma;
    private Boolean Released;
    private int points;

    public Photo(){

    }

    public Photo(String time, String dayOfWeek, String locationName, Boolean Karma, Boolean Released){
        this.time = time;
        this.dayOfWeek = dayOfWeek;
        this.locationName = locationName;
        this.Karma = Karma;
        this.Released = Released;
    }

    public void AddPoints(){
        this.points +=10;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Boolean getKarma() {
        return Karma;
    }

    public void setKarma(Boolean karma) {
        Karma = karma;
    }

    public Boolean getReleased() {
        return Released;
    }

    public void setReleased(Boolean released) {
        Released = released;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }





    @Override
    public int compareTo(@NonNull Photo o) {
        if(getPoints() > o.getPoints()){
            return 1;
        }
        else if(getPoints() < o.getPoints())
            return -1;

        else if (getTime().compareTo(o.getTime()) > 0){
            return 1;
        }
        else
            return -1;

    }
}
