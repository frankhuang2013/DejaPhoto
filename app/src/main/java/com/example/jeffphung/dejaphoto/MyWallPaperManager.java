package com.example.jeffphung.dejaphoto;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by huang on 5/12/2017.
 */

public class MyWallPaperManager {
    Context mContext;
    WallpaperManager myWallPaperManager;
    public MyWallPaperManager(Context mContext){
        myWallPaperManager = WallpaperManager.getInstance(mContext);
    }

    public void setWallPaper(Photo p){

        if(p != null) {
            String path = p.getImgPath();
            if(path != null) {
                // put behavior here:
                Log.i("myWallPaperManager", "start");
                Log.i("ImagePath",p.getImgPath());
                try {

                    if (path == null) {
                        Toast.makeText(mContext, "Error setting wallpaper", Toast.LENGTH_SHORT).show();
                    } else {

                        Log.i("start set", "start");
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        myWallPaperManager.setBitmap(bitmap);

                        Log.i("finish set img", "finished");
                    }
                    //Toast.makeText(mContext, "Wallpaper set", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(mContext, "Error setting wallpaper", Toast.LENGTH_SHORT).show();


                    // test
                }
            }
        }
        else if(PhotoList.getPhotoListInstance().size() == 0){
            setDefaultWalpaper();
        }


    }

    public void setDefaultWalpaper(){

        Log.i("start set", "start");
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.defaultwhatever);
            myWallPaperManager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
