package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.model.ManagerCategoryModel;
import com.ljy.librarymanager.mvp.view.ManagerCategoryView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ManagerCategoryPresenter extends BasePresenter<ManagerCategoryView, List<Category>> {

    private ManagerCategoryModel managerCategoryModel;

    @Inject
    public ManagerCategoryPresenter(ManagerCategoryModel managerCategoryModel) {
        this.managerCategoryModel = managerCategoryModel;
    }

    public void getList() {
        mView.showProgress();
        managerCategoryModel.getList(this);
    }

    public void delete(Category category){
        managerCategoryModel.delete(this,category);
    }

    @Override
    public void success(List<Category> data) {
        super.success(data);
        mView.setList(data);
    }

    public void deleteSuccess(){
        mView.deleteSuccess();
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
