package com.ljy.librarymanager.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.BorrowListAdapter;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.presenter.BorrowListPresenter;
import com.ljy.librarymanager.mvp.ui.activity.BookInfoActivity;
import com.ljy.librarymanager.mvp.ui.activity.MainActivity;
import com.ljy.librarymanager.mvp.view.BorrowListView;
import com.ljy.librarymanager.mvp.view.HomeListView;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/3/16.
 */

public class BorrowListFragment extends BaseFragment implements BorrowListView {

    @BindView(R.id.list)
    LoadMoreRecyclerView list;

    @Inject
    Activity mActivity;
    @Inject
    BorrowListPresenter mPresenter;

    private List<Borrow> mData;
    private BorrowListAdapter mAdapter;

    @Inject
    public BorrowListFragment() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_borrowlist, container, false);
        mFragmentComponent.inject(this);
        mPresenter.attachView(this);
        mAdapter = new BorrowListAdapter(getActivity(),mData,false);
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
    }

    @Override
    protected void initData() {
        mPresenter.getList(MainActivity.instance.getAccount());
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(mAdapter);
    }

    @Override
    public void setList(List<Borrow> data) {
        mData = data;
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
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

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
