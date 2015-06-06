package com.adrianlesniak.timesheet.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.adrianlesniak.timesheet.R;
import com.adrianlesniak.timesheet.views.MyRecyclerView;

import butterknife.InjectView;

/**
 * Created by Adrian on 05-Jun-15.
 */
public class ResultsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    @InjectView(R.id.my_recycler_view)
    MyRecyclerView myRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_activity);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_results, menu);
        setupSearchView(menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupSearchView(Menu menu) {
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search your results");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
