package com.adrianlesniak.stopwatch.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.adrianlesniak.stopwatch.R;

/**
 * Created by Adrian on 30-May-15.
 */
public class TimeBoard extends View {

    int minuteLeft = 0;
    int minuteRight = 0;
    int secondPlaceholder = 0; //Need to keeping track of seconds
    int secondLeft = 0;
    int secondRight = 0;
    int millisecondsLeft = 0;
    int millisecondsRight = 0;
    private TextPaint mTimePaint;
    private TextPaint mMilisTimePaint;
    private ValueAnimator timeAnimator;

    public TimeBoard(Context context) {
        this(context, null);
    }

    public TimeBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        setupPaints();
        setupTimer();
    }

    private void setupPaints() {
        mTimePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mMilisTimePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

        mTimePaint.setColor(getResources().getColor(R.color.time_color));
        mMilisTimePaint.setColor(getResources().getColor(R.color.time_color));

        AssetManager assetManager = getContext().getAssets();
        mTimePaint.setTypeface(Typeface.createFromAsset(assetManager, "fonts/ostrich-regular.ttf"));
        mMilisTimePaint.setTypeface(Typeface.createFromAsset(assetManager, "fonts/ostrich-black.ttf"));

        mTimePaint.setTextSize(400);
        mMilisTimePaint.setTextSize(160);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int adjustedY = (int) (canvas.getHeight() / 2 - (mTimePaint.ascent() + mTimePaint.descent()) / 2);
        canvas.drawText(String.valueOf(secondLeft), (canvas.getWidth() / 2 - (mTimePaint.measureText(String.valueOf(secondLeft)) / 2)) - 60, adjustedY, mTimePaint);
        canvas.drawText(String.valueOf(secondRight), (canvas.getWidth() / 2 - (mTimePaint.measureText(String.valueOf(secondRight)) / 2)) + 80, adjustedY, mTimePaint);
        canvas.drawText(String.valueOf(millisecondsLeft), (canvas.getWidth() / 2 - (mTimePaint.measureText(String.valueOf(millisecondsLeft)) / 2)) + 200, adjustedY, mMilisTimePaint);
        canvas.drawText(String.valueOf(millisecondsRight), (canvas.getWidth() / 2 - (mTimePaint.measureText(String.valueOf(millisecondsRight)) / 2)) + 260, adjustedY, mMilisTimePaint);
    }

    private void setupTimer() {
        timeAnimator = new ValueAnimator();
        timeAnimator.setIntValues(1, 99);
        timeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int milliseconds = (int) animation.getAnimatedValue();
                recalculateTime(milliseconds);
            }
        });
        timeAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                calculateSeconds();
            }
        });
        timeAnimator.setDuration(1000);
        timeAnimator.setRepeatCount(ValueAnimator.INFINITE);
        timeAnimator.setRepeatMode(ValueAnimator.RESTART);
    }

    private void recalculateTime(int millisecondsIn) {
        if (millisecondsIn < 10) millisecondsRight = millisecondsIn;
        else {
            String s = String.valueOf(millisecondsIn);
            millisecondsLeft = Character.getNumericValue(s.charAt(0));
            millisecondsRight = Character.getNumericValue(s.charAt(1));
        }
        invalidate();
    }

    private void calculateSeconds() {
        secondPlaceholder++;

        if (secondPlaceholder < 10) secondRight = secondPlaceholder;
        else {
            String s = String.valueOf(secondPlaceholder);
            secondLeft = Character.getNumericValue(s.charAt(0));
            secondRight = Character.getNumericValue(s.charAt(1));
        }

        if (secondPlaceholder > 59) {
            secondLeft = secondRight = secondPlaceholder = 0;
            calculateMinutes();
        }
        invalidate();
    }

    private void calculateMinutes() {

    }

    public void start() {
        timeAnimator.start();
    }

    public void stop() {
        timeAnimator.cancel();
    }
}
