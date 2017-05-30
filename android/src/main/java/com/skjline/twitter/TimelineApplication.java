package com.skjline.twitter;

import android.app.Application;

import org.fuckboilerplate.rx_social_connect.RxSocialConnect;

import io.victoralbertos.jolyglot.GsonSpeaker;

public class TimelineApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RxSocialConnect.register(this, this.getString(R.string.app_name)).using(new GsonSpeaker());
    }
}
