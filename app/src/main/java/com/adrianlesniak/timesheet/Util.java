package com.adrianlesniak.timesheet;

import android.content.res.Resources;

/**
 * Created by Adrian on 19-May-15.
 */
public final class Util {

    public final static int getDipsFromPixel(Resources resources, float pixels) {
        final float scale = resources.getDisplayMetrics().density;
        return (int) (pixels * scale + 0.5f);
    }
}
