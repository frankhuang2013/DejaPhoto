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

    }

    @Test
    public void testgetCurrentPhoto() {

    }

    @Test
    public void testgetPhoto() {

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