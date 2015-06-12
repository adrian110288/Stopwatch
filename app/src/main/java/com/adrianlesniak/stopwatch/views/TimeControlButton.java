package com.adrianlesniak.stopwatch.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.adrianlesniak.stopwatch.R;
import com.adrianlesniak.stopwatch.utils.Util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Adrian on 29-May-15.
 */
public class TimeControlButton extends RelativeLayout {

    private static final int START = 0;
    private static final int STOP = 1;
    private static final int RESTART = 2;
    private static final int SAVE = 3;
    private
    @Icon
    int mIcon;
    private ImageView mIconView;
    public TimeControlButton(Context context) {
        this(context, null);
    }

    public TimeControlButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TimeControlButton);
        mIcon = retrieveIcon(a.getInt(R.styleable.TimeControlButton_buttonType, 0));
        a.recycle();

        setup();
    }

    private void setup() {
        setElevation(Util.dpToPx(5));
        setupBackground();
        setupIcon();
        if (mIcon != START) hide(false);
    }

    private void setupBackground() {
        int bgRes = -1;

        switch (mIcon) {
            case START:
                bgRes = R.drawable.start_fab_shape;
                break;
            case STOP:
                bgRes = R.drawable.stop_fab_shape;
                break;
            case RESTART:
                bgRes = R.drawable.restart_fab_shape;
                break;
            case SAVE:
                bgRes = R.drawable.save_fab_shape;
                break;
        }
        setBackgroundResource(bgRes);
    }

    private void setupIcon() {
        mIconView = new ImageView(getContext());

        int iconSizePx = Util.dpToPx((int) getResources().getDimension(R.dimen.fab_icon_size));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(iconSizePx, iconSizePx);

        params.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);

        mIconView.setLayoutParams(params);
        addView(mIconView);

        int iconRes = -1;
        switch (mIcon) {
            case START:
                iconRes = R.drawable.ic_play_arrow_white_48dp;
                break;
            case STOP:
                iconRes = R.drawable.ic_pause_white_48dp;
                break;
            case RESTART:
                iconRes = R.drawable.ic_replay_white_24dp;
                break;
            case SAVE:
                iconRes = R.drawable.ic_create_white_24dp;
                break;
        }
        mIconView.setImageDrawable(getResources().getDrawable(iconRes, null));
    }

    @Icon
    private int retrieveIcon(int attrIn) {
        @Icon int icon = 0;

        switch (attrIn) {
            case 0:
                icon = START;
                break;
            case 1:
                icon = STOP;
                break;
            case 2:
                icon = RESTART;
                break;
            case 3:
                icon = SAVE;
                break;
            default:
                break;
        }

        return icon;
    }

    public void show(boolean animated) {
        this.animate().
                translationY(0).
                setDuration(animated ? getResources().getInteger(R.integer.fab_show_anim) : 0).
                setInterpolator(new AccelerateDecelerateInterpolator()).
                start();

        mIconView.
                animate().
                rotationBy(360).
                setDuration(animated ? getResources().getInteger(R.integer.fab_show_anim) / 2 : 0).
                setStartDelay(animated ? getResources().getInteger(R.integer.fab_show_anim) : 0).
                start();
    }

    public void show() {
        show(true);
    }

    public void hide(boolean animated) {
        this.animate().
                translationY(Util.getScreenHeight(getContext())).
                setDuration(animated ? getResources().getInteger(R.integer.fab_hide_anim) : 0).
                setInterpolator(new AccelerateInterpolator(2)).
                start();

        mIconView.
                animate().
                rotationBy(-360).
                setDuration(animated ? getResources().getInteger(R.integer.fab_hide_anim) : 0).
                start();
    }

    public void hide() {
        hide(true);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({START, STOP, RESTART, SAVE})

    public @interface Icon {
    }
}
