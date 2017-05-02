package com.example.jeffphung.dejaphoto;


import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        ToggleButton dButton = (ToggleButton) findViewById(R.id.dejaVuButton);
        dButton.setOnCheckedChangeListener(this);
        ToggleButton lButton = (ToggleButton) findViewById(R.id.locationButton);
        lButton.setOnCheckedChangeListener(this);
        ToggleButton timeDayButton = (ToggleButton) findViewById(R.id.timeDayButton);
        timeDayButton.setOnCheckedChangeListener(this);
        ToggleButton dayWeekButton = (ToggleButton) findViewById(R.id.dayWeekButton);
        dayWeekButton.setOnCheckedChangeListener(this);

        /* DejaVumode class */
        DejaVuMode dejaVuMode = new DejaVuMode();

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

        //if no images set a whatever image, else use first image.
        if (camFiles.length == 0){
            //TODO set default
        }
        else{
            firstImage = camFiles[0];
        }


        //sets wallpaper
        Bitmap bitmap = BitmapFactory.decodeFile(firstImage.getAbsolutePath());
        WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        try {
            myWallpaperManager.setBitmap(bitmap);
            Toast.makeText(MainActivity.this, "Wallpaper set", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Error setting wallpaper", Toast.LENGTH_SHORT).show();
        }




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

        switch (buttonView.getId()){
            case R.id.dejaVuButton:
                if (isChecked) {
                    // The toggle is enabled
                    System.out.println("deja enabled!");
                } else {
                    // The toggle is disabled
                    System.out.println("deja disabled!");
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

}
