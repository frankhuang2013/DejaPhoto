package com.example.jeffphung.dejaphoto;

import android.os.Environment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by not us on 6/4/17.
 */

public class ShareManager {

    public ShareManager() {}

    public void share(List<String> emailList, PhotoList photoList) {

        String mediaStorePath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();

        //TODO: Do not share friends' photos


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myFirebaseRef = database.getReference();

        myFirebaseRef.child("ahh!!").setValue("hi");

        for(int i = 0 ; i < emailList.size(); i++){
            System.out.println(emailList.get(i));
        }
        /*

       // PhotoList photoList1 = PhotoListManager.getPhotoListManagerInstance().getPhotoList();

        //Photo p = photoList.getPhoto(0);
       // String path = p.getImgPath();


        //ArrayList<Photo> sharePhotos = new ArrayList<>(photoList.photoArrayList);

        //TODO: Add user field to all photos except DejaPhotoFriends (we need Friends to be separate!)

        for (Photo photo : sharePhotos) {
            //TODO reference.child(photo.getUser()).setValue(photo); --includes karma, location, imgPath :)
        }
*/
    }
}
