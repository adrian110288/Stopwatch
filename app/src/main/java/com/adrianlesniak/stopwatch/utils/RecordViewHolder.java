package com.adrianlesniak.stopwatch.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.adrianlesniak.stopwatch.R;
import com.adrianlesniak.stopwatch.database.Record;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Adrian on 09-Jun-15.
 */
public class RecordViewHolder extends RecyclerView.ViewHolder {

    static {
    }

    private SimpleDateFormat dateFormat;
    private TextView mTitle;
    private TextView mDate;
    private TextView mTime;

    public RecordViewHolder(View itemView) {
        super(itemView);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        mTitle = (TextView) itemView.findViewById(R.id.title);
        mDate = (TextView) itemView.findViewById(R.id.date);
        mTime = (TextView) itemView.findViewById(R.id.time);
    }

    public void setItem(Record recordIn) {
        mTitle.setText(recordIn.getTitle());
        mDate.setText(dateFormat.format(recordIn.getDate()));
        mTime.setText(recordIn.getTime());
    }
}
