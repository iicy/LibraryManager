package com.ljy.librarymanager.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljy.librarymanager.MyApplication;
import com.ljy.librarymanager.di.component.DaggerActivityComponent;
import com.ljy.librarymanager.di.component.DaggerFragmentComponent;
import com.ljy.librarymanager.di.component.FragmentComponent;
import com.ljy.librarymanager.di.module.FragmentModule;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    private View mRootView;
    protected FragmentComponent mFragmentComponent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //创建dagger组件
        mFragmentComponent = DaggerFragmentComponent.builder()
                .appComponent(MyApplication.getInstance().getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
        mRootView = initView(inflater,container);
        ButterKnife.bind(this, mRootView);//绑定到butterknife
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
        initData();
    }

    protected abstract View initView(LayoutInflater inflater,ViewGroup container);
    protected abstract void initListener();
    protected abstract void initData();
}
