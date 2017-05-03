package com.ljy.librarymanager.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.BorrowListAdapter;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.presenter.BorrowListPresenter;
import com.ljy.librarymanager.mvp.ui.activity.BookInfoActivity;
import com.ljy.librarymanager.mvp.ui.activity.MainActivity;
import com.ljy.librarymanager.mvp.view.BorrowListView;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/3/16.
 */

public class BorrowListFragment extends BaseFragment implements BorrowListView {

    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.loading)
    FrameLayout loading;
    @BindView(R.id.list)
    LoadMoreRecyclerView list;

    @Inject
    Activity mActivity;
    @Inject
    BorrowListPresenter mPresenter;

    private List<Borrow> mData;
    private BorrowListAdapter mAdapter;
    private FragmentTransaction ft;
    private LoadingFragment loadingFragment;
    private static final String TAG_LOADING_FRAGMENT = "LOADING_FRAGMENT";

    @Inject
    public BorrowListFragment() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_borrowlist, container, false);
        mFragmentComponent.inject(this);
        mPresenter.attachView(this);
        mAdapter = new BorrowListAdapter(getActivity(), mData, false);
        loadingFragment = new LoadingFragment();
        return view;
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new BorrowListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mPresenter.getBook(mData.get(position).getBookId());
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadingFragment.setText("正在加载...");
                mPresenter.getList(MainActivity.instance.getAccount());
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initData() {
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(mAdapter);
    }

    @Override
    public void setList(List<Borrow> data) {
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
    public void getBook(Books book) {
        Intent intent = new Intent(getActivity(), BookInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book", book);
        intent.putExtras(bundle);
        intent.putExtra("account", MainActivity.instance.getAccount());
        startActivity(intent);
    }

    @Override
    public void showProgress() {
        ft = getChildFragmentManager().beginTransaction();
        if(getChildFragmentManager().findFragmentByTag(TAG_LOADING_FRAGMENT)==null){
            ft.add(R.id.loading, loadingFragment, TAG_LOADING_FRAGMENT);
        }
        ft.show(loadingFragment);
        loading.setVisibility(View.VISIBLE);
        ft.commit();
    }

    @Override
    public void hideProgress() {
        ft = getChildFragmentManager().beginTransaction();
        loadingFragment =(LoadingFragment) getChildFragmentManager().findFragmentByTag(TAG_LOADING_FRAGMENT);
        ft.hide(loadingFragment);
        loading.setVisibility(View.GONE);
        ft.commit();
    }

    @Override
    public void showMsg(String message) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getList(MainActivity.instance.getAccount());
    }

}
