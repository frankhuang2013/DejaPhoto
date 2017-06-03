package com.example.jeffphung.dejaphoto;

/**
 * Created by kaijiecai on 6/1/17.
 */

public class PhotoListManager {
    static PhotoListManager photoListManager = new PhotoListManager();

    PhotoList photoList = new PhotoList();

    public PhotoListManager(){

    }

    public void setPhotoList(PhotoList p){
        photoList = p;
    }

    public static PhotoListManager getPhotoListManagerInstance(){
        return photoListManager;
    }

    public PhotoList getPhotoList(){
        return photoList;
    }
}
