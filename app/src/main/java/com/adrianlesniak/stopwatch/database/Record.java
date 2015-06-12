package com.adrianlesniak.stopwatch.database;

import android.util.SparseArray;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Adrian on 06-Jun-15.
 */
public class Record extends SugarRecord<Record> {

    private Date date;
    private String myField;
    private String time;
    private String comment;
//    private SparseArray<String> mTags;

    public Record() {
    }

    public Record(Date dateIn, String titleIn, String timeIn, String commentIn, SparseArray<String> tagsIn) {
        this.date = dateIn;
        this.myField = titleIn;
        this.time = timeIn;
        this.comment = commentIn;
//        this.mTags = tagsIn;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return myField;
    }

    public String getTime() {
        return time;
    }

    public String getComment() {
        return comment;
    }

//    public SparseArray<String> getTags() {
//        return mTags;
//    }
}
