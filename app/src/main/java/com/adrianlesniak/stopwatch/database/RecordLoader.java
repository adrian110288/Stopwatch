package com.adrianlesniak.stopwatch.database;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Adrian on 09-Jun-15.
 */
public class RecordLoader extends AsyncTaskLoader<ArrayList<Record>> {

    private String mQuery;

    public RecordLoader(Context context, String queryIn) {
        super(context);
        mQuery = queryIn;
    }

    @Override
    public ArrayList<Record> loadInBackground() {
        ArrayList<Record> recordList = null;

        Log.i("QUERY", mQuery == null ? "null" : mQuery);

        if (TextUtils.isEmpty(mQuery) || mQuery == null) {
            recordList = (ArrayList<Record>) Record.listAll(Record.class);
        } else {
            recordList = (ArrayList<Record>) Record.findWithQuery(Record.class, "Select * from Record where my_field like ?", "%" + mQuery + "%");
        }

        return recordList;
    }
}
