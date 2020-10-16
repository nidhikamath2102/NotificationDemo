package com.nidhikamath.notificationdemo.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {
    private Bitmap bitmap;
    private FetchImageCallback fetchImageCallback;

    public DownloadImageTask(FetchImageCallback fetchImageCallback) {
        this.fetchImageCallback = fetchImageCallback;
    }

    protected Bitmap doInBackground(String... voids) {
        bitmap = getBitmapfromUrl(voids[0]);
        return bitmap;
    }

    protected void onProgressUpdate(Integer... progress) {

    }

    protected void onPostExecute(Bitmap result) {
        if(bitmap!=null){
            fetchImageCallback.onSuccess(result);
        }else{
            fetchImageCallback.onFailed();
        }
    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}
