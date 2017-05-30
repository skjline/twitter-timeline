package com.skjline.twitter.timeline;

import android.app.Activity;

import java.util.List;

/**
 * MVP Contract for view and presenter
 */
public interface Contract {
    interface Presenter {
        boolean enabled();
        void initialize(Activity activity);
        void getTimeline(String handle);
    }

    interface View {
        void updateTimelineList(List<Timeline> timeline);
    }
}
