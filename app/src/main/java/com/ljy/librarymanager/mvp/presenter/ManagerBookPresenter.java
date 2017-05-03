package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.model.ManagerBookModel;
import com.ljy.librarymanager.mvp.view.ManagerBookView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ManagerBookPresenter extends BasePresenter<ManagerBookView, List<Books>> {

    private ManagerBookModel managerBookModel;

    @Inject
    public ManagerBookPresenter(ManagerBookModel managerBookModel) {
        this.managerBookModel = managerBookModel;
    }

    public void getList(String category) {
        mView.showProgress();
        managerBookModel.getList(this,category);
    }

    public void delete(Books books){
        managerBookModel.delete(this,books);
    }

    @Override
    public void success(List<Books> data) {
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
