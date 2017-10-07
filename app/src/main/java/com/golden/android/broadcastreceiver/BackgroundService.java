package com.golden.android.broadcastreceiver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Timer;

/**
 * Created by x on 3/7/2016.
 */
public class BackgroundService extends Service {
        //heloo
    // constant
    public static final long NOTIFY_INTERVAL = 150 * 1000; // 10 seconds

    // run on another Thread to avoid crash
    //public Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // cancel if already existed
        if(mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }

        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(this), 0, NOTIFY_INTERVAL);

    }

}
