package com.example.jeffphung.dejaphoto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by not us on 6/4/17.
 */

public class ShareManager {

    final String dejaPhoto = "DejaPhoto";
    final String dejaPhotoCopied = "DejaPhotoCopied";
    final String dejaPhotoFriend = "DejaPhotoFriends";
    PhotoListManager photoListManager;

    public ShareManager() {
    }

    public void share(List<String> emailList) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        PhotoList theInstance = photoListManager.getPhotoListManagerInstance().getMainPhotoList();

        for (int i = 0; i < theInstance.getPhotoArrayList().size(); i++) {

            StorageReference imagesRef = storageRef.child("/" + emailList.get(0) + "/images/" + i);
            InputStream stream = null;

            try {
                stream = new FileInputStream(new File(theInstance.getPhoto(i).getImgPath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            UploadTask uploadTask = imagesRef.putStream(stream);

        }

    }

    public void unshare(List<String> emailList) {


        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        PhotoList theInstance = photoListManager.getPhotoListManagerInstance().getMainPhotoList();

        for (int i = 0; i < theInstance.getPhotoArrayList().size(); i++) {

            StorageReference imagesRef = storageRef.child("/" + emailList.get(0) + "/images/" + i);

            imagesRef.delete();

        }

    }

    public void friendCopy(List<String> emailList) {


        for (int i = 1; i < emailList.size(); i++){
            String currentEmail = emailList.get(i);

            System.out.println("My friends are:" + currentEmail);
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference().child("/mrjohnwhite10@gmail.com/images/0.jpeg");

            //PhotoList theInstance = photoListManager.getPhotoListManagerInstance().getMainPhotoList();

            final long TENMB = 10000000;
            storageRef.getBytes(TENMB).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    //create image from byte array given from database
                    try {
                        System.out.println("GOT THE IMAGE INFO");
                        FileOutputStream fos = new FileOutputStream("/storage/sdcard/DCIM/DejaPhoto/test.jpg");
                        fos.write(bytes);
                        fos.close();
                    }
                    catch (Exception e){
                        System.out.println("FILE TOO BIG");
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });


        }
    }
}
