package com.adrianlesniak.timesheet.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Adrian on 17-May-15.
 */
public class Stopwatch extends View {

    private Path mSideButtonPath;
    private Path mNeedlePath;

    private Paint mCirclePaint;
    private Paint mSideButtonPaint;
    private Paint mNeedlePaint;
    private int mColor = android.R.color.white;
    private int radius = -1;
    private int mNeedleRotation = 0;

    private Handler mTimerHandler;
    private TimerThread mCurrentThread;

    public Stopwatch(Context context) {
        this(context, null);
        setup();
    }

    public Stopwatch(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        setupCirclePaint();
        setupSideButtonsPaint();
        setupNeedlePaint();
        mTimerHandler = new TimerHandler();
    }

    public void startTimer() {
        mCurrentThread = new TimerThread(mTimerHandler);
        mCurrentThread.start();
    }

    public void stopTimer() {
        mCurrentThread.setCanStop(true);
    }

    private void setupCirclePaint() {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(getResources().getColor(mColor));
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(25);
    }

    private void setupSideButtonsPaint() {
        mSideButtonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSideButtonPaint.setPathEffect(new CornerPathEffect(5));
        mSideButtonPaint.setStyle(Paint.Style.FILL);
        mSideButtonPaint.setColor(getResources().getColor(mColor));
    }

    private void setupNeedlePaint() {
        mNeedlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNeedlePaint.setPathEffect(new CornerPathEffect(5));
        mNeedlePaint.setStyle(Paint.Style.FILL);
        mNeedlePaint.setColor(getResources().getColor(mColor));
        mNeedlePaint.setAlpha(15);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        calculateRadius(canvas);
        drawCircle(canvas);
        drawTopElementPath(canvas);
        drawSideButtons(canvas);
        drawNeedle(canvas);
    }

    private void calculateRadius(Canvas canvas) {
        if (radius == -1) {
            radius = (int) ((Math.min(canvas.getWidth() / 2, canvas.getHeight() / 2)) - ((mCirclePaint.getStrokeWidth() / 2) * 6));
        }
    }

    private void drawCircle(Canvas canvas) {

        /* Main circle */
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, radius, mCirclePaint);
    }

    private void drawSideButtons(Canvas canvas) {
        createSideButtonPath(canvas);
        canvas.save();
        canvas.rotate(-45, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.drawPath(mSideButtonPath, mSideButtonPaint);
        canvas.restore();
        canvas.save();
        canvas.rotate(45, canvas.getWidth() / 2, canvas.getHeight() / 2);
        canvas.drawPath(mSideButtonPath, mSideButtonPaint);
        canvas.restore();
    }

    private void drawTopElementPath(Canvas canvas) {
        canvas.drawRect(new Rect(canvas.getWidth() / 2 - 35, canvas.getHeight() / 2 - radius - 70, canvas.getWidth() / 2 + 35, canvas.getHeight() / 2 - radius), mSideButtonPaint);
    }

    private void createSideButtonPath(Canvas canvas) {

        if (mSideButtonPath == null) {
            mSideButtonPath = new Path();
            mSideButtonPath.addRect(new RectF(canvas.getWidth() / 2 - 70, canvas.getHeight() / 2 - radius - 100, canvas.getWidth() / 2 + 70, canvas.getHeight() / 2 - radius - 40), Path.Direction.CW);
            mSideButtonPath.moveTo(canvas.getWidth() / 2 - 30, canvas.getHeight() / 2 - radius - 40);
            mSideButtonPath.lineTo(canvas.getWidth() / 2 + 30, canvas.getHeight() / 2 - radius - 40);
            mSideButtonPath.lineTo(canvas.getWidth() / 2, canvas.getHeight() / 2 - radius - mCirclePaint.getStrokeWidth() / 2 + 5);
            mSideButtonPath.lineTo(canvas.getWidth() / 2 - 30, canvas.getHeight() / 2 - radius - 40);
        }
    }

    private void drawNeedle(Canvas canvas) {
        canvas.save();
        canvas.rotate(mNeedleRotation, canvas.getWidth() / 2, canvas.getHeight() / 2);
        createNeedlePath(canvas);
        canvas.drawPath(mNeedlePath, mNeedlePaint);
        canvas.restore();
    }

    private void createNeedlePath(Canvas canvas) {

        if (mNeedlePath == null) {
            mNeedlePath = new Path();
            mNeedlePath.moveTo(canvas.getWidth() / 2 - 20, (float) (canvas.getHeight() / 2 - radius + (radius * 0.15)));
            mNeedlePath.lineTo(canvas.getWidth() / 2 + 20, (float) (canvas.getHeight() / 2 - radius + (radius * 0.15)));
            mNeedlePath.lineTo(canvas.getWidth() / 2 + 20, (float) (canvas.getHeight() / 2 + (radius * 0.15) - ((radius * 0.15) * 0.1)));
            mNeedlePath.lineTo(canvas.getWidth() / 2, (float) (canvas.getHeight() / 2 + (radius * 0.15) + 50));
            mNeedlePath.lineTo(canvas.getWidth() / 2 - 20, (float) (canvas.getHeight() / 2 + (radius * 0.15) - ((radius * 0.15) * 0.1)));
            mNeedlePath.addCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, (float) (radius * 0.15), Path.Direction.CW);
            mNeedlePath.lineTo(canvas.getWidth() / 2 - 20, (float) (canvas.getHeight() / 2 - radius + (radius * 0.15)));
        }
    }

    private class TimerHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mNeedleRotation = msg.what;
            invalidate();
        }
    }

    private class TimerThread extends Thread {

        private Handler mHandler;
        private boolean canStop = false;
        private int startingRotation = 0;

        public TimerThread(Handler handlerIn) {
            mHandler = handlerIn;
        }

        public void setCanStop(boolean canStop) {
            this.canStop = canStop;
        }

        @Override
        public void run() {

            while (!canStop) {
                startingRotation += 3;

                mHandler.sendEmptyMessage(startingRotation);
                try {
                    sleep(30l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
