package com.skjline.twitter.timeline;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skjline.twitter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Recycler View Adapter act as part of View
 */
public class TimelineRVAdapter extends RecyclerView.Adapter implements Contract.View {
    private List<Timeline> timeline = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_item, parent, false);
        return new TimelineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TimelineViewHolder vh = (TimelineViewHolder) holder;

        Timeline tl = timeline.get(position);
        vh.txtTweetText.setText(trimText(tl.getText()));
    }

    @Override
    public int getItemCount() {
        return timeline.size();
    }

    @Override
    public void updateTimelineList(List<Timeline> timeline) {
        this.timeline = timeline;
        notifyDataSetChanged();
    }

    // remove the name of retweet user + ": " from text
    private String trimText(String timelineText) {
        if (timelineText.startsWith("RT ")) {
            return timelineText.substring(timelineText.indexOf(':') + 2);
        }

        return timelineText.trim();
    }

    private static class TimelineViewHolder extends RecyclerView.ViewHolder {
        TextView txtTweetText;

        TimelineViewHolder(View view) {
            super(view);

            txtTweetText = (TextView) view.findViewById(R.id.text_tweet);
        }
    }
}

