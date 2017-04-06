package com.ljy.librarymanager.mvp.ui.fragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.view.BorrowListView;
import com.ljy.librarymanager.mvp.view.HomeListView;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/16.
 */

public class BorrowListFragment extends BaseFragment implements BorrowListView {


    @Inject
    Activity mActivity;

    @Inject
    public BorrowListFragment() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_borrowlist, container, false);
        mFragmentComponent.inject(this);
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void setList() {

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
}
