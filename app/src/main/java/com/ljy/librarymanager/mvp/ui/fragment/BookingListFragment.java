package com.ljy.librarymanager.mvp.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.BookingListAdapter;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.presenter.BookingListPresenter;
import com.ljy.librarymanager.mvp.ui.activity.BookInfoActivity;
import com.ljy.librarymanager.mvp.ui.activity.BookListActivity;
import com.ljy.librarymanager.mvp.ui.activity.MainActivity;
import com.ljy.librarymanager.mvp.view.BookingListView;
import com.ljy.librarymanager.mvp.view.HomeListView;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/3/16.
 */

public class BookingListFragment extends BaseFragment implements BookingListView {

    @BindView(R.id.list)
    LoadMoreRecyclerView list;

    @Inject
    Activity mActivity;
    @Inject
    BookingListPresenter mPresenter;

    private List<Booking> mData;
    private BookingListAdapter mAdapter;
    private ProgressDialog pg;

    @Inject
    public BookingListFragment() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_bookinglist, container, false);
        mFragmentComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(getActivity());
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage("请稍候！");
        pg.setCancelable(false);
        mAdapter = new BookingListAdapter(getActivity(),mData,false);
        return view;
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new BookingListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                pg.show();
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
    public void setList(List<Booking> data) {
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
        pg.dismiss();
        Intent intent = new Intent(getActivity(), BookInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book",book);
        intent.putExtras(bundle);
        intent.putExtra("account",MainActivity.instance.getAccount());
        startActivity(intent);
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
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getList(MainActivity.instance.getAccount());
    }
}
