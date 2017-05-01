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

    public void sort(){
        Collections.sort(photoArrayList);
    }


}
