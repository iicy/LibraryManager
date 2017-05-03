package com.ljy.librarymanager.mvp.ui.fragment;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.AnnouncementListAdapter;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.presenter.HomeListPresenter;
import com.ljy.librarymanager.mvp.view.HomeListView;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/3/16.
 */

public class HomeListFragment extends BaseFragment implements HomeListView {

    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.loading)
    FrameLayout loading;
    @BindView(R.id.list)
    LoadMoreRecyclerView list;

    @Inject
    Activity mActivity;
    @Inject
    HomeListPresenter homeListPresenter;

    private List<Announcement> mData;
    private AnnouncementListAdapter mAdapter;
    private FragmentTransaction ft;
    private LoadingFragment loadingFragment;
    private static final String TAG_LOADING_FRAGMENT = "LOADING_FRAGMENT";

    @Inject
    public HomeListFragment() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_homelist, container, false);
        mFragmentComponent.inject(this);
        homeListPresenter.attachView(this);
        loadingFragment = new LoadingFragment();
        return view;
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadingFragment.setText("正在加载...");
                homeListPresenter.getList();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initData() {
        mAdapter = new AnnouncementListAdapter(getActivity(),mData);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(mAdapter);
    }

    @Override
    public void setList(List<Announcement> list) {
        mData = list;
        if(list.size()==0){
            loadingFragment.setText("暂无数据");
            showProgress();
        }else{
            loadingFragment.setText("正在加载...");
        }
        mAdapter.setNewData(mData);
    }

    @Override
    public void showProgress() {
        ft = getChildFragmentManager().beginTransaction();
        if(getChildFragmentManager().findFragmentByTag(TAG_LOADING_FRAGMENT)==null){
            ft.add(R.id.loading, loadingFragment, TAG_LOADING_FRAGMENT);
        }
        ft.show(loadingFragment);
        loading.setVisibility(View.VISIBLE);
        ft.commit();
    }

    @Override
    public void hideProgress() {
        ft = getChildFragmentManager().beginTransaction();
        loadingFragment =(LoadingFragment) getChildFragmentManager().findFragmentByTag(TAG_LOADING_FRAGMENT);
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
        homeListPresenter.getList();
    }

}
