package com.adrianlesniak.stopwatch.activities;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

import com.adrianlesniak.stopwatch.R;
import com.adrianlesniak.stopwatch.database.Record;
import com.adrianlesniak.stopwatch.database.RecordLoader;
import com.adrianlesniak.stopwatch.utils.RecordsListAdapter;
import com.adrianlesniak.stopwatch.views.MyRecyclerView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Adrian on 05-Jun-15.
 */
public class ResultsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, android.support.v4.app.LoaderManager.LoaderCallbacks {

    @InjectView(R.id.my_recycler_view)
    MyRecyclerView myRecyclerView;
    @InjectView(R.id.empty_view)
    TextView mEmptyView;

    private Bundle mQueryBundle;
    private ArrayList<Record> mRecordList;
    private RecordsListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_activity);
        ButterKnife.inject(this);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        myRecyclerView.setEmptyView(mEmptyView);

        mQueryBundle = new Bundle(1);
        mQueryBundle.putString("query", null);
        getSupportLoaderManager().initLoader(0, mQueryBundle, this).forceLoad();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_results, menu);
        setupSearchView(menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupSearchView(Menu menu) {
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        EditText searchViewEditText = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        searchViewEditText.setTextColor(getResources().getColor(android.R.color.white));
        searchViewEditText.setHintTextColor(getResources().getColor(android.R.color.white));

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getResources().getString(R.string.search_view_hint));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mQueryBundle.putString("query", query);
        getSupportLoaderManager().restartLoader(0, mQueryBundle, this).forceLoad();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    @Override
    public Loader onCreateLoader(int id, Bundle bundle) {
        return new RecordLoader(this, bundle.getString("query"));
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        mRecordList = (ArrayList<Record>) data;

        if (mRecordList != null) {
            mListAdapter = new RecordsListAdapter(this, mRecordList);
            myRecyclerView.setAdapter(mListAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        myRecyclerView.setAdapter(null);
    }
}
