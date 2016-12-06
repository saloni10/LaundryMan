package com.example.saloni.laundryman;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by saloni on 27/11/16.
 */

 /*
      Class to set FireBase Context Across the Package
 */
public class LaundryMan extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
