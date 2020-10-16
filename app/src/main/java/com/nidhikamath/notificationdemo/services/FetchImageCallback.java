package com.nidhikamath.notificationdemo.services;

import android.graphics.Bitmap;

public interface FetchImageCallback {
    void onSuccess(Bitmap bitmap);
    void onFailed();
}
