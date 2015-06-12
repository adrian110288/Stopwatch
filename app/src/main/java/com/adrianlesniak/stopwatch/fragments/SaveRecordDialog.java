package com.adrianlesniak.stopwatch.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adrianlesniak.stopwatch.R;
import com.adrianlesniak.stopwatch.database.Record;
import com.adrianlesniak.stopwatch.interfaces.SaveRecordDialogDismissListener;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Adrian on 06-Jun-15.
 */
public class SaveRecordDialog extends DialogFragment implements View.OnClickListener {

    @InjectView(R.id.recordText)
    TextView mRecordText;
    @InjectView(R.id.title_edit_text)
    EditText mTitleEditText;
    @InjectView(R.id.comment_edit_text)
    EditText mCommentEditText;
    //    @InjectView(R.id.tag_edit_text)
//    EditText mTagEditText;
//    @InjectView(R.id.add_tag_button)
//    Button mAddTagButton;
    @InjectView(R.id.save_record_button)
    Button mSaveButton;
    @InjectView(R.id.cancel_record_button)
    Button mCancelButton;

    private String mRecord;
    private SaveRecordDialogDismissListener mSaveRecordDialogDismissListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialog = inflater.inflate(R.layout.save_dialog_layout, container);
        ButterKnife.inject(this, dialog);
        return dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mRecord = getArguments().getString("time");
        mRecordText.setText(mRecord);
        setListeners();
    }

    private void setListeners() {
//        mAddTagButton.setOnClickListener(this);
        mSaveButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mCancelButton) dismiss();
        else if (v == mSaveButton) saveRecord();
    }

    private void saveRecord() {
        Record newRecord = new Record(new Date(), mTitleEditText.getText().toString(), mRecord, mCommentEditText.getText().toString(), null);
        newRecord.save();
        dismiss();
        Toast.makeText(getActivity(), getResources().getString(R.string.record_saved_message), Toast.LENGTH_SHORT).show();
        if (mSaveRecordDialogDismissListener != null)
            mSaveRecordDialogDismissListener.onDialogDismissed();
    }

    public void setOnSaveDialogDismissListener(SaveRecordDialogDismissListener listenerIn) {
        mSaveRecordDialogDismissListener = listenerIn;
    }

}
