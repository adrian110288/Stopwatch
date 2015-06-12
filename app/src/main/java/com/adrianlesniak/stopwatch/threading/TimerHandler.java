package com.adrianlesniak.stopwatch.threading;

import android.os.Handler;
import android.os.Message;

import com.adrianlesniak.stopwatch.interfaces.TimeChangedListener;

/**
 * Created by Adrian on 30-May-15.
 */
public class TimerHandler extends Handler {

    private String sTime;
    private TimeChangedListener mTimeChangedListener;

    public TimerHandler(TimeChangedListener listener) {
        mTimeChangedListener = listener;
    }

    @Override
    public void handleMessage(Message msg) {
        sTime = msg.getData().getString("time");
        if (mTimeChangedListener != null) {
            mTimeChangedListener.OnTimeChanged(sTime);
        }
    }
}
