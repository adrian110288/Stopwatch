package com.adrianlesniak.timesheet.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.adrianlesniak.timesheet.R;
import com.adrianlesniak.timesheet.views.StopwatchNew;
import com.adrianlesniak.timesheet.views.TimeControlButton;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.stopwatch)
    StopwatchNew mStopWatch;
    @InjectView(R.id.startButton)
    TimeControlButton mStartButton;
    @InjectView(R.id.stopButton)
    TimeControlButton mStopButton;
    private boolean mTimerStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        ButterKnife.inject(this);
        setupToolbar();

        mStartButton.setOnClickListener(this);
        mStopButton.setOnClickListener(this);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        toolbarTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Mathlete-Bulky.otf"));
        toolbarTitle.setText(getResources().getString(R.string.app_name).toString().toUpperCase());
    }

    @Override
    public void onClick(View v) {
        swapTimerButtons();

        if (v == mStartButton) {
            mStopWatch.start();

        } else if (v == mStopButton) {
            mStopWatch.stop();
        }
    }

    private void swapTimerButtons() {
        if (!mTimerStarted) {
            mStartButton.hide();
            mStopButton.show();

        } else {
            mStartButton.show();
            mStopButton.hide();
        }
        mTimerStarted = !mTimerStarted;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_timer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_records: {
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
