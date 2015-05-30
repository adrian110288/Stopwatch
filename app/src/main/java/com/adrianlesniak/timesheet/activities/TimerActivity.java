package com.adrianlesniak.timesheet.activities;

import android.os.Bundle;
import android.view.View;

import com.adrianlesniak.timesheet.R;
import com.adrianlesniak.timesheet.views.Stopwatch;
import com.adrianlesniak.timesheet.views.TimeControlButton;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TimerActivity extends ActionBarActivity implements View.OnClickListener {

    @InjectView(R.id.stopwatch)
    Stopwatch mStopWatch;
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

        mStartButton.setOnClickListener(this);
        mStopButton.setOnClickListener(this);

        mStopButton.hide(false);
    }

    @Override
    public void onClick(View v) {

        swapTimerButtons();

        if (v == mStartButton) {

        } else if (v == mStopButton) {
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
}
