package com.adrianlesniak.stopwatch.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.adrianlesniak.stopwatch.R;
import com.adrianlesniak.stopwatch.fragments.SaveRecordDialog;
import com.adrianlesniak.stopwatch.interfaces.SaveRecordDialogDismissListener;
import com.adrianlesniak.stopwatch.views.StopwatchNew;
import com.adrianlesniak.stopwatch.views.TimeControlButton;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener, SaveRecordDialogDismissListener {

    @InjectView(R.id.stopwatch)
    StopwatchNew mStopWatch;
    @InjectView(R.id.startButton)
    TimeControlButton mStartButton;
    @InjectView(R.id.stopButton)
    TimeControlButton mStopButton;
    @InjectView(R.id.saveButton)
    TimeControlButton mSaveButton;
    @InjectView(R.id.restartButton)
    TimeControlButton mRestartButton;

    private boolean mTimerStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        ButterKnife.inject(this);
        setupToolbar();
        setupListeners();
    }

    private void setupListeners() {
        mStartButton.setOnClickListener(this);
        mStopButton.setOnClickListener(this);
        mSaveButton.setOnClickListener(this);
        mRestartButton.setOnClickListener(this);
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

        if (v == mStartButton || v == mStopButton) {
            swapTimerButtons();
            if (v == mStartButton) {
                mStopWatch.start();
                mSaveButton.hide();
                mRestartButton.hide();

            } else if (v == mStopButton) {
                mStopWatch.stop();
                mSaveButton.show();
                mRestartButton.show();
            }
        } else if (v == mSaveButton) {
            showSaveRecordDialog();
        } else if (v == mRestartButton) {
            mStopWatch.reset();
            mSaveButton.hide();
            mRestartButton.hide();
        }
    }

    private void showSaveRecordDialog() {
        SaveRecordDialog saveRecordDialog = new SaveRecordDialog();
        Bundle b = new Bundle();
        b.putString("time", mStopWatch.getTime());
        saveRecordDialog.setArguments(b);
        saveRecordDialog.setOnSaveDialogDismissListener(this);
        saveRecordDialog.show(getSupportFragmentManager(), "");
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
        getMenuInflater().inflate(R.menu.menu_timer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_records: {
                Intent resultsIntent = new Intent(this, ResultsActivity.class);
                startActivity(resultsIntent);
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogDismissed() {
        mStopWatch.reset();
        mSaveButton.hide();
        mRestartButton.hide();
    }
}
