package com.ljy.librarymanager.mvp.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.AnnouncementListAdapter;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.presenter.ManagerAnnouncementPresenter;
import com.ljy.librarymanager.mvp.ui.activity.LoginActivity;
import com.ljy.librarymanager.mvp.view.ManagerAnnouncementView;
import com.ljy.librarymanager.widget.DeleteDialog;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by luojiayu on 2017/3/16.
 */

public class ManagerAnnouncementFragment extends BaseFragment implements ManagerAnnouncementView {

    @BindView(R.id.list)
    LoadMoreRecyclerView list;

    @Inject
    Activity mActivity;
    @Inject
    ManagerAnnouncementPresenter mPresenter;

    private List<Announcement> mData;
    private AnnouncementListAdapter mAdapter;
    private ProgressDialog pg;

    @Inject
    public ManagerAnnouncementFragment() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_manager_announcement, container, false);
        mFragmentComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(getActivity());
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage("正在删除！");
        pg.setCancelable(false);
        mAdapter = new AnnouncementListAdapter(getActivity(),mData);
        return view;
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemLongClickListener(new AnnouncementListAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                DeleteDialog deleteDialog = new DeleteDialog(getActivity());
                deleteDialog.setOnConfirmListener(new DeleteDialog.OnConfirmListener() {
                    @Override
                    public void onConfirmListener() {
                        showProgress();
                        Announcement announcement = new Announcement();
                        announcement.setObjectId(mData.get(position).getObjectId());
                        mPresenter.delete(announcement);
                    }
                });
                deleteDialog.show();
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
    public void setList(List<Announcement> list) {
        mData = list;
        mAdapter.setNewData(mData);
    }

    @Override
    public void deleteSuccess() {
        hideProgress();
        mPresenter.getList();
        Toast.makeText(getActivity(), "删除成功！", Toast.LENGTH_LONG).show();
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

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getList();
    }
}
