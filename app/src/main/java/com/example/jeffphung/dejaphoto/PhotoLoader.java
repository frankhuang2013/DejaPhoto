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


        try {
            //dateTime = "aaa";
            ExifInterface exifInterface = new ExifInterface(path1);
            //dateTime = "BBB";
            dateTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            dateTime = e.toString();

        }

        //if(dateTime == null)
            //dateTime = "aaa";


       // dateTime = files[3] +"";



        return dateTime;
    }

}
