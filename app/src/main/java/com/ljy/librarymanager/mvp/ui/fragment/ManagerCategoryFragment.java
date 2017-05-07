package com.ljy.librarymanager.mvp.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.CategoryListAdapter;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.presenter.ManagerCategoryPresenter;
import com.ljy.librarymanager.mvp.ui.activity.ManagerActivity;
import com.ljy.librarymanager.mvp.ui.activity.ManagerBookActivity;
import com.ljy.librarymanager.mvp.ui.activity.SearchBarActivity;
import com.ljy.librarymanager.mvp.view.ManagerCategoryView;
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

public class ManagerCategoryFragment extends BaseFragment implements ManagerCategoryView {

    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.loading)
    FrameLayout loading;
    @BindView(R.id.list)
    LoadMoreRecyclerView mList;

    @Inject
    Activity mActivity;

    @Inject
    ManagerCategoryPresenter mPresenter;

    private List<Category> mData;
    private CategoryListAdapter mAdapter;
    private ProgressDialog pg;
    private FragmentTransaction ft;
    private LoadingFragment loadingFragment;
    private static final String TAG_LOADING_FRAGMENT = "LOADING_FRAGMENT";
    private Observable<ManagerCategoryFragment> observable;

    @Inject
    public ManagerCategoryFragment() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_manager_category, container, false);
        mFragmentComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(getActivity());
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage(getString(R.string.waiting));
        pg.setCancelable(false);
        mAdapter = new CategoryListAdapter(getActivity(),mData);
        loadingFragment = new LoadingFragment();
        return view;
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemLongClickListener(new CategoryListAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                DeleteDialog deleteDialog = new DeleteDialog(getActivity());
                deleteDialog.setOnConfirmListener(new DeleteDialog.OnConfirmListener() {
                    @Override
                    public void onConfirmListener() {
                        pg.show();
                        Category category = new Category();
                        category.setObjectId(mData.get(position).getObjectId());
                        category.setCategory_name(mData.get(position).getCategory_name());
                        mPresenter.delete(category);
                    }
                });
                deleteDialog.show();
            }
        });
        mAdapter.setOnItemClickListener(new CategoryListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ManagerBookActivity.class);
                intent.putExtra("category",mData.get(position).getCategory_name());
                startActivity(intent);
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
        mList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mList.setAdapter(mAdapter);
    }

    @Override
    public void setList(List<Category> data) {
        mData = data;
        if(data.size()==0){
            loadingFragment.setText(getString(R.string.no_data));
            showProgress();
        }else{
            loadingFragment.setText(getString(R.string.loading));
        }
        mAdapter.setNewData(mData);
        observable = RxBus.getInstance().register("searchCategory", ManagerCategoryFragment.class);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ManagerCategoryFragment>() {
                    @Override
                    public void call(ManagerCategoryFragment mData) {
                        Intent intent = new Intent(getActivity(), SearchBarActivity.class);
                        intent.putExtra("searchType","category");
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
        Toast.makeText(getActivity(), "无法删除！该分类不为空！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregister("searchCategory", observable);
    }

}
