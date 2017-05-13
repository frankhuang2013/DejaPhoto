package com.example.jeffphung.dejaphoto;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by huang on 5/12/2017.
 */

public class MyWallPaperManager {
    Context mContext;
    WallpaperManager myWallPaperManager;
    int textSize = 50;

    public MyWallPaperManager(Context mContext){
        myWallPaperManager = WallpaperManager.getInstance(mContext);
        this.mContext = mContext;
    }

    public void setWallPaper(Photo p){



        if(PhotoList.getPhotoListInstance().isAllowed()) {
            if (p != null) {
                String path = p.getImgPath();
                if (path != null) {
                    // put behavior here:
                    Log.i("myWallPaperManager", "start");
                    Log.i("ImagePath", p.getImgPath());
                    try {

                        if (path == null) {
                            Toast.makeText(mContext, "Error setting wallpaper", Toast.LENGTH_SHORT).show();
                        } else {


                            Log.i("start set", "start");
                            Bitmap bitmap = BitmapFactory.decodeFile(path);
                            Log.i("BITMAP", bitmap + "");
                            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

                            Canvas c = new Canvas(bitmap);
                            Paint textPaint = new Paint();
                            textPaint.setTextSize(textSize);
                            textPaint.setColor(Color.WHITE);
                            if (p.getCityName() != null) {
                                WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                                Display display = wm.getDefaultDisplay();
                                Point size = new Point();
                                display.getSize(size);
                                int phoneWidth = size.x;
                                int phoneHeight = size.y;
                                Log.i("PHONE SIZE", phoneWidth + " " + phoneHeight);
                                Log.i("City name ", p.getCityName());
                                c.drawText(p.getCityName(), p.getWidth() / 2 - phoneWidth / 2 + 50, p.getHeight() / 2 + phoneHeight / 2 - 150, textPaint);
                            }

                            myWallPaperManager.setBitmap(bitmap);


                            Log.i("finish set img", "finished");
                        }
                        //Toast.makeText(mContext, "Wallpaper set", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(mContext, "Error setting wallpaper", Toast.LENGTH_SHORT).show();


                        // test
                    }
                }
            } else if (PhotoList.getPhotoListInstance().size() == 0) {
                setDefaultWallpaper();
            }
        }
        else{
            Toast.makeText(mContext,"Sorting photos now, try later", Toast.LENGTH_SHORT).show();
        }

    }

    public void setDefaultWallpaper(){

        Log.i("start set", "start");
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.defaultwhatever);
            myWallPaperManager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
