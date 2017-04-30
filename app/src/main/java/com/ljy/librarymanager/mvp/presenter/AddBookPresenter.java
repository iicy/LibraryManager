package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.model.AddBookModel;
import com.ljy.librarymanager.mvp.model.AddUserModel;
import com.ljy.librarymanager.mvp.view.AddBookView;
import com.ljy.librarymanager.mvp.view.AddUserView;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class AddBookPresenter extends BasePresenter<AddBookView, String> {

    private AddBookModel addBookModel;

    @Inject
    public AddBookPresenter(AddBookModel addBookModel) {
        this.addBookModel = addBookModel;
    }

    public void add(Books book) {
        addBookModel.add(this,book);
    }

    public void uploadPic(BmobFile pic){
        addBookModel.uploadPic(this,pic);
    }

    public void getCategoryList(){
        addBookModel.getCategoryList(this);
    }

    @Override
    public void success(String s) {
        super.success(s);
        mView.addSuccess();
    }

    public void getCategorySuccess(List<Category> list){
        mView.getCategory(list);
    }

    public void uploadPicSuccess(BmobFile pic){
        mView.uploadPicSuccess(pic);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
