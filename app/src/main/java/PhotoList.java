package com.example.jeffphung.dejaphoto;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kaijiecai on 4/29/17.
 */

public class PhotoList{
    ArrayList<Photo> photoArrayList;

    public Photo next(){
        return null;
    }


    public Photo previous(){
        return null;
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
