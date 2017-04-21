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
import com.ljy.librarymanager.adapter.BorrowListAdapter;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.presenter.ManagerBorrowPresenter;
import com.ljy.librarymanager.mvp.ui.activity.ManagerBorrowInfoActivity;
import com.ljy.librarymanager.mvp.view.ManagerBookingView;
import com.ljy.librarymanager.mvp.view.ManagerBorrowView;
import com.ljy.librarymanager.widget.DeleteDialog;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/3/16.
 */

public class ManagerBorrowFragment extends BaseFragment implements ManagerBorrowView {

    @BindView(R.id.list)
    LoadMoreRecyclerView list;

    @Inject
    Activity mActivity;
    @Inject
    ManagerBorrowPresenter mPresenter;

    private List<Borrow> mData;
    private BorrowListAdapter mAdapter;
    private ProgressDialog pg;


    @Inject
    public ManagerBorrowFragment() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_manager_borrow, container, false);
        mFragmentComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(getActivity());
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage("正在删除！");
        pg.setCancelable(false);
        mAdapter = new BorrowListAdapter(getActivity(),mData,true);
        return view;
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemLongClickListener(new BorrowListAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                DeleteDialog deleteDialog = new DeleteDialog(getActivity());
                deleteDialog.setOnConfirmListener(new DeleteDialog.OnConfirmListener() {
                    @Override
                    public void onConfirmListener() {
                        showProgress();
                        Borrow borrow = new Borrow();
                        borrow.setObjectId(mData.get(position).getObjectId());
                        mPresenter.delete(borrow);
                    }
                });
                deleteDialog.show();
            }
        });
        mAdapter.setOnItemClickListener(new BorrowListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mPresenter.getBorrow(mData.get(position));
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.getList();
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(mAdapter);
    }

    @Override
    public void setList(List<Borrow> data) {
        mData = data;
        mAdapter.setNewData(mData);
    }

    @Override
    public void deleteSuccess() {
        hideProgress();
        mPresenter.getList();
        Toast.makeText(getActivity(), "删除成功！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void getBorrowSuccess(Books book, Borrow borrow) {
        Intent intent = new Intent(getActivity(), ManagerBorrowInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book",book);
        bundle.putSerializable("borrow",borrow);
        intent.putExtras(bundle);
        startActivity(intent);
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
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getList();
    }
}
