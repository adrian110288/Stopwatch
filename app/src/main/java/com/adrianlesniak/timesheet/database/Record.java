package com.adrianlesniak.timesheet.database;

import android.util.SparseArray;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Adrian on 06-Jun-15.
 */
public class Record extends SugarRecord<Record> {

    private Date mDate;
    private String mTitle;
    private String mTime;
    private String mComment;
    private SparseArray<String> mTags;

    public Record(Date dateIn, String titleIn, String timeIn, String commentIn, SparseArray<String> tagsIn) {
        this.mDate = dateIn;
        this.mTitle = titleIn;
        this.mTime = timeIn;
        this.mComment = commentIn;
        this.mTags = tagsIn;
    }
}
