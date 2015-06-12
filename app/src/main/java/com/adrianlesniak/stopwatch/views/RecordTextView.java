package com.adrianlesniak.stopwatch.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.adrianlesniak.stopwatch.R;

/**
 * Created by Adrian on 11-Jun-15.
 */
public class RecordTextView extends TextView {

    private static final int FONT_REGULAR = 0;
    private static final int FONT_BOLD = 1;

    private int mFontType = 0;

    public RecordTextView(Context context) {
        this(context, null);
    }

    public RecordTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RecordTextView);
        mFontType = a.getInt(R.styleable.RecordTextView_fontType, 0);
        a.recycle();
        setup();
    }

    private void setup() {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), mFontType == FONT_REGULAR ? "fonts/weblysleekuil.ttf" : "fonts/weblysleekuisb.ttf"));
    }
}
