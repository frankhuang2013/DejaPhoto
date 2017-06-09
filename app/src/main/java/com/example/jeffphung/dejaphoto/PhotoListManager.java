package com.example.jeffphung.dejaphoto;

import java.util.ArrayList;

/**
 * Created by kaijiecai on 6/1/17.
 */

public class PhotoListManager {

    final static String dejaPhoto = "DejaPhoto";
    final static String dejaPhotoCopied = "DejaPhotoCopied";
    final static String dejaPhotoFriend = "DejaPhotoFriends";
    static PhotoListManager photoListManager = new PhotoListManager();
    ArrayList<PhotoList> photolists = new ArrayList<>();

    PhotoList mainPhotoList = new PhotoList("");

    public PhotoListManager(){

    }

    public void setMainPhotoList(PhotoList p){
        mainPhotoList = p;
    }

    public static PhotoListManager getPhotoListManagerInstance(){
        return photoListManager;
    }

    public PhotoList getMainPhotoList(){
        return mainPhotoList;
    }

    public void addPhotoList(PhotoList p){
        photolists.add(p);
    }

    public PhotoList getPhotoList(String name){
        for(int i = 0; i < photolists.size(); i++){
            if(photolists.get(i).getId().equals(name)){
                return photolists.get(i);
            }
        }
        return null;
    }

    public static void updateMainPhotolist(){
        Options options = Options.getOptionsInstance();
        PhotoList p = new PhotoList("main");
        if( options.isShowFriendPhotos()){
            p.mergeLists(photoListManager.getPhotoList(dejaPhotoFriend));
        }
        if( options.isShowMyPhotos()){
            p.mergeLists(photoListManager.getPhotoList(dejaPhoto));
            p.mergeLists(photoListManager.getPhotoList(dejaPhotoCopied));
        }
        p.sort();
        PhotoListManager.getPhotoListManagerInstance().setMainPhotoList(p);
    }


}
