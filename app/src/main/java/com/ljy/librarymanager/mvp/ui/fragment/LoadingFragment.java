package com.ljy.librarymanager.mvp.ui.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.ui.activity.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/4/26.
 */

public class LoadingFragment extends BaseFragment {

    @BindView(R.id.text)
    TextView text;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_loading, container, false);
        return view;
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {

    }

    public void setText(String s) {
        text.setText(s);
    }

}
