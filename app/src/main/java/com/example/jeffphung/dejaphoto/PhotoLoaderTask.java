package com.example.jeffphung.dejaphoto;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.List;

import static android.media.ExifInterface.TAG_GPS_DATESTAMP;
import static android.media.ExifInterface.TAG_GPS_LATITUDE;
import static android.media.ExifInterface.TAG_GPS_LATITUDE_REF;
import static android.media.ExifInterface.TAG_GPS_LONGITUDE;
import static android.media.ExifInterface.TAG_GPS_LONGITUDE_REF;
import static android.media.ExifInterface.TAG_GPS_TIMESTAMP;
import static android.media.ExifInterface.TAG_IMAGE_LENGTH;
import static android.media.ExifInterface.TAG_IMAGE_WIDTH;

/**
 * Created by kaijiecai on 4/29/17.
 */

public class PhotoLoaderTask extends AsyncTask<Void,String,PhotoList> {
    final private String TAG_KARMA = "TAG_KARMA";
    final private String TAG_RELEASED = "TAG_RELEASED";
    final private int YEAR_INDEX = 0;
    final private int MONTH_INDEX = 1;
    final private int DAY_INDEX = 2;
    final private int HOUR_INDEX = 0;
    final private int MINUTE_INDEX = 1;
    final private int SECOND_INDEX = 2;


    public PhotoLoaderTask(){

    }

    @Override
    protected PhotoList doInBackground(Void... params) {
        PhotoList list = new PhotoList();

        for(int i = 0; i< 10; i++){
            try {
                String path = "";
                ExifInterface exifInterface = new ExifInterface(path);


                String imgWidth = exifInterface.getAttribute(TAG_IMAGE_WIDTH);
                String imgLength = exifInterface.getAttribute(TAG_IMAGE_LENGTH);
                String time = exifInterface.getAttribute(TAG_GPS_TIMESTAMP);
                String date = exifInterface.getAttribute(TAG_GPS_DATESTAMP);
                String karma = exifInterface.getAttribute(TAG_KARMA);
                String released = exifInterface.getAttribute(TAG_RELEASED);
                String gps_longitude = exifInterface.getAttribute(TAG_GPS_LONGITUDE);
                String gps_longitude_ref = exifInterface.getAttribute(TAG_GPS_LONGITUDE_REF);
                String gps_latitude = exifInterface.getAttribute(TAG_GPS_LATITUDE);
                String gps_latitude_ref = exifInterface.getAttribute(TAG_GPS_LATITUDE_REF);

                Location location = toLocation
                        (gps_longitude,gps_longitude_ref,gps_latitude,gps_latitude_ref);

                Photo photo = new Photo(
                        path,
                        Integer.parseInt(imgWidth),
                        Integer.parseInt(imgLength),
                        toGregorianCalendar(date,time),
                        location,
                        toLocationName(location),
                        toBoolean(karma),
                        toBoolean(released));
                list.add(photo);


            } catch (IOException e) {
                e.printStackTrace();

            }

        }

        return list;

    }


    /* convert string to boolean type */
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


    /* generate a GregorianCalendar by dateStamp and timeStamp */
    public GregorianCalendar toGregorianCalendar(String dateStamp, String timeStamp){
        GregorianCalendar calendar = null;
        if(dateStamp != null && timeStamp != null){
            String[] date = dateStamp.split(":");
            String[] time = dateStamp.split(":");
            if(date.length ==3 && time.length ==3){
                calendar = new GregorianCalendar(Integer.parseInt(date[YEAR_INDEX])
                        ,Integer.parseInt(date[MONTH_INDEX])
                        ,Integer.parseInt(date[DAY_INDEX])
                        ,Integer.parseInt(time[HOUR_INDEX])
                        ,Integer.parseInt(time[MINUTE_INDEX])
                        ,Integer.parseInt(time[SECOND_INDEX]));
            }

        }
        return calendar;
    }


    //TODO
    /* convert longitude and latitude to a Location Object */
    public Location toLocation(String lo, String lo_ref,String la, String la_ref){
        Location location= null;
        if(lo != null && la != null){
            location= new Location(lo+la);
            location.setLatitude(toDouble(la,la_ref));
            location.setLongitude(toDouble(lo,lo_ref));
        }
        return location;
    }

    /* get location name from its latitude and longtidu */
    public List<Address> toLocationName(Location location) {
        List<Address> addresses = null;
        if(location != null){
            Geocoder geocoder = new Geocoder(new Activity());
            try {
                addresses = geocoder.getFromLocation(location.getLatitude(),
                        location.getLongitude(),1);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return addresses;
    }

    /* convert a DMS to decimal */
    public Double toDouble(String gps,String ref) {
        String[] gps_dms = gps.split(",");
        double d = Integer.parseInt(gps_dms[0].split("/")[0])/1.0;
        double min = Integer.parseInt(gps_dms[1].split("/")[0])/60.0;
        double sec = Integer.parseInt(gps_dms[2].split("/")[0])/(3600.0)/(100.0);

        if(ref.equals("W") || ref.equals("S"))
            return (d+min+sec)*(-1);
        return d+min+sec;
    }




    public String getA(){

        String dateTime = "asd";


        String path1= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        path1 +="/Camera/IMG_20170429_190323.jpg";

        File file = new File(path1);
        //File[] files = file.listFiles();

        String ExternalStorageDirectoryPath = Environment
                .getExternalStorageDirectory()
                .getAbsolutePath();

        path1 = ExternalStorageDirectoryPath + "/Download/img2.jpg";

        String exif="";

        double lo=0.0;
        try {
            //dateTime = "aaa";
            ExifInterface exifInterface = new ExifInterface(path1);
            //dateTime = "BBB";

            exif += "\nIMAGE_LENGTH: " + exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
            exif += "\nIMAGE_WIDTH: " + exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
            exif += "\n DATETIME: " + exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
            exif += "\n TAG_MAKE: " + exifInterface.getAttribute(ExifInterface.TAG_MAKE);
            exif += "\n TAG_MODEL: " + exifInterface.getAttribute(ExifInterface.TAG_MODEL);
            exif += "\n TAG_ORIENTATION: " + exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
            exif += "\n TAG_WHITE_BALANCE: " + exifInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE);
            exif += "\n TAG_FOCAL_LENGTH: " + exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH);
            exif += "\n TAG_FLASH: " + exifInterface.getAttribute(ExifInterface.TAG_FLASH);
            exif += "\nGPS related:";
            exif += "\n TAG_GPS_DATESTAMP: " + exifInterface.getAttribute(TAG_GPS_DATESTAMP);
            exif += "\n TAG_GPS_TIMESTAMP: " + exifInterface.getAttribute(TAG_GPS_TIMESTAMP);
            exif += "\n TAG_GPS_LATITUDE: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            exif += "\n TAG_GPS_LATITUDE_REF: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            exif += "\n TAG_GPS_LONGITUDE: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            exif += "\n TAG_GPS_LONGITUDE_REF: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
            exif += "\n TAG_GPS_PROCESSING_METHOD: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD);
            lo = toDouble(exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE),
                    exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            dateTime = e.toString();

        }




        return lo+"";
    }




}
