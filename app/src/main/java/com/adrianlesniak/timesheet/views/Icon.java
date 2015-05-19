package com.adrianlesniak.timesheet.views;

import android.content.Context;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.adrianlesniak.timesheet.R;
import com.adrianlesniak.timesheet.Util;

/**
 * Created by Adrian on 19-May-15.
 */
public abstract class Icon extends View {

    protected static final int COLOR = android.R.color.white;
    protected static int CORNER_RADIUS;
    protected Paint mPaint;

    public Icon(Context context) {
        this(context, null);
    }

    public Icon(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        CORNER_RADIUS = getContext().getResources().getInteger(R.integer.main_button_icon_corner_radius);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(COLOR));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(Util.getDipsFromPixel(getResources(), CORNER_RADIUS)));
    }
}
