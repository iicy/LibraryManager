package com.ljy.librarymanager.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.CategoryListAdapter;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.presenter.CategoryListPresenter;
import com.ljy.librarymanager.mvp.ui.activity.BookListActivity;
import com.ljy.librarymanager.mvp.ui.activity.MainActivity;
import com.ljy.librarymanager.mvp.view.BorrowListView;
import com.ljy.librarymanager.mvp.view.CategoryListView;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/3/16.
 */

public class CategoryListFragment extends BaseFragment implements CategoryListView {

    @BindView(R.id.list)
    LoadMoreRecyclerView mList;

    @Inject
    Activity mActivity;
    @Inject
    CategoryListPresenter mPresenter;

    private List<Category> mData;
    private CategoryListAdapter mAdapter;

    @Inject
    public CategoryListFragment() {
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_categorylist, container, false);
        mFragmentComponent.inject(this);
        mPresenter.attachView(this);
        mAdapter = new CategoryListAdapter(getActivity(),mData);
        return view;
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new CategoryListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), BookListActivity.class);
                intent.putExtra("category",mData.get(position).getCategory_name());
                intent.putExtra("account", MainActivity.instance.getAccount());
                startActivity(intent);
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
            MainActivity.instance.hasData(false);
            showProgress();
        }else {
            MainActivity.instance.hasData(true);
        }
        mAdapter.setNewData(mData);
    }

    @Override
    public void showProgress() {
        MainActivity.instance.showProgress();
    }

    @Override
    public void hideProgress() {
        MainActivity.instance.hideProgress();
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
