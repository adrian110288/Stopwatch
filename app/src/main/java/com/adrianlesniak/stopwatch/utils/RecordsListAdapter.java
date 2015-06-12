package com.adrianlesniak.stopwatch.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.adrianlesniak.stopwatch.R;
import com.adrianlesniak.stopwatch.database.Record;

import java.util.List;

/**
 * Created by Adrian on 09-Jun-15.
 */
public class RecordsListAdapter extends RecyclerView.Adapter<RecordViewHolder> {

    private Context mContext;
    private List<Record> mRecordsList;

    public RecordsListAdapter(Context context, List<Record> listIn) {
        mContext = context;
        mRecordsList = listIn;


    }

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.record_layout, null);
        RecordViewHolder viewHolder = new RecordViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        holder.setItem(mRecordsList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mRecordsList.size();
    }
}
