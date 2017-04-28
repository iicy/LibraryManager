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
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.BookListAdapter;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.presenter.AddUserPresenter;
import com.ljy.librarymanager.mvp.presenter.BookListPresenter;
import com.ljy.librarymanager.mvp.presenter.ManagerBookPresenter;
import com.ljy.librarymanager.mvp.ui.fragment.LoadingFragment;
import com.ljy.librarymanager.mvp.view.BookInfoView;
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

    private String category;

    @Inject
    BookListPresenter mPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_booklist);
        //注入对象
        mActivityComponent.inject(this);
        mPresenter.attachView(this);
        mAdapter = new BookListAdapter(this,mData);
        category = getIntent().getStringExtra("category");
        loadingFragment = new LoadingFragment();
    }

    @Override
    protected void init() {
        mToolbar.setTitle(category);
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
                bundle.putSerializable("book",mData.get(position));
                intent.putExtras(bundle);
                intent.putExtra("account",getIntent().getStringExtra("account"));
                startActivity(intent);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadingFragment.setText("正在加载...");
                mPresenter.getList(category);
                refreshLayout.setRefreshing(false);
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
        if(getSupportFragmentManager().findFragmentByTag(TAG_LOADING_FRAGMENT)==null){
            ft.add(R.id.loading, loadingFragment, TAG_LOADING_FRAGMENT);
        }
        ft.show(loadingFragment);
        loading.setVisibility(View.VISIBLE);
        ft.commit();
    }

    @Override
    public void hideProgress() {
        ft = getSupportFragmentManager().beginTransaction();
        loadingFragment =(LoadingFragment) getSupportFragmentManager().findFragmentByTag(TAG_LOADING_FRAGMENT);
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
        if(data.size()==0){
            loadingFragment.setText("暂无数据");
            showProgress();
        }else{
            loadingFragment.setText("正在加载...");
        }
        mAdapter.setNewData(mData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getList(category);
    }
}
