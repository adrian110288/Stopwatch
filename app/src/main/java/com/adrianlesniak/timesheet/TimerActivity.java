package com.adrianlesniak.timesheet;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.adrianlesniak.timesheet.views.MainButton;
import com.adrianlesniak.timesheet.views.Stopwatch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class TimerActivity extends ActionBarActivity {

    private static final UIState DEFAULT_STATE = UIState.READY;
    private String LOG = this.getClass().getName();
    private TimerThread mCurrentThread;
    private TimerHandler mHander = new TimerHandler();

    @InjectView(R.id.button_start)
    MainButton mStartButton;

    @InjectView(R.id.button_stop)
    MainButton mStopButton;

    @InjectView(R.id.time)
    TextView mTimeText;

    @InjectView(R.id.stopwatch)
    Stopwatch mStopWatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        ButterKnife.inject(this);

        mTimeText.setTypeface(Typeface.createFromAsset(getAssets(), "sofachromerg.ttf"));
    }

    public void startClicked(View v) {
        mStopWatch.startTimer();
        mCurrentThread = new TimerThread(mHander);
        mCurrentThread.start();
    }

    public void stopClicked(View v) {
        mStopWatch.stopTimer();
        mCurrentThread.setCanStop(true);
    }

    private class TimerHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            String time = msg.getData().getString("time");
            mTimeText.setText(time);
        }
    }

    private class TimerThread extends Thread {

        private long startTime;
        private Handler mHandler;
        private boolean canStop = false;

        public TimerThread(Handler handlerIn) {
            mHandler = handlerIn;
        }

        private void setCanStop(boolean canStop) {
            this.canStop = canStop;
        }

        @Override
        public void run() {

            startTime = System.currentTimeMillis();

            Bundle b = new Bundle();
            Date date;
            Message msg;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:.SS");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            while (!canStop) {

                long endTime = System.currentTimeMillis();
                long delta = endTime - startTime;
                date = new Date(delta);

                msg = Message.obtain();
                b.putString("time", simpleDateFormat.format(date));
                msg.setData(b);
                mHandler.sendMessage(msg);
            }
        }
    }
}
