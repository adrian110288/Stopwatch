package com.adrianlesniak.stopwatch.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
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
        setup();
    }

    private void setup() {
        setLayoutManager(new LinearLayoutManager(getContext()));
//        addItemDecoration(new ItemDivider());
    }

    public void setEmptyView(View view) {
        this.mEmptyView = view;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (mEmptyView != null && adapter != null) {
            mEmptyView.setVisibility(adapter.getItemCount() == 0 ? VISIBLE : GONE);

            //if(mEmptyView.getVisibility() !=VISIBLE) this.setVisibility(VISIBLE);
        }
    }
}
