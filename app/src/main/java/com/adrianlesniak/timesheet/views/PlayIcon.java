package com.adrianlesniak.timesheet.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Created by Adrian on 19-May-15.
 */
public class PlayIcon extends Icon {

    private Path path;

    public PlayIcon(Context context) {
        super(context);
    }

    public PlayIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (path == null) {
            path = new Path();
            path.moveTo(0, 0);
            path.lineTo(0, canvas.getHeight());
            path.lineTo(canvas.getWidth(), canvas.getHeight() / 2);
            path.close();
        }

        canvas.drawPath(path, mPaint);
    }
}
