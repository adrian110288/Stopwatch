package com.adrianlesniak.stopwatch.threading;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Adrian on 30-May-15.
 */
public class TimerThread extends Thread {

    private long startTime;
    private Handler mHandler;
    private boolean canStop = false;

    public TimerThread(Handler handlerIn) {
        mHandler = handlerIn;
    }

    public void stopTimer() {
        this.canStop = true;
        interrupt();
    }

    @Override
    public void run() {

        startTime = System.currentTimeMillis();

        Bundle b = new Bundle();
        Date date;
        Message msg;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:.SS", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        while (!canStop && !interrupted()) {

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
