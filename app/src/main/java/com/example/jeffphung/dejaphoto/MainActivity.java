package com.example.jeffphung.dejaphoto;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    PhotoList photoList; // PhotoList contains all photo in the app
    DejaVuMode dejaVuMode; // DejaVumode class
    PhotoLoaderTask photoLoader; // PhotoLoader class: load photo to app from photo
    // PhotoSorter class: sort the photo according to location and time

    EditText waitTimeText;
    String waitTimeStr = "";
    int waitTimeInt = -1;
    Intent intent;

    final int photoPickerID = 1;
    final int takePhotoID = 2;

    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button waitTimeButton = (Button)findViewById(R.id.waitTimeButton);
        waitTimeText = (EditText)findViewById(R.id.waitTimeEditText);
        intent = new Intent(this, AutoChangeWallPaper.class);
        waitTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                waitTimeInt = -1;
                waitTimeStr = waitTimeText.getText().toString();
                boolean isNum = true;
                for (int i = 0; i < waitTimeStr.length(); i++) {
                    if (!Character.isDigit(waitTimeStr.charAt(i))) {
                        i = waitTimeStr.length();
                        isNum = false;
                    }
                }
                if (isNum) {
                    waitTimeInt = Integer.parseInt(waitTimeStr);
                }
                intent.putExtra("waitTimeInt", waitTimeInt);
                Toast.makeText(v.getContext(), "Set wait time to " + waitTimeInt + " seconds", Toast.LENGTH_SHORT).show();
                startService(intent);

            }
        });

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        File wallpaperDirectory = new File(path.toString()+"/adddd");
        wallpaperDirectory.mkdirs();
        /* initialization */
        photoList = new PhotoList();
        PhotoListManager.getPhotoListManagerInstance().setPhotoList(photoList);
        dejaVuMode = DejaVuMode.getDejaVuModeInstance();

        photoList.setContext(this);
        photoLoader = new PhotoLoaderTask(MainActivity.this);
        photoLoader.execute();

          /* toggleButtons */
        ToggleButton dButton = (ToggleButton) findViewById(R.id.dejaVuButton);
        dButton.setOnCheckedChangeListener(this);
        ToggleButton lButton = (ToggleButton) findViewById(R.id.locationButton);
        lButton.setOnCheckedChangeListener(this);
        ToggleButton timeDayButton = (ToggleButton) findViewById(R.id.timeDayButton);
        timeDayButton.setOnCheckedChangeListener(this);
        ToggleButton dayWeekButton = (ToggleButton) findViewById(R.id.dayWeekButton);
        dayWeekButton.setOnCheckedChangeListener(this);


        CreateDirs.createDir();
    }


    public void pickerClicked(View v){

        Intent newIntent = new Intent();
        newIntent.setType("image/*");
        newIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        newIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(newIntent, photoPickerID);

    }
    public void takePhotoClicked(View v){

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, takePhotoID);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && requestCode == photoPickerID){
            copyImages(data);
        }
        else if(resultCode == RESULT_OK && requestCode == takePhotoID){
            copyImages(data);
        }
    }

    public void copyImages(Intent data){
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() +
                "/DejaPhotoCopied/" + "copied" + Calendar.getInstance().getTimeInMillis() + ".JPG");

        FileChannel source = null;
        FileChannel destination = null;

        Uri pickedImage = data.getData();
        //String imagePath = getRealPathFromURI(getActivity(),uri);
        // Let's read picked image path using content resolver


        String[] filePath = {MediaStore.Images.Media.DATA};
        Log.i("---------",filePath[0]+"");
        Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
        cursor.moveToFirst();
        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
        try {
            source = new FileInputStream(new File(imagePath)).getChannel();
            destination = new FileOutputStream(file).getChannel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (destination != null && source != null) {
            try {
                destination.transferFrom(source, 0, source.size());
                final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                final Uri contentUri = Uri.fromFile(file);
                scanIntent.setData(contentUri);
                sendBroadcast(scanIntent);

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                cursor.close();
            }
        }
        if (source != null) {
            try {
                source.close();
                destination.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        Toast.makeText(MainActivity.this, photoList.size()+"",Toast.LENGTH_SHORT).show();


        switch (buttonView.getId()){
            case R.id.dejaVuButton:
                if (isChecked) {
                    // The toggle is enabled
                    Log.i("dejaVu","button enabled");
                    dejaVuMode.setDejaVuModeOn(true);

                } else {
                    // The toggle is disabled
                    Log.i("dejaVu","button disabled");
                    dejaVuMode.setDejaVuModeOn(false);
                }
                break;
            case R.id.locationButton:
                if (isChecked) {
                    // The toggle is enabled
                    Log.i("location","enabled");
                    dejaVuMode.setLocationModeOn(true);

                } else {
                    // The toggle is disabled
                    Log.i("location","disabled");
                    dejaVuMode.setLocationModeOn(false);
                }
                break;
            case R.id.timeDayButton:
                if (isChecked) {
                    // The toggle is enabled
                    Log.i("timeDay","enabled");
                    dejaVuMode.setTimeModeOn(true);
                } else {
                    // The toggle is disabled
                    Log.i("timeDay","disabled");
                    dejaVuMode.setTimeModeOn(false);
                }
                break;
            case R.id.dayWeekButton:
                if (isChecked) {
                    // The toggle is enabled
                    Log.i("day of week","enabled!");
                    dejaVuMode.setDayModeOn(true);
                } else {
                    // The toggle is disabled
                    Log.i("day of week", "disabled");
                    dejaVuMode.setDayModeOn(false);
                }
                break;



        }

    }

    public void createAlbum(View v){
        Log.i("creating custom album","Creating Custom Album");


    }


}
