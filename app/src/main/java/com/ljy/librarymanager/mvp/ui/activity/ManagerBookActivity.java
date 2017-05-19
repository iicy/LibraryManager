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
import com.ljy.librarymanager.mvp.presenter.ManagerBookPresenter;
import com.ljy.librarymanager.mvp.ui.fragment.LoadingFragment;
import com.ljy.librarymanager.mvp.view.ManagerBookView;
import com.ljy.librarymanager.widget.DeleteDialog;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/4/11.
 */

public class ManagerBookActivity extends BaseActivity implements ManagerBookView {

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
    private ProgressDialog pg;
    private FragmentTransaction ft;
    private LoadingFragment loadingFragment;
    private static final String TAG_LOADING_FRAGMENT = "LOADING_FRAGMENT";

    private String category;
    private int more = 0;

    @Inject
    ManagerBookPresenter mPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_booklist);
        //注入对象
        mActivityComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(ManagerBookActivity.this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage(getString(R.string.waiting));
        pg.setCancelable(false);
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
        mAdapter.setOnItemLongClickListener(new BookListAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                DeleteDialog deleteDialog = new DeleteDialog(ManagerBookActivity.this);
                deleteDialog.setOnConfirmListener(new DeleteDialog.OnConfirmListener() {
                    @Override
                    public void onConfirmListener() {
                        showProgress();
                        Books books = new Books();
                        books.setObjectId(mData.get(position).getObjectId());
                        mPresenter.delete(books);
                    }
                });
                deleteDialog.show();
            }
        });
        mAdapter.setOnItemClickListener(new BookListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ManagerBookActivity.this, ManagerBookInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book",mData.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.manager_toolbar_add: {
                        Intent intent = new Intent(ManagerBookActivity.this,AddBookActivity.class);
                        intent.putExtra("category",category);
                        startActivity(intent);
                        break;
                    }
                    case R.id.manager_toolbar_search:{
                        Intent intent = new Intent(ManagerBookActivity.this, SearchBarActivity.class);
                        intent.putExtra("searchType","book");
                        startActivity(intent);
                        break;
                    }
                }
                return false;
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
        list.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void loadMore() {
                more += 10;
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
        Toast.makeText(ManagerBookActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setList(List<Books> data) {
        mData = data;
        if(data.size()==0){
            loadingFragment.setText(getString(R.string.no_data));
            showProgress();
        }else{
            loadingFragment.setText(getString(R.string.loading));
        }
        mAdapter.setNewData(mData);
    }

    @Override
    public void deleteSuccess() {
        hideProgress();
        mPresenter.getList(category, 0);
        more = 0;
        Toast.makeText(this, "删除成功！", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_manager_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getList(category, 0);
    }
}
