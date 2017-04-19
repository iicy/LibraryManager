package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.model.AddAnnouncementModel;
import com.ljy.librarymanager.mvp.model.AddCategoryModel;
import com.ljy.librarymanager.mvp.view.AddAnnouncementView;
import com.ljy.librarymanager.mvp.view.AddCategoryView;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class AddCategoryPresenter extends BasePresenter<AddCategoryView, String> {

    private AddCategoryModel addCategoryModel;

    @Inject
    public AddCategoryPresenter(AddCategoryModel addCategoryModel) {
        this.addCategoryModel = addCategoryModel;
    }

    public void add(Category category) {
        addCategoryModel.add(this,category);
    }

    @Override
    public void success(String s) {
        super.success(s);
        mView.add();
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
