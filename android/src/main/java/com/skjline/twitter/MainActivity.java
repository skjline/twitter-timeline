package com.skjline.twitter;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.skjline.twitter.R;
import com.skjline.twitter.api.TwitterService;
import com.skjline.twitter.timeline.Contract;
import com.skjline.twitter.timeline.TimelinePresenter;
import com.skjline.twitter.timeline.TimelineRVAdapter;

public class MainActivity extends AppCompatActivity {
    private Contract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TimelineRVAdapter adapter = new TimelineRVAdapter();

        RecyclerView viewTimeline = (RecyclerView) findViewById(R.id.rv_timeline_list);
        viewTimeline.setAdapter(adapter);
        viewTimeline.setLayoutManager(new LinearLayoutManager(this));

        MainActivity.this.setTitle("TT Viewer");
        presenter = new TimelinePresenter(new TwitterService(), adapter);
//        presenter = new TimelinePresenter(new MockService(), adapter);

        presenter.initialize(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final SearchView view = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_handle));
        view.setQueryHint("Handle");
        view.setOnSearchClickListener(button -> {
            if (!presenter.enabled()) {
                Toast.makeText(button.getContext(), "User is not authenticated", Toast.LENGTH_LONG).show();
            }
        });
        view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.isEmpty()) {
                    return false;
                }

                if (presenter.enabled()) {
                    presenter.getTimeline(query);
                    MainActivity.this.setTitle("TT Viewer: " + query);
                } else {
                    Toast.makeText(view.getContext(), "User is not authenticated", Toast.LENGTH_LONG).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
}
