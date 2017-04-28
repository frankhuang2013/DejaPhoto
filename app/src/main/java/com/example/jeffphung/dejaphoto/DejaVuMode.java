package com.example.jeffphung.dejaphoto;

/**
 * Created by kaijiecai on 4/28/17.
 *
 * This is dejaVuMode class which contains all boolean values for modes inside the dejaVuMode
 * all modes are true by default
 *
 * use set+Modename(boolean b) to set turn on/off the mode
 *    - true is on, false is off
 * use is+Modename to get the current mode status
 *
 */

public class DejaVuMode {
    private boolean dejaVuMode = true;
    private boolean locationMode = true;
    private boolean timeMode = true;
    private boolean dayMode = true;


    public boolean isDejaVuMode() {
        return dejaVuMode;
    }

    public void setDejaVuMode(boolean dejaVuMode) {
        this.dejaVuMode = dejaVuMode;
        //if user turn off dejaVuMode, all modes inside will automatically be turned off
        if(!dejaVuMode){
            setLocationMode(false);
            setDayMode(false);
            setTimeMode(false);
        }
    }

    public boolean isLocationMode() {
        return locationMode;
    }

    public void setLocationMode(boolean locationMode) {
        this.locationMode = locationMode;
    }

    public boolean isTimeMode() {
        return timeMode;
    }

    public void setTimeMode(boolean timeMode) {
        this.timeMode = timeMode;
    }

    public boolean isDayMode() {
        return dayMode;
    }

    public void setDayMode(boolean dayMode) {
        this.dayMode = dayMode;
    }


}
