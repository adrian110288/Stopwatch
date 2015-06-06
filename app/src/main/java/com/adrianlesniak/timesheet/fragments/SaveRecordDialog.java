package com.adrianlesniak.timesheet.fragments;

import android.content.DialogInterface;
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

import com.adrianlesniak.timesheet.R;
import com.adrianlesniak.timesheet.database.Record;

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
    @InjectView(R.id.tag_edit_text)
    EditText mTagEditText;
    @InjectView(R.id.add_tag_button)
    Button mAddTagButton;
    @InjectView(R.id.save_record_button)
    Button mSaveButton;
    @InjectView(R.id.cancel_record_button)
    Button mCancelButton;
    private String mRecord;
    private DismissListener mListener;

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
        mAddTagButton.setOnClickListener(this);
        mSaveButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == mCancelButton) dismiss();
        else if (v == mSaveButton) {
            saveRecord();
        }
    }

    public void setOnDismissListener(DismissListener listenerIn) {
        mListener = listenerIn;
    }

    private void saveRecord() {
        Record newRecord = new Record(new Date(), mTitleEditText.getText().toString(), mRecord, "", null);
        newRecord.save();
        dismiss();
        Toast.makeText(getActivity(), "Record saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mListener != null) mListener.onSaveDialogDismissListener();
    }

    public interface DismissListener {
        void onSaveDialogDismissListener();
    }
}
