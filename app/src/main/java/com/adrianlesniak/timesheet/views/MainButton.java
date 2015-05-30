package com.adrianlesniak.timesheet.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adrianlesniak.timesheet.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Adrian on 16-May-15.
 */
public class MainButton extends RelativeLayout {

    private static final int LAYOUT = 0;
    private static final AccelerateInterpolator interpolator = new AccelerateInterpolator();
    @InjectView(R.id.title)
    TextView mTitle;
    private Type mType = Type.START;
    private View mVisibleIcon;
    private float mIconXPos = -1;
    private float mTitleXPos = -1;
    public MainButton(Context context, Type typeIn) {
        super(context);
        mType = typeIn;
        setup(context);
    }

    public MainButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context);
    }

    private void setup(Context context) {

        View.inflate(context, LAYOUT, this);
        ButterKnife.inject(this);

        mTitle.setTypeface(Typeface.createFromAsset(context.getAssets(), "sofachromerg.ttf"));

        if (mType == Type.START) {
            setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
            mTitle.setText("Start");
        }

        if (mType == Type.STOP) {
            setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
            mTitle.setText("Stop");
        }

        mIconXPos = mVisibleIcon.getX();
        mTitleXPos = mTitle.getX();
    }

    public void show() {
        animate().translationX(0).setInterpolator(interpolator).start();
        mVisibleIcon.animate().translationX(mIconXPos).setInterpolator(interpolator).setStartDelay(80).start();
        mTitle.animate().translationX(mTitleXPos).setInterpolator(interpolator).setStartDelay(120).start();
    }

    public void hide() {
        mTitle.animate().translationX(MainButton.this.getWidth()).setInterpolator(interpolator).start();
        mVisibleIcon.animate().translationX(MainButton.this.getWidth()).setInterpolator(interpolator).setStartDelay(120).start();
        animate().translationX(MainButton.this.getWidth()).setInterpolator(interpolator).setStartDelay(80).start();
    }

    private enum Type {
        START, STOP
    }
}
