package com.ljy.librarymanager.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.ui.activity.LoginActivity;
import com.ljy.librarymanager.mvp.view.BooksListView;
import com.ljy.librarymanager.mvp.view.HomeListView;

import javax.inject.Inject;

import butterknife.BindView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by luojiayu on 2017/3/16.
 */

public class HomeListFragment extends BaseFragment implements HomeListView {


    @Inject
    Activity mActivity;

    @Inject
    public HomeListFragment() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_homelist, container, false);
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
