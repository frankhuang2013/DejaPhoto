package com.example.jeffphung.dejaphoto;

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



    public Photo next(){
        if(photoArrayList.size() ==0) {
            return null;//// TODO: 5/4/17
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

    public void add(Photo p){
        photoArrayList.add(p);
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
