package com.adrianlesniak.timesheet;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Adrian on 19-May-15.
 */
public abstract class Util {

    private static Point sScreenDimension;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenWidth(Context context) {
        if (sScreenDimension != null) {
            return sScreenDimension.x;
        }

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        sScreenDimension = new Point();
        display.getSize(sScreenDimension);
        return sScreenDimension.x;
    }

    public static int getScreenHeight(Context context) {
        if (sScreenDimension != null) {
            return sScreenDimension.y;
        }

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        sScreenDimension = new Point();
        display.getSize(sScreenDimension);
        return sScreenDimension.y;
    }
}
