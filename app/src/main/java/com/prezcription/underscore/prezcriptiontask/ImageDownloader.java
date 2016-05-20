package com.prezcription.underscore.prezcriptiontask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ImageDownloader extends AsyncTask<String , String ,Bitmap> {

    private ProgressBar pr;
    private ImageView image;
    private Bitmap errorImage;
    private static final int MAX_IMAGE_WIDTH = 640;
    private static final int MAX_IMAGE_HEIGHT = 480;

    ImageDownloader(ProgressBar pr,ImageView image,Bitmap errorImage) {
        this.pr = pr;
        this.image = image;
        this.errorImage = errorImage;

    }

    @Override
    protected void onPreExecute() {
        pr.setVisibility(View.VISIBLE);
        pr.setProgress(0);
        image.setImageBitmap(null);
    }

    @Override
    protected Bitmap doInBackground(String... url) {

        String imageURl = url[0];
        Bitmap bitmap = null;
        int height =0 ,width = 0;
        InputStream input = null;
        OutputStream output = null;
        String[] temp = imageURl.split("/");
        String filepath = url[1] +"/"+temp[temp.length-1];

        Log.i("path", filepath);

        byte[] data = new byte[1024];
        int count = 0 ,total = 0,len;
        URL u = null;
        URLConnection conection = null;

        try {
            u = new URL(imageURl);
            conection = u.openConnection();
            conection.connect();
            output = new FileOutputStream(filepath);
            len = conection.getContentLength();
            input = conection.getInputStream();
            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data,0,count);
                publishProgress("" + (int) ((total * 100) / len));
            }
            input.close();
            output.close();
        } catch (MalformedURLException e) {
            Log.i("info","malformed URL");
            return errorImage;
        } catch (FileNotFoundException e) {
            Log.i("info", "file not found");
            return errorImage;
        } catch (IOException e) {
            Log.i("info", "io exception");
            return errorImage;

        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath,options);
        height = options.outHeight;
        width = options.outWidth;

        Log.i("original height" , Integer.toString(height) + "  " + Integer.toString(width));
        float scale = 1;
        float hs,vs;

        hs = height/MAX_IMAGE_HEIGHT;
        vs = width/MAX_IMAGE_WIDTH;

        if(hs <= scale && vs <= scale){

        }else if(hs > scale && vs > scale){
            if(hs > vs){
                scale = hs;
            }else{
                scale = vs;
            }
        }else if(hs > scale){
            scale = hs;
        }else{
            scale = vs;
        }

        Log.i("new scale" , Integer.toString((int)scale));
        options = new BitmapFactory.Options();
        options.inSampleSize = (int)scale;

        bitmap = BitmapFactory.decodeFile(filepath,options);

        return Bitmap.createScaledBitmap(bitmap,MAX_IMAGE_WIDTH,MAX_IMAGE_HEIGHT,true);
    }


    protected void onProgressUpdate(String... progress) {
        pr.setProgress(Integer.parseInt(progress[0]));
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        image.setImageBitmap(bitmap);
        pr.setProgress(100);
        pr.setVisibility(View.GONE);
    }


}


