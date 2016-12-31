package com.golden.android.broadcastreceiver;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class BroadcastNewSms extends AppCompatActivity {

    String TAG ="Broadcastnewsms";
//        String URL = Resources.getSystem().getString(R.string.reciveremailid);

    //String prefsKeyTimeBetweenNotifications = getString(R.string.reciveremailid);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
      //   String URL = Resources.getSystem().getString(R.string.reciveremailid);
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS,};
        String[] Write = {Manifest.permission.WRITE_EXTERNAL_STORAGE, };

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        if(hasPermissions(this, Write)) {

        startService(new Intent(this, BackgroundService.class));

        }




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
