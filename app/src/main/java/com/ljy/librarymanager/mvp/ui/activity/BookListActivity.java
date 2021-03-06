package com.ljy.librarymanager.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.BookListAdapter;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.presenter.BookListPresenter;
import com.ljy.librarymanager.mvp.ui.fragment.LoadingFragment;
import com.ljy.librarymanager.mvp.view.BookListView;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/4/11.
 */

public class BookListActivity extends BaseActivity implements BookListView {

    @BindView(R.id.book_list_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.loading)
    FrameLayout loading;
    @BindView(R.id.list)
    LoadMoreRecyclerView list;

    private List<Books> mData;
    private BookListAdapter mAdapter;
    private FragmentTransaction ft;
    private LoadingFragment loadingFragment;
    private static final String TAG_LOADING_FRAGMENT = "LOADING_FRAGMENT";
    private ProgressDialog pg;

    private String category;
    private int more = 0;

    @Inject
    BookListPresenter mPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_booklist);
        //注入对象
        mActivityComponent.inject(this);
        mPresenter.attachView(this);
        mAdapter = new BookListAdapter(this, mData);
        category = getIntent().getStringExtra("category");
        loadingFragment = new LoadingFragment();
        pg = new ProgressDialog(this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage(getString(R.string.waiting));
        pg.setCancelable(false);
    }

    @Override
    protected void init() {
        if (category == null || category.equals("")) {
            mToolbar.setTitle("全部图书");
        } else {
            mToolbar.setTitle(category);
        }
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAdapter.setOnItemClickListener(new BookListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(BookListActivity.this, BookInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", mData.get(position));
                intent.putExtras(bundle);
                intent.putExtra("account", getIntent().getStringExtra("account"));
                startActivity(intent);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadingFragment.setText(getString(R.string.loading));
                mPresenter.getList(category, 0);
                more = 0;
                refreshLayout.setRefreshing(false);
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_toolbar_search: {
                        Intent intent = new Intent(BookListActivity.this, SearchBarActivity.class);
                        intent.putExtra("searchType", "book");
                        intent.putExtra("identity", "user");
                        startActivity(intent);
                        break;
                    }
                }
                return false;
            }
        });
        list.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void loadMore() {
                more += 5;
                mPresenter.getList(category, more);
            }
        });
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
    }

    @Override
    public void showProgress() {
        ft = getSupportFragmentManager().beginTransaction();
        if (getSupportFragmentManager().findFragmentByTag(TAG_LOADING_FRAGMENT) == null) {
            ft.add(R.id.loading, loadingFragment, TAG_LOADING_FRAGMENT);
        }
        ft.show(loadingFragment);
        loading.setVisibility(View.VISIBLE);
        ft.commit();
    }

    @Override
    public void hideProgress() {
        ft = getSupportFragmentManager().beginTransaction();
        loadingFragment = (LoadingFragment) getSupportFragmentManager().findFragmentByTag(TAG_LOADING_FRAGMENT);
        ft.hide(loadingFragment);
        loading.setVisibility(View.GONE);
        ft.commit();
    }

    @Override
    public void showMsg(String message) {
        Toast.makeText(BookListActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setList(List<Books> data) {
        mData = data;
        if (data.size() == 0) {
            loadingFragment.setText(getString(R.string.no_data));
            showProgress();
        } else {
            loadingFragment.setText(getString(R.string.loading));
        }
        mAdapter.setNewData(mData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getList(category, 0);
    }
}
