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
    boolean allowed = true;


    /**
     * private constructor prevent other from initializing
     */
    private PhotoList(){
        photoArrayList = new ArrayList<>();
        index = 0;
    }

    public Boolean isAllowed(){
        return allowed;
    }

    public void setAllowed(Boolean b){
        allowed = b;
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
        if (photoArrayList.size() == 0) return null;
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
        if(size() != 0) {
            return photoArrayList.get(i);
        }
        return null;
    }

    public int size(){
        return photoArrayList.size();
    }

    public void sort(){
        Collections.sort(photoArrayList,Collections.<Photo>reverseOrder());
    }

    public void setIndex(int i){
        this.index = i;
    }

}
