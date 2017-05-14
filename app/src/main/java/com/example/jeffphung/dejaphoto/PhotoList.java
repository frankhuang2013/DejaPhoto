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


    /*
     * disallowed get photo when sorting the list
     */
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



    /*
     * return next photo in the list
     * if it is the last photo, return the first one
     * if there is no photo, return null
     */
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
     * return previous photo in the list
     * if is the first photo, return the last one
     * if there is no photo in the list, reutn null
     */
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


    /*
     * remove the current photo from the list
     * mark the photo as released
     * return next photo in the list
     */
    public Photo removeCurrentPhoto(){
        photoArrayList.get(index).setReleased(true);
        photoArrayList.remove(index--);
        return next();
    }


    /*
     * add photo to the list
     */
    public void add(Photo p){
        photoArrayList.add(p);
    }


    /*
     * return current photo
     */
    public Photo getCurrentPhoto(){
        if(photoArrayList.size() == 0)
            return null;
        return photoArrayList.get(index);
    }


    /*
     * get the photo from list by index
     */
    public Photo getPhoto(int i){
        if(size() != 0) {
            return photoArrayList.get(i);
        }
        return null;
    }

    /*
     * return size of list
     */
    public int size(){
        return photoArrayList.size();
    }


    /* call Collection.sort to sort the list in reverseOrder
     * photo with higher points will be the first one
     */
    public void sort(){
        Collections.sort(photoArrayList,Collections.<Photo>reverseOrder());
    }


    /*
     * change the index
     */
    public void setIndex(int i){
        this.index = i;
    }

}
