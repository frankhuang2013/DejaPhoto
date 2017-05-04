package com.example.jeffphung.dejaphoto;


import android.Manifest;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{




    final long TIMER = 1; //60mins
    final float LOCATIONCHANGE = 152.4f; // 500 ft/152.4 meters
    PhotoList photoList; // PhotoList contains all photo in the app
    Photo currentPhoto; // photo that is dispalying now
    DejaVuMode dejaVuMode; // DejaVumode class
    PhotoLoaderTask photoLoader; // PhotoLoader class: load photo to app from photo
    // PhotoSorter class: sort the photo according to location and time
    PhotoSorterTask photoSorter;
    Location currentLocation;
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        /* toggleButtons */
        ToggleButton dButton = (ToggleButton) findViewById(R.id.dejaVuButton);
        dButton.setOnCheckedChangeListener(this);
        ToggleButton lButton = (ToggleButton) findViewById(R.id.locationButton);
        lButton.setOnCheckedChangeListener(this);
        ToggleButton timeDayButton = (ToggleButton) findViewById(R.id.timeDayButton);
        timeDayButton.setOnCheckedChangeListener(this);
        ToggleButton dayWeekButton = (ToggleButton) findViewById(R.id.dayWeekButton);
        dayWeekButton.setOnCheckedChangeListener(this);


        /* initialization */
        photoList = PhotoList.getPhotoListInstance();
        currentPhoto = new Photo();
        dejaVuMode = DejaVuMode.getDejaVuModeInstance();
        photoLoader= new PhotoLoaderTask(MainActivity.this);
        photoLoader.execute();

       /*
        Log.d("execute","after");
        TextView textV = (TextView) findViewById(R.id.text);
        textV.setText(photoList.size());
        photoSorter = new PhotoSorterTask(dejaVuMode,currentLocation);*/
        /* initialization */

        setContentView(R.layout.activity_main);


        //gets path to camera album photos
        File cameraDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString());

        //there are multiple folders here, but we are only interested in the first one (ie Camera)
        File[] files = cameraDir.listFiles();
        File finalCamDir = files[0];

        File[] camFiles = finalCamDir.listFiles();

        /* PRINTS OUT THE CAMERA LOCATION AND ALL SUBSEQUENT IMAGES
        System.out.println(cameraDir);
        for (File filess : camFiles){
            System.out.println(filess.getName());
        }
        */

        // should set the wallpaper to the first in our file array
        File firstImage = null;
        Bitmap bitmap = null;

        //if no images set a whatever image, else use first image.
        /*
        if (camFiles.length == 0){
            bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.defaultwhatever);

        }
        else{
            firstImage = camFiles[0];
            bitmap = BitmapFactory.decodeFile(firstImage.getAbsolutePath());
        }


        //sets wallpaper


        WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        try {
            myWallpaperManager.setBitmap(bitmap);
            Toast.makeText(MainActivity.this, "Wallpaper set", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Error setting wallpaper", Toast.LENGTH_SHORT).show();
        }
*/




        /* run location and time chacek in the background */
        /* haven't test yet */
        /*
        Intent locationIntent = new Intent(MainActivity.this,LocationService.class);
        startService(locationIntent);
        */
        /* run location and time chacek in the background */

        if (ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }

        Button cButton = (Button) findViewById(R.id.customAlbum);
        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(MainActivity.this, photoList.size()+"",
                        Toast.LENGTH_SHORT).show();
                AutoGPSTimer autoGPSTimer = new AutoGPSTimer(MainActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(MainActivity.this, photoList.size()+"",Toast.LENGTH_SHORT).show();

        switch (buttonView.getId()){
            case R.id.dejaVuButton:
                if (isChecked) {
                    // The toggle is enabled
                    System.out.println("deja enabled!");
                    Toast.makeText(MainActivity.this, "jjjjjj",Toast.LENGTH_SHORT).show();
                } else {
                    // The toggle is disabled
                    System.out.println("deja disabled!");
                    Toast.makeText(MainActivity.this, photoList.size()+"",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.locationButton:
                if (isChecked) {
                    // The toggle is enabled
                    System.out.println("location enabled!");
                } else {
                    // The toggle is disabled
                    System.out.println("location disabled!");
                }
                break;
            case R.id.timeDayButton:
                if (isChecked) {
                    // The toggle is enabled
                    System.out.println("time of day enabled!");
                } else {
                    // The toggle is disabled
                    System.out.println("time of day disabled!");
                }
                break;
            case R.id.dayWeekButton:
                if (isChecked) {
                    // The toggle is enabled
                    System.out.println("day of week enabled!");
                } else {
                    // The toggle is disabled
                    System.out.println("day of week disabled!");
                }
                break;
        }

    }

    public void createAlbum(View v){
        System.out.println("Creating Custom Album");

    }


    /* this class doesn't work correctly now, don't use it */

    protected class LocationService extends Service {

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startID) {
            Toast.makeText(LocationService.this, "Service Started", Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "Wallpaperjjjj", Toast.LENGTH_SHORT).show();
            LocationChange locationChange = new LocationChange();
            locationChange.start();;

            return super.onStartCommand(intent,flags,startID);

        }
        private class LocationChange {

            public void start() {

                LocationListener locationListener = new LocationListener() {

                    @Override
                    public void onLocationChanged(Location location) {
                        currentLocation = location;
                        photoSorter.execute();
                        Toast.makeText(MainActivity.this, "Wallpaperjjjj", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                    }
                };

            }

        }
    }
    /* this class doesn't work correctly now, don't use it */


    /* haven't test yet */

    private boolean isServiceRunning(Class<?> serviceClass){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        // Loop through the running services
        for(ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                // If the service is running then return true
                return true;
            }
        }
        return false;
    }

}
