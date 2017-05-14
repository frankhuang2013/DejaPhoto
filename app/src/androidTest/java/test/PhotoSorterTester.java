package test;

import android.support.test.rule.ActivityTestRule;

import com.example.jeffphung.dejaphoto.MainActivity;
import com.example.jeffphung.dejaphoto.PhotoSorterTask;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by kaijiecai on 5/1/17.
 */

public class PhotoSorterTester {
    PhotoSorterTask photoSorterTask;

    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp(){
        photoSorterTask = new PhotoSorterTask(mainActivity.getActivity());

    }

    @Test
    public void testWithinHours(){

        GregorianCalendar gregorianCalendar = new GregorianCalendar(2011,2,3,9,1,0);
        GregorianCalendar gregorianCalendar1 = new GregorianCalendar(2011,2,3,10,1,0);
        assertTrue(photoSorterTask.withinHours(gregorianCalendar,gregorianCalendar1));

        //more test cases

    }


    @Test
    public void testIsLocationClose(){


    }
    @Test
    public void testToSecond(){
        GregorianCalendar gregorianCalendar = new GregorianCalendar(2011,2,3,9,1,0);
        assertEquals(32460,photoSorterTask.calendarToSecond(gregorianCalendar));
        //more test cases


    }

    @Test
    public void testSameDayOfWeek(){
        //calendar constructor's month start at 0, which is 1 represents Feb
        GregorianCalendar gregorianCalendar = new GregorianCalendar(2016,1,4,9,1,0);
        GregorianCalendar gregorianCalendar1 = new GregorianCalendar(2017,4,11,9,1,0);
        assertTrue(photoSorterTask.sameDayOfWeek(gregorianCalendar,gregorianCalendar1));

        //more test cases

    }



}
