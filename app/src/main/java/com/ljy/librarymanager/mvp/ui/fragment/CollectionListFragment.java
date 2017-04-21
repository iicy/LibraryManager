package com.ljy.librarymanager.mvp.ui.fragment;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.CollectionListAdapter;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.entity.Collection;
import com.ljy.librarymanager.mvp.presenter.CollectionListPresenter;
import com.ljy.librarymanager.mvp.ui.activity.MainActivity;
import com.ljy.librarymanager.mvp.view.CategoryListView;
import com.ljy.librarymanager.mvp.view.CollectionListView;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/3/16.
 */

public class CollectionListFragment extends BaseFragment implements CollectionListView {

    @BindView(R.id.list)
    LoadMoreRecyclerView list;

    @Inject
    Activity mActivity;
    @Inject
    CollectionListPresenter mPresenter;

    private List<Collection> mData;
    private CollectionListAdapter mAdapter;

    @Inject
    public CollectionListFragment() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_collectionlist, container, false);
        mFragmentComponent.inject(this);
        mPresenter.attachView(this);
        mAdapter = new CollectionListAdapter(getActivity(),mData);
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mPresenter.getList(MainActivity.instance.getAccount());
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(mAdapter);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String message) {

    }

    @Override
    public void setList(List<Collection> data) {
        mData = data;
        mAdapter.setNewData(mData);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getList(MainActivity.instance.getAccount());
    }
}
