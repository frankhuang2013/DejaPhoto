package test;


import com.example.jeffphung.dejaphoto.Photo;
import com.example.jeffphung.dejaphoto.PhotoList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by kaijiecai on 5/1/17.
 */

public class PhotoListTester {

    PhotoList photoList;

    @Before
    public void setUp() { photoList = PhotoList.getPhotoListInstance(); }

    @Test
    public void testisAllowed() {
        //Testing value from constructor
        assertTrue(photoList.isAllowed());

        //Testing setter method
        photoList.setAllowed(false);
        assertTrue(!photoList.isAllowed());
        photoList.setAllowed(true);
        assertTrue(photoList.isAllowed());
    }

    @Test
    public void testnext() {
        //next on an empty list
        assertEquals(null, photoList.next());

        //next on a list with one photo, index at 0
        Photo photoOne = new Photo("/storage/sdcard/DCIM/Camera/img1.JPG");
        photoList.add(photoOne);
        assertEquals(photoOne,photoList.next());
        assertEquals(0, photoList.getIndex());

        //index still at 0, next on a list with two photos
        Photo photoTwo = new Photo("/storage/sdcard/DCIM/Camera/img2.JPG");
        photoList.add(photoTwo);
        assertEquals(photoTwo, photoList.next());
        assertEquals(1, photoList.getIndex());

        //index at 1 now, next on two-photo list
        assertEquals(photoOne, photoList.next());
        assertEquals(0, photoList.getIndex());
    }

    @Test
    public void testprevious() {
        //previous on an empty list
        assertEquals(null, photoList.previous());

        //previous on a list with one photo, index at 0
        Photo photoOne = new Photo("/storage/sdcard/DCIM/Camera/img1.JPG");
        photoList.add(photoOne);
        assertEquals(photoOne,photoList.previous());
        assertEquals(0, photoList.getIndex());

        //index still at 0, previous on a list with two photos
        Photo photoTwo = new Photo("/storage/sdcard/DCIM/Camera/img2.JPG");
        photoList.add(photoTwo);
        assertEquals(photoTwo, photoList.previous());
        assertEquals(1, photoList.getIndex());

        //index at 1 now, previous on two-photo list
        assertEquals(photoOne, photoList.previous());
        assertEquals(0, photoList.getIndex());
    }

    @Test
    public void testremoveCurrentPhoto() {
        //list of one, index is at 0
        Photo photoOne = new Photo("/storage/sdcard/DCIM/Camera/img1.JPG");
        photoList.add(photoOne);
        assertEquals(null,photoList.removeCurrentPhoto());
        assertEquals(0,photoList.size());
        assertTrue(photoOne.isReleased());

        //list of three, remove last photo
        photoList.add(photoOne);
        Photo photoTwo = new Photo("/storage/sdcard/DCIM/Camera/img2.JPG");
        photoList.add(photoTwo);
        Photo photoThree = new Photo("/storage/sdcard/DCIM/Camera/img3.JPG");
        photoList.add(photoThree);
        photoList.setIndex(2);
        assertEquals(photoOne,photoList.removeCurrentPhoto());
        assertEquals(2,photoList.size());
        assertTrue(photoThree.isReleased());
        //from middle
        photoList.add(photoThree);
        photoList.setIndex(1);
        assertEquals(photoThree,photoList.removeCurrentPhoto());
        assertEquals(2,photoList.size());
        assertTrue(photoTwo.isReleased());
    }

    @Test
    public void testgetCurrentPhoto() {
        //empty list
        assertEquals(null,photoList.getCurrentPhoto());

        //4-photo list
        photoList.add(new Photo("/storage/sdcard/DCIM/Camera/img1.JPG"));
        photoList.add(new Photo("/storage/sdcard/DCIM/Camera/img2.JPG"));
        Photo photoCurr = new Photo("/storage/sdcard/DCIM/Camera/img3.JPG");
        photoList.add(photoCurr);
        photoList.add(new Photo("/storage/sdcard/DCIM/Camera/img4.JPG"));
        photoList.setIndex(2);
        assertEquals(photoCurr,photoList.getCurrentPhoto());
    }

    @Test
    public void testgetPhoto() {
        //empty list, invalid index
        assertEquals(null,photoList.getPhoto(0));

        //4-photo list
        photoList.add(new Photo("/storage/sdcard/DCIM/Camera/img1.JPG"));
        Photo photoCurr = new Photo("/storage/sdcard/DCIM/Camera/img2.JPG");
        photoList.add(photoCurr);
        photoList.add(new Photo("/storage/sdcard/DCIM/Camera/img3.JPG"));
        assertEquals(photoCurr,photoList.getPhoto(1));
    }

    @Test
    public void testsort() {
        //add photos
        Photo photoOne = new Photo("/storage/sdcard/DCIM/Camera/img2.JPG");
        photoList.add(photoOne);
        Photo photoTwo = new Photo("/storage/sdcard/DCIM/Camera/img2.JPG");
        photoList.add(photoTwo);
        Photo photoThree = new Photo("/storage/sdcard/DCIM/Camera/img2.JPG");
        photoList.add(photoThree);
        Photo photoFour = new Photo("/storage/sdcard/DCIM/Camera/img2.JPG");
        photoList.add(photoFour);
        //making sure photos are in original order
        assertEquals(photoOne,photoList.getPhoto(0));
        assertEquals(photoTwo,photoList.getPhoto(1));
        assertEquals(photoThree,photoList.getPhoto(2));
        assertEquals(photoFour,photoList.getPhoto(3));
        //add points
        photoOne.setPoints(0);
        photoTwo.setPoints(30);
        photoThree.setPoints(10);
        photoFour.setPoints(20);
        //call sort
        photoList.sort();
        assertEquals(photoTwo,photoList.getPhoto(0));
        assertEquals(photoFour,photoList.getPhoto(1));
        assertEquals(photoThree,photoList.getPhoto(2));
        assertEquals(photoOne,photoList.getPhoto(3));
    }

    @Test
    public void testsize() {
        //size from constructor
        assertEquals(0,photoList.size());
        //size after adding a photo
        photoList.add(new Photo("/storage/sdcard/DCIM/Camera/img1.JPG"));
        assertEquals(1,photoList.size());
    }
}