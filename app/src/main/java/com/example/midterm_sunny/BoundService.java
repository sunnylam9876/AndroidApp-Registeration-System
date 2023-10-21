package com.example.midterm_sunny;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/* This part is for time service */

public class BoundService extends Service {

    public class MyLocalBinder extends Binder {
        BoundService getService() {
            return BoundService.this;
        }
    }
    private final IBinder myBinder = new MyLocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss MM/dd/yyy", Locale.US);
        return (dateFormat.format(new Date()));
    }

    /*public void stopService() {
        stopSelf();
    }*/


}
//how to stop the BoundService?