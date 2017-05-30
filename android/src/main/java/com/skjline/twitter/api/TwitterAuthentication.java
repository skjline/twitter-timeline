package com.skjline.twitter.api;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.skjline.twitter.R;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.oauth.OAuth10aService;

import org.fuckboilerplate.rx_social_connect.RxSocialConnect;

import rx.Observable;

class TwitterAuthentication {
    private OAuth1AccessToken accessToken;
    private OAuth10aService twitterService;

    TwitterAuthentication() {
    }

    OAuth10aService getService() {
        return twitterService;
    }

    Observable<OAuth1AccessToken> authenticate(Activity activity) {
        twitterService = new ServiceBuilder()
                .apiKey(activity.getString(R.string.consumer_key))
                .apiSecret(activity.getString(R.string.consumer_secret))
                .callback("")
                .build(TwitterApi.instance());

        return RxSocialConnect.with(activity, twitterService)
                .onErrorReturn(throwable -> {
                    Log.e("Authentication", "Unable to authenticate: " + throwable.toString());
                    Toast.makeText(activity, "Unable to authenticate user", Toast.LENGTH_LONG)
                            .show();
                    return null;
                })
                .map(response -> {
                    if (response != null) {
                        accessToken = response.token();
                    }

                    return accessToken;
                });
    }
}
