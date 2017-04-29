package com.example.jeffphung.dejaphoto;

/**
 * Created by kaijiecai on 4/28/17.
 *
 * This is dejaVuMode class which contains all boolean values for modes inside the dejaVuMode
 * all modes are true by default
 *
 * call set+Modename(boolean b) to set turn on/off the mode
 *    - true is on, false is off
 * call is+ModenameOn to get the current mode status
 *
 */

public class DejaVuMode {
    private boolean dejaVuModeOn = true;
    private boolean locationModeOn = true;
    private boolean timeModeOn = true;
    private boolean dayModeOn = true;


    /* return true if all sub-modes are off */
    private boolean isAllModeOff(){
        return !isDayModeOn()&&!isLocationModeOn()&&!isTimeModeOn();
    }

    /* return true if all sub-modes are on */
    private boolean isAllModeOn(){
        return isDayModeOn()&&isLocationModeOn()&&isTimeModeOn();
    }

    public boolean isDejaVuModeOn() {
        return dejaVuModeOn;
    }

    /* turn off DejaVuMode will automatically turn off all modes */
    public void setDejaVuModeOn(boolean b) {
        this.dejaVuModeOn = b;
        if(!b){
            this.dayModeOn=false;
            this.locationModeOn=false;
            this.timeModeOn=false;
        }
        else{
            this.dayModeOn=true;
            this.locationModeOn=true;
            this.timeModeOn=true;
        }

    }

    public boolean isLocationModeOn() {
        return locationModeOn;
    }


    /* if received a true, it will turn on the DejaVuMode
     * if all sub-modes are off, it will turn off the DejaVuMode
     */
    public void setLocationModeOn(boolean b) {
        this.locationModeOn = b;
        if(isAllModeOff()){
            this.dejaVuModeOn = false;
        }
        else if(b){
            this.dejaVuModeOn = true;
        }
    }

    public boolean isTimeModeOn() {
        return timeModeOn;
    }

    /* if received a true, it will turn on the DejaVuMode
     * if all sub-modes are off, it will turn off the DejaVuMode
     */
    public void setTimeModeOn(boolean b) {
        this.timeModeOn = b;
        if(isAllModeOff()){
            this.dejaVuModeOn = false;
        }
        else if(b){
            this.dejaVuModeOn = true;
        }
    }

    public boolean isDayModeOn() {
        return dayModeOn;
    }


    /* if received a true, it will turn on the DejaVuMode
     * if all sub-modes are off, it will turn off the DejaVuMode
     */
    public void setDayModeOn(boolean b) {
        this.dayModeOn = b;
        if(isAllModeOff()){
            this.dejaVuModeOn = false;
        }
        else if(b){
            this.dejaVuModeOn = true;
        }
    }

}
