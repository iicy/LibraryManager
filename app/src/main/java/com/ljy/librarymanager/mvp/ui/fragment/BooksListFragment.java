package com.ljy.librarymanager.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.WelcomeActivity;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.ui.activity.LoginActivity;
import com.ljy.librarymanager.mvp.view.BooksListView;

import javax.inject.Inject;

import butterknife.BindView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by luojiayu on 2017/3/16.
 */

public class BooksListFragment extends BaseFragment implements BooksListView {

    @BindView(R.id.logout)
    Button logout;

    @Inject
    Activity mActivity;

    @Inject
    public BooksListFragment() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_bookslist, container, false);
        mFragmentComponent.inject(this);
        return view;
    }

    @Override
    protected void initListener() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("loginFlag", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("login", false);
                editor.commit();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
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
