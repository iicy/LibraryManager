package com.ljy.librarymanager.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.CollectionListAdapter;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Collection;
import com.ljy.librarymanager.mvp.presenter.CollectionListPresenter;
import com.ljy.librarymanager.mvp.ui.activity.BookInfoActivity;
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
        mAdapter.setOnItemClickListener(new CollectionListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mPresenter.getBook(mData.get(position).getBookId());
            }
        });
    }

    @Override
    protected void initData() {
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(mAdapter);
    }

    @Override
    public void showProgress() {
        MainActivity.instance.showProgress();
    }

    @Override
    public void hideProgress() {
        MainActivity.instance.hideProgress();
    }

    @Override
    public void showMsg(String message) {

    }

    @Override
    public void setList(List<Collection> data) {
        mData = data;
        if(data.size()==0){
            MainActivity.instance.hasData(false);
            showProgress();
        }else {
            MainActivity.instance.hasData(true);
        }
        mAdapter.setNewData(mData);
    }

    @Override
    public void getBook(Books book) {
        Intent intent = new Intent(getActivity(), BookInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book",book);
        intent.putExtras(bundle);
        intent.putExtra("account",MainActivity.instance.getAccount());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getList(MainActivity.instance.getAccount());
    }
}
