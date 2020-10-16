package com.nidhikamath.notificationdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.nidhikamath.notificationdemo.services.DownloadImageTask;
import com.nidhikamath.notificationdemo.services.FetchImageCallback;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText title, description;
    private Button send, cancel;
    private ImageView icon;
    private DownloadImageTask downloadImageTask = null;
    private Bitmap bitMap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        icon = findViewById(R.id.icon);
        send = findViewById(R.id.send);
        cancel = findViewById(R.id.cancel);

        downloadImageTask = new DownloadImageTask(new FetchImageCallback(){

            @Override
            public void onSuccess(Bitmap bitmap) {
                icon.setImageBitmap(bitmap);
                bitMap = bitmap;
            }

            @Override
            public void onFailed() {

            }
        });
        downloadImageTask.execute(Constants.url);


    }

    public void sendNotification(View view) {
        send.setEnabled(false);
        cancel.setEnabled(true);
        String ttl = title.getText().toString().trim();
        String desc = description.getText().toString().trim();
        if(bitMap!=null){
            Notification.getInstance(getApplicationContext()).setNotification(ttl, desc, bitMap);
        }

    }

    public void cancelNotification(View view) {
        send.setEnabled(true);
        cancel.setEnabled(false);
        Notification.getInstance(getApplicationContext()).cancelNotification();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Notification.getInstance(getApplicationContext()).onDestroy();
        downloadImageTask.cancel(true);
    }
}