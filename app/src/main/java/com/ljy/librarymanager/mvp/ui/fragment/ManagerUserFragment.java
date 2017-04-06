package com.ljy.librarymanager.mvp.ui.fragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.view.ManagerCategoryView;
import com.ljy.librarymanager.mvp.view.ManagerUserView;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/16.
 */

public class ManagerUserFragment extends BaseFragment implements ManagerUserView {


    @Inject
    Activity mActivity;

    @Inject
    public ManagerUserFragment() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_manager_user, container, false);
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
