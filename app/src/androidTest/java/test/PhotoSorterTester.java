package test;

import com.example.jeffphung.dejaphoto.PhotoSorterTask;

import org.junit.Test;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertTrue;

/**
 * Created by kaijiecai on 5/1/17.
 */

public class PhotoSorterTester {

    @Test
    public void testWithinHours(){
        PhotoSorterTask photoSorterTask = new PhotoSorterTask();
        GregorianCalendar gregorianCalendar = new GregorianCalendar(2011,2,3,9,1,0);
        GregorianCalendar gregorianCalendar1 = new GregorianCalendar(2011,2,3,10,1,0);
        assertTrue(photoSorterTask.withinHours(gregorianCalendar,gregorianCalendar1));

    }


    @Test
    public void testToSecond(){

    }

}
