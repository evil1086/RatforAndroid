package com.golden.android.broadcastreceiver;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static com.google.android.gms.wearable.DataMap.TAG;

public class GetLocation extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    double latitude, longitude;
    String reciverEmail = "vinod.pawar713@gmail.com";
    String senderMail = "vinod.pawar713@gmail.com";
    String Pass = "samsung1086";
    private GoogleApiClient mGoogleApiClient;
    private android.location.Location mLocation;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;

    public GetLocation() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        reciverEmail = getApplicationContext().getString(R.string.reciveremailid);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mGoogleApiClient.connect();

        //  UrlMaps();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, "Manifest.permission.ACCESS_FINE_LOCATION") != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, "Manifest.permission.ACCESS_COARSE_LOCATION") != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return;
        }
        startLocationUpdates();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation == null) {
            startLocationUpdates();
        }
        if (mLocation != null) {
            latitude = mLocation.getLatitude();
            longitude = mLocation.getLongitude();
            UrlMaps();

        } else {
            // Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(0)
                .setFastestInterval(0);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, "Manifest.permission.ACCESS_FINE_LOCATION") != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, "Manifest.permission.ACCESS_COARSE_LOCATION") != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        mGoogleApiClient.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onLocationChanged(android.location.Location location) {

    }

    public void UrlMaps() {
        mGoogleApiClient.connect();
        final String mapsURL = "http://www.google.com/maps?q=" + latitude + "," + longitude;


        new Thread(new Runnable() {
            @Override
            public void run() {
                GMailSender sender = new GMailSender(senderMail, Pass);
                try {
                    Log.i(TAG, "sending email");
                    sender.sendMail("Victims Location", mapsURL, senderMail, reciverEmail);
                } catch (Exception e) {
                    Log.e(TAG, "onReceive: " + e.getMessage());
                    // e.printStackTrace();
                }
            }
        }).start();


        // return mapsURL;
    }


}
