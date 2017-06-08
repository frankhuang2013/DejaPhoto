package com.example.jeffphung.dejaphoto;

import android.os.Environment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by not us on 6/4/17.
 */

public class ShareManager {

    final String dejaPhoto = "DejaPhoto";
    final String dejaPhotoCopied = "DejaPhotoCopied";

    public ShareManager() {
    }

    public void share(List<String> emailList, PhotoList photoList) {

        String mediaStorePath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myFirebaseRef = database.getReference();

        //for(int i = 0 ; i < emailList.size(); i++) {
        //    System.out.println(emailList.get(i));
        //}

        PhotoList shareList = PhotoListManager.getPhotoListManagerInstance().getPhotoList(dejaPhoto);
        PhotoList shareListCopied = PhotoListManager.getPhotoListManagerInstance().getPhotoList(dejaPhotoCopied);

        for (Photo photo : shareList.getPhotoArrayList()) {
            photo.setUser(emailList.get(0));
            String photoId = photo.getImgPath().replace(".", "");
            photoId = photoId.replace("/storage/sdcard/DCIM/", "");
            myFirebaseRef.child(photo.getUser()).child(photoId).setValue(photo);
        }

        for (Photo photo : shareListCopied.getPhotoArrayList()) {
            photo.setUser(emailList.get(0));
            String photoId = photo.getImgPath().replace(".", "");
            photoId = photoId.replace("/storage/sdcard/DCIM/", "");
            myFirebaseRef.child(photo.getUser()).child(photoId).setValue(photo);
        }
    }
}
