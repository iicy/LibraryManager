package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.model.AddBorrowModel;
import com.ljy.librarymanager.mvp.model.AddUserModel;
import com.ljy.librarymanager.mvp.view.AddBorrowView;
import com.ljy.librarymanager.mvp.view.AddUserView;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class AddBorrowPresenter extends BasePresenter<AddBorrowView, String> {

    private AddBorrowModel addBorrowModel;

    @Inject
    public AddBorrowPresenter(AddBorrowModel addBorrowModel) {
        this.addBorrowModel = addBorrowModel;
    }

    public void add(Borrow borrow) {
        addBorrowModel.checkUserAndBook(this,borrow);
    }

    @Override
    public void success(String s) {
        super.success(s);
        mView.add();
    }

    public void checkSuccess(Borrow borrow,String bookName){
        borrow.setBookName(bookName);
        addBorrowModel.add(this,borrow);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
