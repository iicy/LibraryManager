package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.model.CategoryListModel;
import com.ljy.librarymanager.mvp.view.CategoryListView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class CategoryListPresenter extends BasePresenter<CategoryListView, List<Category>> {

    private CategoryListModel categoryListModel;

    @Inject
    public CategoryListPresenter(CategoryListModel categoryListModel) {
        this.categoryListModel = categoryListModel;
    }

    public void getList() {
        mView.showProgress();
        categoryListModel.getList(this);
    }

    @Override
    public void success(List<Category> data) {
        super.success(data);
        mView.setList(data);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
