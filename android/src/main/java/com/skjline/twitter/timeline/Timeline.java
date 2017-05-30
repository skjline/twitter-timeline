package com.skjline.twitter.timeline;

import com.google.gson.annotations.SerializedName;

/**
 * Simplified Twitter timeline model for a purpose of this exercise
 * Just need to show the timeline text at the moment
 */
public class Timeline {
    @SerializedName("text")
    String textValue;

    @SerializedName("created_at")
    String createdAt;

    @SerializedName("retweeted")
    boolean retweet;

    public Timeline() {
    }

    public String getText() {
        return textValue;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public boolean isRetweet() {
        return retweet;
    }
}
