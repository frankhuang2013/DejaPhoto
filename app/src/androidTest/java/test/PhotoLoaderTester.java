package test;


import android.location.Location;
import android.support.test.rule.ActivityTestRule;

import com.example.jeffphung.dejaphoto.MainActivity;
import com.example.jeffphung.dejaphoto.PhotoLoaderTask;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by kaijiecai on 5/1/17.
 */

public class PhotoLoaderTester {

    PhotoLoaderTask photoLoaderTask;

    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);


    @Before
    public void setup(){
        photoLoaderTask = new PhotoLoaderTask(mainActivity.getActivity());
    }

    @Test
    public void testToBoolean() {
        assertTrue(photoLoaderTask.toBoolean("true"));
        //more test cases

    }

    @Test
    public void testToGregorianCalendar(){
        // calendar constructor month start at 0, 8 means Sept
        GregorianCalendar calendar = new GregorianCalendar(
                2015,8,14,2,28,0);
        assertEquals(calendar,photoLoaderTask.toGregorianCalendar("2015:9:14 2:28:0"));

        //more test cases
    }

    @Test
    public void testToDouble(){
        DecimalFormat twoDForm = new DecimalFormat("#.####");

        assertEquals(new Double(-10.1944),
                Double.valueOf(twoDForm.format(photoLoaderTask.toDouble("10/1,10/1,100/100","W"))));

        //more test cases
        //google DMS to decimal convertor


    }

    @Test
    public void testToLocation(){
        final double DELTA = 1e-15;
        Location location = new Location("location");
        location.setLongitude(photoLoaderTask.toDouble("10/1,10/1,100/100","E"));
        location.setLatitude(photoLoaderTask.toDouble("10/1,10/1,100/100","N"));

        assertEquals(location.getLatitude(),
                photoLoaderTask.toLocation("10/1,10/1,100/100","E","10/1,10/1,100/100","N").getLatitude()
                ,DELTA);

        //more test cases

    }

    @Test
    public void testToLocationName(){
        Location location = new Location("location");
        location.setLatitude(32.715736);
        location.setLongitude(-117.161087);
        assertEquals("San Diego",photoLoaderTask.toLocationName(location));

        //more test cases

    }
}
