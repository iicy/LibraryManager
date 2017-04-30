package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.model.AddBookModel;
import com.ljy.librarymanager.mvp.model.ManagerBookInfoModel;
import com.ljy.librarymanager.mvp.view.AddBookView;
import com.ljy.librarymanager.mvp.view.ManagerBookInfoView;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ManagerBookInfoPresenter extends BasePresenter<ManagerBookInfoView, String> {

    private ManagerBookInfoModel managerBookInfoModel;

    @Inject
    public ManagerBookInfoPresenter(ManagerBookInfoModel managerBookInfoModel) {
        this.managerBookInfoModel = managerBookInfoModel;
    }

    public void save(Books book) {
        managerBookInfoModel.save(this,book);
    }

    public void uploadPic(BmobFile pic){
        managerBookInfoModel.uploadPic(this,pic);
    }


    public void getCategoryList(){
        managerBookInfoModel.getCategoryList(this);
    }

    @Override
    public void success(String s) {
        super.success(s);
        mView.saveSuccess();
    }

    public void uploadPicSuccess(BmobFile pic){
        mView.uploadPicSuccess(pic);
    }

    public void getCategorySuccess(List<Category> list){
        mView.getCategory(list);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
