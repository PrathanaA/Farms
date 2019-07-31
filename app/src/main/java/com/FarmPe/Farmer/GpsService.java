package com.FarmPe.Farmer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class GpsService extends Service {

    int count;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Thread i=new Thread(new Runnable() {
            @Override
            public void run() {
                count++;
                System.out.println("llllllllllllllllllllllllllllllllllllll"+count);


            }
        });
        i.start();


        return super.onStartCommand(intent, flags, startId);



    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
