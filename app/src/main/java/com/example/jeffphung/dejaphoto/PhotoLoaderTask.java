package com.example.jeffphung.dejaphoto;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.List;

import static android.media.ExifInterface.TAG_DATETIME;
import static android.media.ExifInterface.TAG_GPS_LATITUDE;
import static android.media.ExifInterface.TAG_GPS_LATITUDE_REF;
import static android.media.ExifInterface.TAG_GPS_LONGITUDE;
import static android.media.ExifInterface.TAG_GPS_LONGITUDE_REF;

/**
 * Created by kaijiecai on 4/29/17.
 */

/* this class will load photo from default camera album */
 //
public class PhotoLoaderTask extends AsyncTask<Void,String,String> {

    final private String TAG_KARMA = ExifInterface.TAG_USER_COMMENT;
    final private String TAG_RELEASED = "TAG_RELEASED";
    final private int YEAR_INDEX = 0;
    final private int MONTH_INDEX = 1;
    final private int DAY_INDEX = 2;
    final private int HOUR_INDEX = 0;
    final private int MINUTE_INDEX = 1;
    final private int SECOND_INDEX = 2;

    Context mContext;
    ProgressDialog progressDialog;



    public PhotoLoaderTask(Context context){
        this.mContext = context;
    }

    @Override
    protected void onPreExecute(){
        progressDialog = ProgressDialog.show(mContext,
                "ProgressDialog",
                "Wait for loading photos");

    }

    /* convert string to boolean type
     * accept string "true" or "false" and return corresponding boolean value
     */
    public boolean toBoolean(String str){
        if(str == null){
            return false;
        }
        else if(str.equals("true")){
            return true;
        }
        else
            return false;
    }


    @Override
    protected String doInBackground(Void... params) {
        Log.i("start loading","start loading");
        /*
        String[] strings = {"img1.jpg","img2.jpg","img3.jpg","img4.jpg","img5.jpg","img6.jpg"
        ,"img7.jpg","img8.jpg","img9.jpg","img10.jpg"};*/
        //gets path to camera album photos
        File cameraDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString());

        //there are multiple folders here, but we are only interested in the first one (ie Camera)
        File[] files = cameraDir.listFiles();
        File finalCamDir = files[0];

        File[] camFiles = finalCamDir.listFiles();
        for(int i = 0; i< camFiles.length; i++){
            try {
                Log.i("start loading","load "+i+"th photo");
                /*
                String path = Environment
                        .getExternalStorageDirectory()
                        .getAbsolutePath()+"/Download/" + strings[i];*/

                String path = camFiles[i].toString();
                ExifInterface exifInterface = new ExifInterface(path);


                Log.i("ImagePath",path);

                String released = exifInterface.getAttribute(TAG_RELEASED);
                Log.i("ImageReleased",released+"");
                if(! toBoolean(released)) {

                    String dateTime = exifInterface.getAttribute(TAG_DATETIME);
                    Log.i("ImageDateTime", dateTime + "");
                    String karma = exifInterface.getAttribute(TAG_KARMA);
                    Log.i("ImageKarma", karma + "");
                    String gps_longitude = exifInterface.getAttribute(TAG_GPS_LONGITUDE);
                    Log.i("ImageLongitude", gps_longitude + "");
                    String gps_longitude_ref = exifInterface.getAttribute(TAG_GPS_LONGITUDE_REF);
                    Log.i("ImageLongitude_ref", gps_longitude_ref + "");
                    String gps_latitude = exifInterface.getAttribute(TAG_GPS_LATITUDE);
                    Log.i("ImageLatitude", gps_latitude + "");
                    String gps_latitude_ref = exifInterface.getAttribute(TAG_GPS_LATITUDE_REF);
                    Log.i("ImageLatitude_ref", gps_latitude_ref + "");

                    Location location = toLocation
                            (gps_longitude, gps_longitude_ref, gps_latitude, gps_latitude_ref);
                    Log.i("Location", location + "");


                    Photo photo = new Photo(
                            path,
                            toGregorianCalendar(dateTime),
                            location,
                            toLocationName(location),
                            toBoolean(karma));
                    PhotoList.getPhotoListInstance().add(photo);
                }
            } catch (IOException e) {
                e.printStackTrace();

            }

        }

      //  Toast.makeText(mContext,list.size()+"",Toast.LENGTH_SHORT).show();
        Log.i("end loading","end loading");
        return null;
    }



    @Override
    public void onPostExecute (String result){
        progressDialog.dismiss();
        Intent intent = new Intent(mContext,AutoGPSTimer.class);
        mContext.startService(intent);
        Intent alarmIntent = new Intent(mContext, AutoAlarmTimer.class);
        mContext.startService(alarmIntent);
        PhotoLoaderTask photoLoaderTask = new PhotoLoaderTask(mContext);


    }


    /* generate a GregorianCalendar by dateStamp and timeStamp
     * arg format "YYYY:MM:DD HH:MM:SS"
     */
    public GregorianCalendar toGregorianCalendar(String dateTime){
        GregorianCalendar calendar = null;
        if(dateTime != null ){
            try {
                String dateStamp = dateTime.split("\\s+")[0];
                String timeStamp = dateTime.split("\\s+")[1];
                String[] date = dateStamp.split(":");
                String[] time = timeStamp.split(":");
                if (date.length == 3 && time.length == 3) {
                    calendar = new GregorianCalendar(Integer.parseInt(date[YEAR_INDEX])
                            , (Integer.parseInt(date[MONTH_INDEX])-1)//month start at 0
                            , Integer.parseInt(date[DAY_INDEX])
                            , Integer.parseInt(time[HOUR_INDEX])
                            , Integer.parseInt(time[MINUTE_INDEX])
                            , Integer.parseInt(time[SECOND_INDEX]));
                    return calendar;
                }
            }catch (Exception e){
                return calendar;
            }

        }

        return calendar;
    }


    /* convert longitude and latitude to a Location Object
     * lo format: "d/1,m/1,s/100"
     * lo_ref: "W" or "E"
     * la format: "d/1,m/1,s/100"
     * la_ref: "N" or "S"
     * */
    public Location toLocation(String lo, String lo_ref,String la, String la_ref){
        Location location= null;
        if(lo != null && la != null){
            double la_double = toDouble(la,la_ref);
            double lo_double = toDouble(lo,lo_ref);
            Log.i("la double",la_double+"");
            Log.i("lo_double",lo_double+"");
            if(la_double != 0 && lo_double != 0) {
                location= new Location("location");
                location.setLatitude(la_double);
                location.setLongitude(lo_double);
            }
        }
        return location;
    }


    /* convert a DMS to decimal
     * gps format: "d/1,m/1,s/100"
     * ref: "W" or "E" or "N" or "S"
     * "W" or "S" will make negate the value
     * */
    public Double toDouble(String gps,String ref) {
        if(gps != null && ref != null) {
            String[] gps_dms = gps.split(",");
            double d = Integer.parseInt(gps_dms[0].split("/")[0]) / 1.0;
            double min = Integer.parseInt(gps_dms[1].split("/")[0]) / 60.0;
            double sec = Integer.parseInt(gps_dms[2].split("/")[0]) / (3600.0);

            if (ref.equals("W") || ref.equals("S"))
                return (d + min + sec) * (-1);
            return d + min + sec;
        }
        return null;
    }

    /* get location name from its location object */
    public String toLocationName(Location location) {
        List<Address> addresses;
        if(location != null){
            Geocoder geocoder = new Geocoder(mContext);
            try {
                addresses = geocoder.getFromLocation(location.getLatitude(),
                        location.getLongitude(),5);
                if(addresses.size() > 0) {
                    return addresses.get(0).getLocality();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }



}
