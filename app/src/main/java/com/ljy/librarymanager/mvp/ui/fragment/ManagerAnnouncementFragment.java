package com.ljy.librarymanager.mvp.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.AnnouncementListAdapter;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.presenter.ManagerAnnouncementPresenter;
import com.ljy.librarymanager.mvp.ui.activity.LoginActivity;
import com.ljy.librarymanager.mvp.ui.activity.ManagerActivity;
import com.ljy.librarymanager.mvp.ui.activity.ManagerAnnouncementInfoActivity;
import com.ljy.librarymanager.mvp.ui.activity.SearchBarActivity;
import com.ljy.librarymanager.mvp.view.ManagerAnnouncementView;
import com.ljy.librarymanager.utils.RxBus;
import com.ljy.librarymanager.widget.DeleteDialog;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by luojiayu on 2017/3/16.
 */

public class ManagerAnnouncementFragment extends BaseFragment implements ManagerAnnouncementView {

    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.loading)
    FrameLayout loading;
    @BindView(R.id.list)
    LoadMoreRecyclerView list;

    @Inject
    Activity mActivity;
    @Inject
    ManagerAnnouncementPresenter mPresenter;

    private List<Announcement> mData;
    private AnnouncementListAdapter mAdapter;
    private ProgressDialog pg;
    private FragmentTransaction ft;
    private LoadingFragment loadingFragment;
    private static final String TAG_LOADING_FRAGMENT = "LOADING_FRAGMENT";
    private Observable<ManagerAnnouncementFragment> observable;

    private String account;

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
        pg.setMessage("请稍候！");
        pg.setCancelable(false);
        mAdapter = new AnnouncementListAdapter(getActivity(), mData);
        account = ManagerActivity.instance.getAccount();
        loadingFragment = new LoadingFragment();
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
                        pg.show();
                        Announcement announcement = new Announcement();
                        announcement.setObjectId(mData.get(position).getObjectId());
                        mPresenter.delete(announcement);
                    }
                });
                deleteDialog.show();
            }
        });
        mAdapter.setOnItemClickListener(new AnnouncementListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ManagerAnnouncementInfoActivity.class);
                Bundle bundle = new Bundle();
                Announcement announcement = mData.get(position);
                announcement.setAccount(account);
                bundle.putSerializable("announcement", announcement);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadingFragment.setText("正在加载...");
                mPresenter.getList();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initData() {
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(mAdapter);
    }

    @Override
    public void setList(List<Announcement> list) {
        mData = list;
        if (list.size() == 0) {
            loadingFragment.setText("暂无数据");
            showProgress();
        } else {
            loadingFragment.setText("正在加载...");
        }
        mAdapter.setNewData(mData);
        observable = RxBus.getInstance().register("searchAnnounce", ManagerAnnouncementFragment.class);
        observable.subscribeOn(Schedulers.io())
                .map(new Func1<ManagerAnnouncementFragment, List<Announcement>>() {
                    @Override
                    public List<Announcement> call(ManagerAnnouncementFragment managerBookingFragment) {
                        return mData;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Announcement>>() {
                    @Override
                    public void call(List<Announcement> mData) {
                        Intent intent = new Intent(getActivity(), SearchBarActivity.class);
                        intent.putExtra("list", (Serializable) mData);
                        intent.putExtra("searchType","announcement");
                        intent.putExtra("account",account);
                        startActivity(intent);
                    }
                });
    }

    @Override
    public void deleteSuccess() {
        pg.dismiss();
        mPresenter.getList();
        Toast.makeText(getActivity(), "删除成功！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        ft = getChildFragmentManager().beginTransaction();
        if (getChildFragmentManager().findFragmentByTag(TAG_LOADING_FRAGMENT) == null) {
            ft.add(R.id.loading, loadingFragment, TAG_LOADING_FRAGMENT);
        }
        ft.show(loadingFragment);
        loading.setVisibility(View.VISIBLE);
        ft.commit();
    }

    @Override
    public void hideProgress() {
        ft = getChildFragmentManager().beginTransaction();
        loadingFragment = (LoadingFragment) getChildFragmentManager().findFragmentByTag(TAG_LOADING_FRAGMENT);
        ft.hide(loadingFragment);
        loading.setVisibility(View.GONE);
        ft.commit();
    }

    @Override
    public void showMsg(String message) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister("searchAnnounce", observable);
    }
}
