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
import com.ljy.librarymanager.adapter.UserListAdapter;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.ManagerUserPresenter;
import com.ljy.librarymanager.mvp.ui.activity.ManagerActivity;
import com.ljy.librarymanager.mvp.ui.activity.ManagerBookActivity;
import com.ljy.librarymanager.mvp.ui.activity.ManagerBookInfoActivity;
import com.ljy.librarymanager.mvp.ui.activity.ManagerUserInfoActivity;
import com.ljy.librarymanager.mvp.view.ManagerCategoryView;
import com.ljy.librarymanager.mvp.view.ManagerUserView;
import com.ljy.librarymanager.widget.DeleteDialog;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/3/16.
 */

public class ManagerUserFragment extends BaseFragment implements ManagerUserView {

    @BindView(R.id.list)
    LoadMoreRecyclerView list;

    @Inject
    Activity mActivity;
    @Inject
    ManagerUserPresenter mPresenter;

    private List<User> mData;
    private UserListAdapter mAdapter;
    private ProgressDialog pg;

    @Inject
    public ManagerUserFragment() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_manager_user, container, false);
        mFragmentComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(getActivity());
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage("正在删除！");
        pg.setCancelable(false);
        mAdapter = new UserListAdapter(getActivity(), mData);
        return view;
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemLongClickListener(new UserListAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                DeleteDialog deleteDialog = new DeleteDialog(getActivity());
                deleteDialog.setOnConfirmListener(new DeleteDialog.OnConfirmListener() {
                    @Override
                    public void onConfirmListener() {
                        pg.show();
                        User user = new User();
                        user.setObjectId(mData.get(position).getObjectId());
                        mPresenter.delete(user);
                    }
                });
                deleteDialog.show();
            }
        });
        mAdapter.setOnItemClickListener(new UserListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ManagerUserInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",mData.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(mAdapter);
    }

    @Override
    public void setList(List<User> data) {
        mData = data;
        if(data.size()==0){
            ManagerActivity.instance.hasData(false);
            showProgress();
        }else {
            ManagerActivity.instance.hasData(true);
        }
        mAdapter.setNewData(mData);
    }

    @Override
    public void deleteSuccess() {
        pg.dismiss();
        mPresenter.getList();
        Toast.makeText(getActivity(), "删除成功！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        ManagerActivity.instance.showProgress();
    }

    @Override
    public void hideProgress() {
        ManagerActivity.instance.hideProgress();
    }

    @Override
    public void showMsg(String message) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getList();
    }
}
