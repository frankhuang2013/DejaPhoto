package com.example.jeffphung.dejaphoto;

import android.location.Location;
import android.os.Environment;

import com.google.api.client.util.Data;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by not us on 6/4/17.
 */

public class ShareManager {

    final String dejaPhoto = "DejaPhoto";
    final String dejaPhotoCopied = "DejaPhotoCopied";
    final String dejaPhotoFriend = "DejaPhotoFriends";

    public ShareManager() {
    }

    public void share(List<String> emailList) {

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

    public void unshare(List<String> emailList) {

        String emailToRemove = emailList.get(0);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myFirebaseRef = database.getReference();
        myFirebaseRef.child(emailToRemove).removeValue();
    }

    public void friendCopy(List<String> emailList) {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myFirebaseRef = database.getReference();

        DatabaseReference path = myFirebaseRef.child("/" + "aneleono2@gmailcom" + "/DejaPhoto");

        System.out.println("gimme something " + path.toString());
        path.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()){
                    String infos =  snap.getKey();
                    System.out.println("ahaha " + infos);

                    String imgPath = (String) snap.child("imgPath").getValue();

                    String  widthString = snap.child("width").getValue().toString();
                    Integer width = Integer.valueOf(widthString);

                    String heightString = snap.child("height").getValue().toString();
                    Integer height = Integer.valueOf(heightString);

                    /*
                    GregorianCalendar calendar = (GregorianCalendar) snap.child("calendar").getValue();
                    Location location = (Location) snap.child("location").getValue();
                    String locationName = (String) snap.child("locationName").getValue();
                    Boolean karma = (Boolean) snap.child("karma").getValue();
                    */
                    System.out.println("LOLOL" + width);

                    /*
                    Photo photo = new Photo(imgPath,width, height, calendar, location, locationName, karma);
                    PhotoListManager.getPhotoListManagerInstance()
                            .getPhotoList(dejaPhotoFriend).
                            getPhotoArrayList().add(photo);
                     */
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Attach a listener to read the data at our posts reference
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                    String username = snap.child("/").getKey();
                    //System.out.println("PLEASEE! " + username);





                    DataSnapshot dejaPhotoSnapShot = snap.child("/").child(username);

                    for (DataSnapshot pic : dejaPhotoSnapShot.getChildren()){

                        String ah = (String) pic.getValue();
                        //System.out.println("HOLY SHT " + ah);

                    }



                    //DataSnapshot mainPath =  snap.child("/").child(username).child("");
                    //String dejaPhotoSnapShot = mainPath.getKey();
                    //System.out.println("THAL2132525436346" + dejaPhotoSnapShot);

                    //DataSnapshot photoId = mainPath.child("DejaPhoto").child(dejaPhotoSnapShot);

                    //String imgPath = (String) photoId.child("imgPath").getValue();
                    //System.out.println("DAISY123124235345345" + imgPath);

                    /*for (DataSnapshot photoInfo : path.getChildren()){
                        String imgPath = (String) photoInfo.child("imgPath").getValue();
                        System.out.print("OH MY " + imgPath);
                    }*/

                    //System.out.println("PLEASE WORK" + dejaPhotoSnapShot);



                    //DataSnapshot dejaPhotoCopiedSnapShot = mainPath.child("DejaPhotoCopied");

                    //System.out.println("HOLY SHT1" + dejaPhotoSnapShot.getValue());
                    //System.out.println("HOLY SHT2" + dejaPhotoCopiedSnapShot.getValue());




                }

                //System.out.println("WE GOT THE STUFF!" + photo.getImgPath());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        for (int i = 1; i < emailList.size(); i++){
            String currentEmail = emailList.get(i);
            currentEmail = currentEmail.replace(".", "");
            //Query queryRef = myFirebaseRef.orderByChild("user").equalTo(currentEmail);



        }
    }
}
