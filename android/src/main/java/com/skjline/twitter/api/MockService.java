package com.skjline.twitter.api;

import android.app.Activity;

import com.skjline.twitter.timeline.Timeline;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;

// For a quick testing purpose, can be moved under testing
public class MockService extends TwitterService {
    public final static String MOCK_DATA = "[" +
            "{\"created_at\": \"Tue Jul 02 11:04:43 +0000 2013\",\n" +
            " \"text\": \"item 1\"},\n" +
            "{\"created_at\": \"Tue Jul 02 11:04:43 +0000 2013\",\n" +
            " \"text\": \"item 2\"},\n" +
            "{\"created_at\": \"Tue Jul 02 11:04:43 +0000 2013\",\n" +
            " \"text\": \"item 3\"},\n" +
            "{\"created_at\": \"Tue Jul 02 11:04:43 +0000 2013\",\n" +
            " \"text\": \"item 4\"},\n" +
            "{\"created_at\": \"Tue Jul 02 11:04:43 +0000 2013\",\n" +
            " \"text\": \"item 5\"}]";

    @Override
    public void initialize(Activity activity) {
    }

    @Override
    public Observable<List<Timeline>> getTimeline(String query) {
        return Observable
                .just((List<Timeline>) new GsonBuilder().create().fromJson(MOCK_DATA, new TypeToken<List<Timeline>>() {
                }.getType()))
                .delay(2, TimeUnit.SECONDS);
    }
}

