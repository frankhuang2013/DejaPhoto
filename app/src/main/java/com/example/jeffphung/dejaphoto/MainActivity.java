package com.example.jeffphung.dejaphoto;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.support.annotation.NonNull;

import android.support.v4.content.FileProvider;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import java.util.ArrayList;
import java.util.Arrays;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.api.client.googleapis.auth.oauth2.GoogleBrowserClientRequestUrl;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.people.v1.People;
import com.google.api.services.people.v1.PeopleScopes;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


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
    final String dejaPhoto = "DejaPhoto";
    final String dejaPhotoCopied = "DejaPhotoCopied";

    File image;

    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;

    private List<String> emailList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        emailList = new ArrayList<>();

        Button waitTimeButton = (Button) findViewById(R.id.waitTimeButton);
        waitTimeText = (EditText) findViewById(R.id.waitTimeEditText);
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
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestServerAuthCode("233552778796-7fm2m9cd3h8cjforhuo8p8q0v5sse786.apps.googleusercontent.com")
                .requestScopes(new Scope(PeopleScopes.CONTACTS), new Scope(PeopleScopes.USERINFO_EMAIL))
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        //TODO
                    }
                }/* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton signInButton = (SignInButton) findViewById(R.id.signin);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();
                Toast.makeText(v.getContext(), "YOU HAVE NOW SIGNED IN", Toast.LENGTH_SHORT).show();

            }
        });


        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        File wallpaperDirectory = new File(path.toString() + "/adddd");
        wallpaperDirectory.mkdirs();

        /* initialization */
        photoList = new PhotoList();
        PhotoListManager.getPhotoListManagerInstance().setPhotoList(photoList);
        dejaVuMode = DejaVuMode.getDejaVuModeInstance();

        photoList.setContext(this);
        photoLoader = new PhotoLoaderTask(MainActivity.this);
        photoLoader.execute();

          /* toggleButtons */
        ToggleButton dButton = (ToggleButton) findViewById(R.id.sharebtn);
        dButton.setOnCheckedChangeListener(this);
        ToggleButton lButton = (ToggleButton) findViewById(R.id.friendbtn);
        lButton.setOnCheckedChangeListener(this);
        ToggleButton timeDayButton = (ToggleButton) findViewById(R.id.mebtn);
        timeDayButton.setOnCheckedChangeListener(this);


        CreateDirs.createDir();


    }


    public void pickerClicked(View v) {

        Intent newIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //newIntent.setType("image/*");
        //newIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        //newIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(newIntent, photoPickerID);

    }


    public void takePhotoClicked(View v) throws IOException {


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File[] DCIMFiles = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).listFiles();
            int i = 0;
            for (i = 0; i < DCIMFiles.length; i++) {
                System.out.println("----------"+DCIMFiles[i].toString()+"");
                System.out.println(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+dejaPhoto);
                if (DCIMFiles[i].toString().equals(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+"/"+dejaPhoto)){
                    break;
                }
            }
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).listFiles()[i];
            System.out.println("----------"+storageDir+"");
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents

            Uri photoURI = FileProvider.getUriForFile(this,"com.example.android.fileprovider",image);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

            startActivityForResult(takePictureIntent, takePhotoID);
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && requestCode == photoPickerID){
            copyImages(data,dejaPhotoCopied);
        }
        else if(resultCode == RESULT_OK && requestCode == takePhotoID){
            final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            final Uri contentUri = Uri.fromFile(image);
            scanIntent.setData(contentUri);
            sendBroadcast(scanIntent);




        }

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    public void copyImages(Intent data, String album){

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() +
                "/"+album+"/" + "copied" + Calendar.getInstance().getTimeInMillis() + ".JPG");

        FileChannel source = null;
        FileChannel destination = null;

        Uri pickedImage = data.getData();
        //String imagePath = getRealPathFromURI(getActivity(),uri);
        // Let's read picked image path using content resolver


        String[] filePath = {MediaStore.Images.Media.DATA};

        Log.i("---------", filePath[0] + "");

        Log.i("---------",filePath[0]+"--"+pickedImage);

        Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
        cursor.moveToFirst();
        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
        try {
            Log.i("-------",imagePath+"");
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
            } finally {
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
        Toast.makeText(MainActivity.this, photoList.size() + "", Toast.LENGTH_SHORT).show();


        switch (buttonView.getId()) {
            case R.id.sharebtn:
                if (isChecked) {
                    // The toggle is enabled
                    ShareManager sharer = new ShareManager();
                    sharer.share(emailList, photoList);

                } else {
                    // The toggle is disabled
                    //TODO: for all user photos, setShare(false);
                }
                break;
            case R.id.friendbtn:
                if (isChecked) {
                    //TODO: if share == true and user != this user, then add to friends photolist (???)
                    //TODO: ^^need to keep karma, location fields etc. so get photos

                } else {
                    // The toggle is disabled
                    //TODO: implement with photolist???
                }
                break;
            case R.id.mebtn:
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
                break;

        }

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("signintag", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String name = acct.getDisplayName();

            // This is what we need to exchange with the server.
            String authcode = acct.getServerAuthCode();

            new PeoplesAsync().execute(acct.getServerAuthCode());


            System.out.println("the name is: " + name);


            /*
            try{
                setUp(authcode);
            }
            catch(IOException e){

            }
        */
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.signin).setVisibility(View.GONE);
        } else {
            findViewById(R.id.signin).setVisibility(View.VISIBLE);
        }
    }
    class PeoplesAsync extends AsyncTask<String, Void, List<String>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<String> doInBackground(String... params) {

            List<String> nameList = new ArrayList<>();

            try {
                People peopleService = PeopleHelper.setUp(MainActivity.this, params[0]);

                ListConnectionsResponse response = peopleService.people().connections()
                        .list("people/me")
                        // This line's really important! Here's why:
                        // http://stackoverflow.com/questions/35604406/retrieving-information-about-a-contact-with-google-people-api-java
                        .setRequestMaskIncludeField("person.emailAddresses")
                        .execute();
                List<Person> connections = response.getConnections();

                for (Person person : connections) {
                    if (!person.isEmpty()) {

                        List<EmailAddress> emailAddresses = person.getEmailAddresses();


                        if (emailAddresses != null)
                            for (EmailAddress emailAddress : emailAddresses) {
                                emailList.add(emailAddress.getValue());
                        }

                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return nameList;
        }
    }
}