package com.example.jeffphung.dejaphoto;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kaijiecai on 4/29/17.
 *
 * call next/previous to get the next/previous photo in the list
 */


public class PhotoList{
    private static PhotoList photoListInstance = new PhotoList();
    ArrayList<Photo> photoArrayList ;
    int index;


    /**
     * private constructor prevent other from initializing
     */
    private PhotoList(){
        photoArrayList = new ArrayList<>();
        index = 0;
    }

    /**
     * get Photolist instance
     * @return
     */
    public static PhotoList getPhotoListInstance(){
        return photoListInstance;
    }


    //need context to be able to set background when there is no pictures
    Context context;
    public void setContext(Context c) {
        context = c;
    }

    public Photo next(){
        if(photoArrayList.size() ==0) {
            return null;

        }
        else{
            if(index == photoArrayList.size()-1){
                index = -1;
            }
            return photoArrayList.get(++index);
        }

    }

    /*
    public Photo next(){
        if(photoArrayList.size() ==0) {
            return null;//// TODO: 5/4/17
        }
        else{
            if(index == photoArrayList.size()-1){
                index = -1;
            }

            // check if the next photo is released
            int counter = 0;
            while (photoArrayList.get(++index).isReleased() && ++counter <= photoArrayList.size())
            {
                if(index == photoArrayList.size()-1){
                    index = -1;
                }

            }
            if (counter > photoArrayList.size())
            {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.defaultwhatever);
                WallpaperManager myWallpaperManager = WallpaperManager.getInstance(context);
                try {
                    myWallpaperManager.setBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(context, "Error setting wallpaper", Toast.LENGTH_SHORT).show();
                }
                return null;
            }

            return photoArrayList.get(index);
        }

    }*/


    /*
    public Photo previous(){
        if(photoArrayList.size() ==0){
            return null ;//TODO
        }
        else{
            if(index == 0){
                index = photoArrayList.size();
            }

            // check if the previous photo is released
            int counter = 0;
            while (photoArrayList.get(--index).isReleased() && ++counter <= photoArrayList.size())
            {
                if(index == 0){
                    index = photoArrayList.size();
                }
            }
            if (counter > photoArrayList.size())
            {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.defaultwhatever);
                WallpaperManager myWallpaperManager = WallpaperManager.getInstance(context);
                try {
                    myWallpaperManager.setBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(context, "Error setting wallpaper", Toast.LENGTH_SHORT).show();
                }
                return null;
            }
            return photoArrayList.get(index);
        }
    }*/


    public Photo previous(){
        if(photoArrayList.size() ==0){
            return null ;//TODO
        }
        else{
            if(index == 0){
                index = photoArrayList.size();
            }
            return photoArrayList.get(--index);
        }
    }

    public Photo removeCurrentPhoto(){
        photoArrayList.get(index).setReleased(true);
        photoArrayList.remove(index--);
        return next();
    }

    public void add(Photo p){
        photoArrayList.add(p);
    }

    public Photo getCurrentPhoto(){
        if(photoArrayList.size() == 0)
            return null;
        return photoArrayList.get(index);
    }

    public Photo getPhoto(int i){
        return photoArrayList.get(i);
    }

    public int size(){
        return photoArrayList.size();
    }

    public void sort(){
        Collections.sort(photoArrayList,Collections.<Photo>reverseOrder());
    }


}
