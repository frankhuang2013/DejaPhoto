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
    int index = 0;


    /**
     * private constructor prevent other from initializing
     */
    private PhotoList(){
        photoArrayList = new ArrayList<>();
    }

    /**
     * get Photolist instance
     * @return
     */
    public static PhotoList getPhotoListInstance(){
        return photoListInstance;
    }



    public Photo next(){
        if(index == photoArrayList.size()){
            index = 0;
        }
        return photoArrayList.get(index++);
    }


    public Photo previous(){
        if( index == -1){
            index = photoArrayList.size()-1;
        }
        return photoArrayList.get(index--);
    }

    public void add(Photo p){
        photoArrayList.add(p);
    }



    public void clear(){
        for(int i = 0; i < photoArrayList.size(); i++){
            if(photoArrayList != null)
                photoArrayList.get(i).setPoints(0);
        }
    }

    public int size(){
        return photoArrayList.size();
    }

    public Photo get(int index){
        return photoArrayList.get(index);
    }
    public void sort(){
        Collections.sort(photoArrayList);
    }


}
