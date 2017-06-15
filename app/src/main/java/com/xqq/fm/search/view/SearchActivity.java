package com.xqq.fm.search.view;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.xqq.fm.R;
import com.xqq.fm.daily.adapter.ExpandListAdapter;
import com.xqq.fm.search.model.SearchResponse;
import com.xqq.fm.search.presenter.ISearchPresenter;
import com.xqq.fm.search.presenter.SearchPresenter;

public class SearchActivity extends AppCompatActivity implements ISearchView {

    private ExpandableListView elv;
    private ExpandListAdapter adapter;
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private ISearchPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bill_analysis);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        // 初始化ActionBar
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

        String category = getIntent().getStringExtra("category");
        mActionBar.setTitle(category);

        presenter = new SearchPresenter(new SearchResponse(), (ISearchView) this);
        elv = (ExpandableListView) findViewById(R.id.elv_activity_search);
        adapter = new ExpandListAdapter(this,
                presenter.getBillHeaderByCategory(category), presenter.getBillBody(category));
        elv.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
