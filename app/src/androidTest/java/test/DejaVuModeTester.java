package test;

import com.example.jeffphung.dejaphoto.DejaVuMode;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by kaijiecai on 4/29/17.
 *
 * Test DejaVuMode class
 */

public class DejaVuModeTester {


    DejaVuMode dejaVuMode;

    @Before
    public void setUp(){
        dejaVuMode = DejaVuMode.getDejaVuModeInstance();
    }

    @Test
    public void testSetDejaModeOn(){
        //check initiial status
        assertTrue(dejaVuMode.isDejaVuModeOn());
        assertTrue(dejaVuMode.isDayModeOn());
        assertTrue(dejaVuMode.isLocationModeOn());
        assertTrue(dejaVuMode.isTimeModeOn());


        //turn off dejaVuMode
        dejaVuMode.setDejaVuModeOn(false);
        assertTrue(!dejaVuMode.isDejaVuModeOn());
        assertTrue(!dejaVuMode.isDayModeOn());
        assertTrue(!dejaVuMode.isLocationModeOn());
        assertTrue(!dejaVuMode.isTimeModeOn());

        //turn on dejaVuMode
        dejaVuMode.setDejaVuModeOn(true);
        assertTrue(dejaVuMode.isDejaVuModeOn());
        assertTrue(dejaVuMode.isDayModeOn());
        assertTrue(dejaVuMode.isLocationModeOn());
        assertTrue(dejaVuMode.isTimeModeOn());
    }

    @Test
    public void test1(){
        //turn off location mode
        dejaVuMode.setLocationModeOn(false);
        assertTrue(dejaVuMode.isDejaVuModeOn());
        assertTrue(dejaVuMode.isDayModeOn());
        assertTrue(!dejaVuMode.isLocationModeOn());
        assertTrue(dejaVuMode.isTimeModeOn());

        //turn off day mode
        dejaVuMode.setDayModeOn(false);
        assertTrue(dejaVuMode.isDejaVuModeOn());
        assertTrue(!dejaVuMode.isDayModeOn());
        assertTrue(!dejaVuMode.isLocationModeOn());
        assertTrue(dejaVuMode.isTimeModeOn());

        //turn off time mode
        dejaVuMode.setTimeModeOn(false);
        assertTrue(!dejaVuMode.isDejaVuModeOn());
        assertTrue(!dejaVuMode.isDayModeOn());
        assertTrue(!dejaVuMode.isLocationModeOn());
        assertTrue(!dejaVuMode.isTimeModeOn());


        //turn on location mode
        dejaVuMode.setLocationModeOn(true);
        assertTrue(dejaVuMode.isDejaVuModeOn());
        assertTrue(!dejaVuMode.isDayModeOn());
        assertTrue(dejaVuMode.isLocationModeOn());
        assertTrue(!dejaVuMode.isTimeModeOn());

        //turn on day mode
        dejaVuMode.setDayModeOn(true);
        assertTrue(dejaVuMode.isDejaVuModeOn());
        assertTrue(dejaVuMode.isDayModeOn());
        assertTrue(dejaVuMode.isLocationModeOn());
        assertTrue(!dejaVuMode.isTimeModeOn());

        //turn on time mode
        dejaVuMode.setTimeModeOn(true);
        assertTrue(dejaVuMode.isDejaVuModeOn());
        assertTrue(dejaVuMode.isDayModeOn());
        assertTrue(dejaVuMode.isLocationModeOn());
        assertTrue(dejaVuMode.isTimeModeOn());
    }
}
