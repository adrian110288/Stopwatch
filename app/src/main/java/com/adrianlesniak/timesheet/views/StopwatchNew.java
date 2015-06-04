package com.adrianlesniak.timesheet.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.adrianlesniak.timesheet.R;
import com.adrianlesniak.timesheet.Util;

/**
 * Created by Adrian on 17-May-15.
 */
public class StopwatchNew extends View {

    /* Time values */
    int minutesPlaceholder = 0; //Need to keeping track of minutes
    int minuteLeft = 0;
    int minuteRight = 0;

    int secondPlaceholder = 0; //Need to keeping track of seconds
    int secondLeft = 0;
    int secondRight = 0;

    int millisecondsLeft = 0;
    int millisecondsRight = 0;

    /*Paints */
    private TextPaint mTimePaint;
    private TextPaint mMilisTimePaint;
    private ValueAnimator timeAnimator;

    private Context mContext;
    private Paint mCirclePaint;
    private Paint mArcPaint;
    private int mColor = android.R.color.white;
    private int radius = -1;
    private RectF mArcRect;
    private int mCircleSweepAngle = 0;

    public StopwatchNew(Context context) {
        this(context, null);
        setup();
    }

    public StopwatchNew(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        mContext = getContext();
        setupCirclePaint();
        setupTimePaints();
        setupArcPaint();
        setupTimer();
    }

    private void setupCirclePaint() {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(getResources().getColor(mColor));
        mCirclePaint.setStyle(Paint.Style.STROKE);
    }

    private void setupTimePaints() {
        mTimePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mMilisTimePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

        mTimePaint.setColor(getResources().getColor(R.color.time_color));
        mMilisTimePaint.setColor(getResources().getColor(R.color.time_color));

        AssetManager assetManager = mContext.getAssets();
        mTimePaint.setTypeface(Typeface.createFromAsset(assetManager, "fonts/ostrich-regular.ttf"));
        mMilisTimePaint.setTypeface(Typeface.createFromAsset(assetManager, "fonts/ostrich-black.ttf"));
    }

    private void setupArcPaint() {
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(getResources().getColor(R.color.stop_fab_color));
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /* Stopwatch visuals */
        calculateRadius(canvas);
        drawCircle(canvas);

        /* Time visuals */
        drawNumbers(canvas);
        drawArc(canvas);
    }

    private void calculateRadius(Canvas canvas) {
        if (radius == -1) {
            radius = (Math.min(canvas.getWidth() / 2, canvas.getHeight() / 2)) - Util.dpToPx((int) getResources().getDimension(R.dimen.activity_vertical_margin));

            mTimePaint.setTextSize(radius * 0.85f);
            mMilisTimePaint.setTextSize(radius * 0.42f);
            mCirclePaint.setStrokeWidth(radius * 0.06f);
            mArcPaint.setStrokeWidth(radius * 0.065f);
        }
    }

    private void drawCircle(Canvas canvas) {
        /* Main circle */
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, radius, mCirclePaint);
    }

    private void drawNumbers(Canvas canvas) {

        /* minutes */
        canvas.drawText(String.valueOf(minuteRight), canvas.getWidth() / 2 - radius * (minuteLeft != 0 ? 0.56f : 0.65f), (float) (canvas.getHeight() / 2 - (mTimePaint.ascent() + mTimePaint.descent()) / 1.6), mTimePaint);

        if (minuteLeft != 0) {
            canvas.drawText(String.valueOf(minuteLeft), canvas.getWidth() / 2 - radius * 0.86f, (float) (canvas.getHeight() / 2 - (mTimePaint.ascent() + mTimePaint.descent()) / 1.6), mTimePaint);
        }

        /* seconds */
        canvas.drawText(String.valueOf(secondLeft), canvas.getWidth() / 2 - mTimePaint.measureText(String.valueOf(secondLeft)), (float) (canvas.getHeight() / 2 - (mTimePaint.ascent() + mTimePaint.descent()) / 1.6), mTimePaint);
        canvas.drawText(String.valueOf(secondRight), (float) (canvas.getWidth() / 2 + (mTimePaint.measureText(String.valueOf(secondRight)) * 0.15)), (float) (canvas.getHeight() / 2 - (mTimePaint.ascent() + mTimePaint.descent()) / 1.6), mTimePaint);

        /* milliseconds */
        canvas.drawText(String.valueOf(millisecondsLeft), (float) canvas.getWidth() / 2 + radius * 0.36f, (float) (canvas.getHeight() / 2 - (mTimePaint.ascent() + mTimePaint.descent()) / 1.6), mMilisTimePaint);
        canvas.drawText(String.valueOf(millisecondsRight), (float) canvas.getWidth() / 2 + radius * 0.56f, (float) (canvas.getHeight() / 2 - (mTimePaint.ascent() + mTimePaint.descent()) / 1.6), mMilisTimePaint);
    }

    private void drawArc(Canvas canvas) {
        if (mArcRect == null) {
            mArcRect = new RectF(canvas.getWidth() / 2 - radius, canvas.getHeight() / 2 - radius, canvas.getWidth() / 2 + radius, canvas.getHeight() / 2 + radius);
        }
        canvas.drawArc(mArcRect, -90, mCircleSweepAngle, false, mArcPaint);
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

        mCircleSweepAngle++;
        if (mCircleSweepAngle == 360) mCircleSweepAngle = 0;

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
        minutesPlaceholder++;

        if (minutesPlaceholder < 10) minuteRight = secondPlaceholder;
        else {
            String s = String.valueOf(minutesPlaceholder);
            minuteLeft = Character.getNumericValue(s.charAt(0));
            minuteRight = Character.getNumericValue(s.charAt(1));
        }

        invalidate();
    }

    public void start() {
        timeAnimator.start();
    }

    public void stop() {
        timeAnimator.cancel();
    }

}
