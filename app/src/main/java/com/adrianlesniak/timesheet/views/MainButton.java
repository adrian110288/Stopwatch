package com.adrianlesniak.timesheet.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.adrianlesniak.timesheet.R;

/**
 * Created by Adrian on 16-May-15.
 */
public class MainButton extends Button {

    private static Animation sButtonHideAnimation;
    private static Animation sButtonRevealAnimation;
    private static Animation sButtonDrawableRotateAnimation;
    private Drawable mDrawableLeft;

    public MainButton(Context context) {
        this(context, null);
    }

    public MainButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context);
    }

    private void setup(Context context) {
        sButtonHideAnimation = AnimationUtils.loadAnimation(context, R.anim.main_button_hide_animation);
        sButtonRevealAnimation = AnimationUtils.loadAnimation(context, R.anim.main_button_reveal_animation);
        sButtonDrawableRotateAnimation = AnimationUtils.loadAnimation(context, R.anim.main_button_drawable_left_rotate_animation);
        mDrawableLeft = getCompoundDrawables()[0];
    }

    public void show() {
        this.setVisibility(VISIBLE);
        this.startAnimation(sButtonRevealAnimation);
    }

    public void hide() {
        this.startAnimation(sButtonHideAnimation);
        this.setVisibility(GONE);
    }
}
