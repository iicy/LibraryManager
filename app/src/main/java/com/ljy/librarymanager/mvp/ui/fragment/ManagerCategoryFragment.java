package com.ljy.librarymanager.mvp.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.CategoryListAdapter;
import com.ljy.librarymanager.mvp.base.BaseFragment;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.presenter.ManagerCategoryPresenter;
import com.ljy.librarymanager.mvp.ui.activity.BookInfoActivity;
import com.ljy.librarymanager.mvp.ui.activity.BookListActivity;
import com.ljy.librarymanager.mvp.ui.activity.ManagerActivity;
import com.ljy.librarymanager.mvp.ui.activity.ManagerBookActivity;
import com.ljy.librarymanager.mvp.view.ManagerBorrowView;
import com.ljy.librarymanager.mvp.view.ManagerCategoryView;
import com.ljy.librarymanager.widget.DeleteDialog;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/3/16.
 */

public class ManagerCategoryFragment extends BaseFragment implements ManagerCategoryView {

    @BindView(R.id.list)
    LoadMoreRecyclerView mList;

    @Inject
    Activity mActivity;

    @Inject
    ManagerCategoryPresenter mPresenter;

    private List<Category> mData;
    private CategoryListAdapter mAdapter;
    private ProgressDialog pg;

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
        pg.setMessage("正在删除！");
        pg.setCancelable(false);
        mAdapter = new CategoryListAdapter(getActivity(),mData);
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
        Toast.makeText(getActivity(), "无法删除！该分类不为空！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getList();
    }
}
