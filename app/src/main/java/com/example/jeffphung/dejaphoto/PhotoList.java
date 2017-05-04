package com.example.jeffphung.dejaphoto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

/**
 * Created by kaijiecai on 4/29/17.
 *
 * call next/previous to get the next/previous photo in the list
 */


public class PhotoList{
    private static PhotoList photoListInstance = new PhotoList();
    ArrayList<Photo> photoArrayList ;
    int index = 0;
    ListIterator<Photo> listIterator;


    /**
     * private constructor prevent other from initializing
     */
    private PhotoList(){
        photoArrayList = new ArrayList<>();
        listIterator = photoArrayList.listIterator();
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
            if(listIterator.hasNext()) {
                return listIterator.next();
            }
            else{
                listIterator = photoArrayList.listIterator();
                return  listIterator.next();
            }
        }
    }


    public Photo previous(){
        if(photoArrayList.size() ==0){
            return null ;//TODO
        }
        else{
            if(listIterator.hasPrevious()){
                return listIterator.previous();
            }
            else{
                listIterator = photoArrayList.listIterator(photoArrayList.size());
                return listIterator.previous();
            }
        }
    }

    public void add(Photo p){
        photoArrayList.add(p);
    }


    public void remove(){
        listIterator.remove();
    }

    public void clear(){
        ListIterator<Photo> clearIterator = photoArrayList.listIterator();
        if(listIterator.hasNext()){
            listIterator.next().setPoints(0);
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
