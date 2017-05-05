package com.example.jeffphung.dejaphoto;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

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

/* this class will load photo from default camera album */
 //
public class PhotoLoaderTask extends AsyncTask<Void,String,String> {
    final private String TAG_KARMA = "TAG_KARMA";
    final private String TAG_RELEASED = "TAG_RELEASED";
    final private int YEAR_INDEX = 0;
    final private int MONTH_INDEX = 1;
    final private int DAY_INDEX = 2;
    final private int HOUR_INDEX = 0;
    final private int MINUTE_INDEX = 1;
    final private int SECOND_INDEX = 2;

    Context mContext;
    ProgressDialog progressDialog;

    public PhotoLoaderTask(){

    }

    public PhotoLoaderTask(Context context){
        this.mContext = context;
    }

    @Override
    protected void onPreExecute(){
        progressDialog = ProgressDialog.show(mContext,
                "ProgressDialog",
                "Wait for loading photos");
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


    @Override
    protected String doInBackground(Void... params) {
        Log.i("start loading","start loading");
        String[] strings = {"img1.jpg","img2.jpg","img3.jpg","img4.jpg","img5.jpg","img6.jpg"
        ,"img7.jpg","img8.jpg","img9.jpg","img10.jpg"};
        for(int i = 0; i< strings.length; i++){
            try {
                Log.i("start loading","load "+i+"th photo");
                String path = Environment
                        .getExternalStorageDirectory()
                        .getAbsolutePath()+"/Download/" + strings[i];

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



                Log.i("path",path);
                Log.i(location+"",location+"");
                Photo photo = new Photo(
                        path,
                        Integer.parseInt(imgWidth),
                        Integer.parseInt(imgLength),
                        toGregorianCalendar(date,time),
                        location,
                        toLocationName(location),
                        toBoolean(karma),
                        toBoolean(released));
                PhotoList.getPhotoListInstance().add(photo);
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


    /* convert longitude and latitude to a Location Object */
    public Location toLocation(String lo, String lo_ref,String la, String la_ref){
        Location location= null;
        if(lo != null && la != null){
            double la_double = toDouble(la,la_ref);
            double lo_double = toDouble(lo,lo_ref);
            Log.i("la double",la_double+"");
            Log.i("lo_double",lo_double+"");
            if(la_double != 0.0 && lo_double != 0.0) {
                location= new Location(lo+la);
                location.setLatitude(la_double);
                location.setLongitude(lo_double);
            }
        }
        return location;
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

    /* get location name from its location object */
    public Address toLocationName(Location location) {
        List<Address> addresses = null;
        if(location != null){
            Geocoder geocoder = new Geocoder(mContext);
            try {
                addresses = geocoder.getFromLocation(location.getLatitude(),
                        location.getLongitude(),1);
                return addresses.get(0);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }



}
