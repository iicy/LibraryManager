package com.ljy.librarymanager.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.CommentListAdapter;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.Comment;
import com.ljy.librarymanager.mvp.presenter.ManagerCommentsPresenter;
import com.ljy.librarymanager.mvp.ui.fragment.LoadingFragment;
import com.ljy.librarymanager.mvp.view.ManagerCommentsView;
import com.ljy.librarymanager.widget.DeleteDialog;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/4/11.
 */

public class ManagerCommentsActivity extends BaseActivity implements ManagerCommentsView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.loading)
    FrameLayout loading;
    @BindView(R.id.list)
    LoadMoreRecyclerView list;

    private List<Comment> mData;
    private CommentListAdapter mAdapter;
    private FragmentTransaction ft;
    private LoadingFragment loadingFragment;
    private static final String TAG_LOADING_FRAGMENT = "LOADING_FRAGMENT";
    private ProgressDialog pg;

    private String bookId;

    @Inject
    ManagerCommentsPresenter mPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_manager_comments);
        //注入对象
        mActivityComponent.inject(this);
        mPresenter.attachView(this);
        mAdapter = new CommentListAdapter(this, mData);
        loadingFragment = new LoadingFragment();
        pg = new ProgressDialog(this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage(getString(R.string.waiting));
        pg.setCancelable(false);
    }

    @Override
    protected void init() {
        bookId = getIntent().getStringExtra("bookId");
        mToolbar.setTitle("评论管理");
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
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadingFragment.setText(getString(R.string.loading));
                mPresenter.getComments(bookId);
                refreshLayout.setRefreshing(false);
            }
        });
        mAdapter.setOnItemLongClickListener(new CommentListAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                DeleteDialog deleteDialog = new DeleteDialog(ManagerCommentsActivity.this);
                deleteDialog.setOnConfirmListener(new DeleteDialog.OnConfirmListener() {
                    @Override
                    public void onConfirmListener() {
                        pg.show();
                        Comment comment = new Comment();
                        comment.setObjectId(mData.get(position).getObjectId());
                        mPresenter.delete(comment);
                    }
                });
                deleteDialog.show();
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
        Toast.makeText(ManagerCommentsActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getComments(bookId);
    }

    @Override
    public void deleteSuccess() {
        pg.dismiss();
        Toast.makeText(ManagerCommentsActivity.this, "删除成功！", Toast.LENGTH_LONG).show();
        mPresenter.getComments(bookId);
    }

    @Override
    public void getComments(List<Comment> data) {
        mData = data;
        if (data.size() == 0) {
            loadingFragment.setText(getString(R.string.no_data));
            showProgress();
        } else {
            loadingFragment.setText(getString(R.string.loading));
        }
        mAdapter.setNewData(mData);
    }
}
