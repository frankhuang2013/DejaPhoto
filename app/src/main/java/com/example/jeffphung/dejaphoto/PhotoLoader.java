package com.example.jeffphung.dejaphoto;

import android.media.ExifInterface;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by kaijiecai on 4/29/17.
 */

public class PhotoLoader {




    public PhotoLoader(){

    }


    public PhotoList load(DejaVuMode dejaVuMode){
        PhotoList list = new PhotoList();

        for(int i = 0; i< 10; i++){
            String path = "";
            String dateTime;
            String dayOfWeek;
            String Karma;
            String Released;
            String location;
            try {

                ExifInterface exifInterface = new ExifInterface(path);

                dateTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
                dayOfWeek = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
                location = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
                Karma = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
                Released = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);


                Photo photo = new Photo();
                list.add(photo);


            } catch (IOException e) {
                e.printStackTrace();

            }

            //check within location

            //check witin time

            //check day of time

            //check karma

            //check released;




        }

        return null;
    }

    public String get(){

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

        try {
            //dateTime = "aaa";
            ExifInterface exifInterface = new ExifInterface(path1);
            //dateTime = "BBB";
            dateTime = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            exifInterface.setAttribute(ExifInterface.TAG_DATETIME,"12/12/23");
            dateTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL);

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
            exif += "\n TAG_GPS_DATESTAMP: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP);
            exif += "\n TAG_GPS_TIMESTAMP: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP);
            exif += "\n TAG_GPS_LATITUDE: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            exif += "\n TAG_GPS_LATITUDE_REF: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            exif += "\n TAG_GPS_LONGITUDE: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            exif += "\n TAG_GPS_LONGITUDE_REF: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
            exif += "\n TAG_GPS_PROCESSING_METHOD: " + exifInterface.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            dateTime = e.toString();

        }

        //if(dateTime == null)
            //dateTime = "aaa";


       // dateTime = files[3] +"";



        return exif;
    }

}
