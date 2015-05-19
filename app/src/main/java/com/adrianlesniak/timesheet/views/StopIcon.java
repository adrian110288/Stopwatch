package com.adrianlesniak.timesheet.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * Created by Adrian on 19-May-15.
 */
public class StopIcon extends Icon {

    private Rect leftRect;
    private Rect rightRect;

    public StopIcon(Context context) {
        this(context, null);
    }

    public StopIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (leftRect == null & rightRect == null) {
            leftRect = new Rect(0, 0, canvas.getWidth() / 2 - (canvas.getWidth() / 10), canvas.getHeight());
            rightRect = new Rect(canvas.getWidth() / 2 + (canvas.getWidth() / 10), 0, canvas.getWidth(), canvas.getHeight());
        }

        canvas.drawRect(leftRect, mPaint);
        canvas.drawRect(rightRect, mPaint);
    }
}
