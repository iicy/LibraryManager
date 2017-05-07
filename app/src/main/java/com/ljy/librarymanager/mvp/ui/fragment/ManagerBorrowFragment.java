package com.ljy.librarymanager.mvp.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.BorrowListAdapter;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.presenter.ManagerBorrowPresenter;
import com.ljy.librarymanager.mvp.ui.activity.ManagerActivity;
import com.ljy.librarymanager.mvp.ui.activity.ManagerBorrowInfoActivity;
import com.ljy.librarymanager.mvp.ui.activity.SearchBarActivity;
import com.ljy.librarymanager.mvp.view.ManagerBorrowView;
import com.ljy.librarymanager.utils.RxBus;
import com.ljy.librarymanager.widget.DeleteDialog;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by luojiayu on 2017/3/16.
 */

public class ManagerBorrowFragment extends BaseFragment implements ManagerBorrowView {

    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.loading)
    FrameLayout loading;
    @BindView(R.id.list)
    LoadMoreRecyclerView list;

    @Inject
    Activity mActivity;
    @Inject
    ManagerBorrowPresenter mPresenter;

    private List<Borrow> mData;
    private BorrowListAdapter mAdapter;
    private ProgressDialog pg;
    private FragmentTransaction ft;
    private LoadingFragment loadingFragment;
    private static final String TAG_LOADING_FRAGMENT = "LOADING_FRAGMENT";
    private Observable<ManagerBorrowFragment> observable;

    @Inject
    public ManagerBorrowFragment() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_manager_borrow, container, false);
        mFragmentComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(getActivity());
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage(getString(R.string.waiting));
        pg.setCancelable(false);
        mAdapter = new BorrowListAdapter(getActivity(),mData,true);
        loadingFragment = new LoadingFragment();
        return view;
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemLongClickListener(new BorrowListAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                DeleteDialog deleteDialog = new DeleteDialog(getActivity());
                deleteDialog.setOnConfirmListener(new DeleteDialog.OnConfirmListener() {
                    @Override
                    public void onConfirmListener() {
                        pg.show();
                        Borrow borrow = new Borrow();
                        borrow.setObjectId(mData.get(position).getObjectId());
                        mPresenter.delete(borrow);
                    }
                });
                deleteDialog.show();
            }
        });
        mAdapter.setOnItemClickListener(new BorrowListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                pg.show();
                mPresenter.getBorrow(mData.get(position));
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadingFragment.setText(getString(R.string.loading));
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
    public void setList(List<Borrow> data) {
        mData = data;
        if(data.size()==0){
            loadingFragment.setText(getString(R.string.no_data));
            showProgress();
        }else{
            loadingFragment.setText(getString(R.string.loading));
        }
        mAdapter.setNewData(mData);
        observable = RxBus.getInstance().register("searchBorrow", ManagerBorrowFragment.class);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ManagerBorrowFragment>() {
                    @Override
                    public void call(ManagerBorrowFragment mData) {
                        Intent intent = new Intent(getActivity(), SearchBarActivity.class);
                        intent.putExtra("searchType","borrow");
                        intent.putExtra("account", ManagerActivity.instance.getAccount());
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
    public void getBorrowSuccess(Books book, Borrow borrow) {
        pg.dismiss();
        Intent intent = new Intent(getActivity(), ManagerBorrowInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book",book);
        bundle.putSerializable("borrow",borrow);
        intent.putExtras(bundle);
        startActivity(intent);
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
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister("searchBorrow", observable);
    }
}
