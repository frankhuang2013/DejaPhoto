package com.example.jeffphung.dejaphoto;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

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

    final String externalPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
    final String dejaPhoto = "DejaPhoto";
    final String dejaPhotoCopied = "DejaPhotoCopied";
    final String dejaPhotoFriend = "DejaPhotoFriends";
    PhotoListManager photoListManager;
    Context mContext;

    int index = 0;
    String account;

    int numPhoto;

    public ShareManager(Context c) {
        mContext = c;
    }

    public int share(List<String> emailList) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        PhotoList photoList = PhotoListManager.getPhotoListManagerInstance().getPhotoList(dejaPhoto);
        photoList.mergeLists(PhotoListManager.getPhotoListManagerInstance().getPhotoList(dejaPhotoCopied));

        numPhoto= photoList.size();
        for (int i = 0; i < photoList.size(); i++) {

            final String imgPath = "/" + emailList.get(0) + "/"+i+".jpg";
            StorageReference imagesRef = storageRef.child(imgPath);

            InputStream stream = null;

            try {
                stream = new FileInputStream(new File(photoList.getPhoto(i).getImgPath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            UploadTask uploadTask = imagesRef.putStream(stream);


        }
        return numPhoto;
    }

    public void unshare(List<String> emailList) {


        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();


        for (int i = 0; i < numPhoto; i++) {


            final String imgPath = "/" + emailList.get(0) + "/"+i+".jpg";
            StorageReference imagesRef = storageRef.child(imgPath);

            imagesRef.delete();

        }

    }

    public void friendCopy(List<String> emailList, String name, int num) {

        Log.i("-------emailListsize",emailList.size()+"");
        Log.i("current email: ",""+ emailList.get(0));

        for (int i = 1; i < emailList.size(); i++) {

            String currentEmail = emailList.get(i);

            if (currentEmail.equals(name)) {
                account = name;
                Log.i("-----currentEmail", currentEmail + "");
                FirebaseStorage storage = FirebaseStorage.getInstance();
                for(int n = 0; n < num; n++) {
                    StorageReference storageRef = storage.getReference().child(name+"/"+n+".jpg");

                    Log.i("------path", storageRef+"");

                    index = n;

                    //PhotoList theInstance = photoListManager.getPhotoListManagerInstance().getMainPhotoList();


                    final long TENMB = 1000000000;
                    storageRef.getBytes(TENMB).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            //create image from byte array given from database
                            try {
                                Log.i("---------photo", "thisisatest");
                                String path = externalPath+"/"+dejaPhotoCopied+"/"+account+"/"+index;
                                FileOutputStream fos = new FileOutputStream(path);
                                fos.write(bytes);
                                fos.close();
                                final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                final Uri contentUri = Uri.fromFile(new File(path));
                                scanIntent.setData(contentUri);
                                mContext.sendBroadcast(scanIntent);

                                Photo photo = ExifDataParser.createNewPhoto(path);
                                PhotoListManager.getPhotoListManagerInstance().getPhotoList(dejaPhotoCopied).add(photo);
                            } catch (Exception e) {
                                System.out.println("FILE TOO BIG");
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            Log.i("---------photofailure", "thisisatest");
                        }
                    });
                }


            }
        }
    }
}
