package com.example.jeffphung.dejaphoto;


import android.content.Intent;
import android.os.Bundle;
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


public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    PhotoList photoList; // PhotoList contains all photo in the app
    DejaVuMode dejaVuMode; // DejaVumode class
    PhotoLoaderTask photoLoader; // PhotoLoader class: load photo to app from photo
    // PhotoSorter class: sort the photo according to location and time

    EditText waitTimeText;
    String waitTimeStr = "";
    int waitTimeInt = -1;
    Intent intent;

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

        /* initialization */
        photoList = PhotoList.getPhotoListInstance();
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
                    Log.i("dejaVuButton","button pressed");
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


}
