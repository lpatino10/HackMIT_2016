package com.example.loganpatino.hackmit_2016;

import com.firebase.client.Firebase;

/**
 * Created by loganpatino on 9/17/16.
 */
public class HackMIT_2016 extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
