package com.skjline.twitter.api;

import android.app.Activity;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.skjline.twitter.timeline.Timeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import io.victoralbertos.rx_social_connect.OAuth1Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;
import rx.schedulers.Schedulers;

public class TwitterService {
    private static final String BASE_URL = "https://api.twitter.com/1.1/";

    private TwitterAuthentication authenticator;
    private OkHttpClient client;

    public TwitterService() {
        authenticator = new TwitterAuthentication();
    }

    public boolean enabled() {
        return client != null;
    }

    public void initialize(Activity activity) {
        authenticator.authenticate(activity)
                .subscribe(this::createHttpClient);
    }

    public Observable<List<Timeline>> getTimeline(String handle) {
        Map<String, String> options = new HashMap<>();
        options.put("screen_name", handle);
        options.put("count", "10");

        if (getApi() == null) {
            return Observable
                    .fromCallable(() -> (List<Timeline>) new ArrayList<Timeline>())
                    .delay(100, TimeUnit.MILLISECONDS);
        }
        return getApi().getTimeline(options).subscribeOn(Schedulers.io());
    }

    private void createHttpClient(OAuth1AccessToken token) {
        if (token == null) {
            return;
        }

        SSLSocketFactory factory = SSLSocketFactoryHelper.getSocketFactory();
        X509TrustManager trust = SSLSocketFactoryHelper.trust;

        client = new OkHttpClient().newBuilder()
                .socketFactory(SocketFactory.getDefault())
                .sslSocketFactory(factory, trust)
                .addInterceptor(new OAuth1Interceptor(authenticator.getService()))
                .build();
    }

    private TwitterApi getApi() {
        if (client == null) {
            return null;
        }

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client).baseUrl(BASE_URL).build().create(TwitterApi.class);
    }

    interface TwitterApi {
        @GET("statuses/user_timeline.json")
        Observable<List<Timeline>> getTimeline(@QueryMap Map<String, String> options);
    }
}
