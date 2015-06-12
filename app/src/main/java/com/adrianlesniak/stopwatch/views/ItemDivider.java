package com.adrianlesniak.stopwatch.views;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.adrianlesniak.stopwatch.utils.Util;

/**
 * Created by Adrian on 11-Jun-15.
 */
public class ItemDivider extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = Util.dpToPx(1);
    }
}
