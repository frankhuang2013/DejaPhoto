package test;


import com.example.jeffphung.dejaphoto.PhotoLoaderTask;

import org.junit.Test;

import java.util.GregorianCalendar;

import static junit.framework.Assert.assertEquals;

/**
 * Created by kaijiecai on 5/1/17.
 */

public class PhotoLoaderTester {


    @Test
    public void test() {
        PhotoLoaderTask photoLoaderTask = new PhotoLoaderTask();
        assertEquals("1",photoLoaderTask.getA());
    }


    @Test
    public void test1(){
        GregorianCalendar calendar = new GregorianCalendar(
                2015,9,14,2,28,0);
        PhotoLoaderTask photoLoaderTask = new PhotoLoaderTask();
        GregorianCalendar calendar1 = photoLoaderTask.getCalendar();
        assertEquals(calendar.YEAR, calendar1.YEAR);
        assertEquals(calendar.MONTH, calendar1.MONTH);
        assertEquals(calendar.DAY_OF_WEEK, calendar1.DAY_OF_WEEK);
        assertEquals(calendar.HOUR, calendar1.HOUR);
        assertEquals(calendar.MINUTE, calendar1.MINUTE);

    }

    @Test
    public void test2(){

    }
}
