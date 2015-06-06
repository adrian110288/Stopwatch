package com.adrianlesniak.timesheet.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Adrian on 06-Jun-15.
 */
public class MyRecyclerView extends RecyclerView {

    private View mEmptyView;

    public MyRecyclerView(Context context) {
        this(context, null);
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setEmptyView(View view) {
        this.mEmptyView = view;
    }
}
