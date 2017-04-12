package com.ljy.librarymanager.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.AnnouncementListAdapter;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.presenter.SearchBarPresenter;
import com.ljy.librarymanager.mvp.view.SearchBarView;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;
import com.ljy.librarymanager.widget.SearchView;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/3/17.
 */

public class SearchBarActivity extends BaseActivity implements SearchBarView {

    @BindView(R.id.search_toolbar)
    Toolbar search_toolbar;
    @BindView(R.id.search_search_view)
    SearchView search_search_view;
    @BindView(R.id.history_text)
    TextView history_text;
    @BindView(R.id.list)
    LoadMoreRecyclerView list;
    @BindView(R.id.clear_cache)
    Button clear_cache;

    private List<String> searchAnnoucementCache;
    private List<Announcement> searchAnnoucementData;
    private AnnouncementListAdapter announcementListAdapter;
    private ProgressDialog pg;

    @Inject
    SearchBarPresenter searchBarPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_search);
        //注入对象
        mActivityComponent.inject(this);
        searchBarPresenter.attachView(this);
        pg = new ProgressDialog(SearchBarActivity.this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage("正在搜素！");
        pg.setCancelable(false);
        announcementListAdapter = new AnnouncementListAdapter(this, searchAnnoucementData);
    }

    @Override
    protected void init() {
        search_toolbar.setTitle("");
        setSupportActionBar(search_toolbar);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(announcementListAdapter);
    }

    @Override
    protected void setListener() {
        search_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        search_search_view.setOnSearchListener(new SearchView.OnSearchListener() {
            @Override
            public void onCheckBoxClick(EditText v) {
                v.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                v.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH
                                || actionId == EditorInfo.IME_ACTION_DONE
                                || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                            showProgress();
                            searchBarPresenter.searchAnnouncement(v.getText().toString());
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
        clear_cache.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_cache: {
                break;
            }
        }
    }

    @Override
    public void searchAnnouncement(List<Announcement> mData) {
        searchAnnoucementData = mData;
        announcementListAdapter.setNewData(mData);
        clear_cache.setVisibility(View.GONE);
        history_text.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        pg.show();
    }

    @Override
    public void hideProgress() {
        pg.dismiss();
    }

    @Override
    public void showMsg(String message) {

    }
}
