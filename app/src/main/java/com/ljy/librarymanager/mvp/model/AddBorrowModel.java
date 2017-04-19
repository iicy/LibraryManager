package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.AddBorrowPresenter;
import com.ljy.librarymanager.mvp.presenter.AddUserPresenter;

import javax.inject.Inject;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class AddBorrowModel {

    @Inject
    public AddBorrowModel() {
    }

    public void add(final AddBorrowPresenter addBorrowPresenter, Borrow borrow) {
        borrow.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    addBorrowPresenter.success(s);
                } else {
                    addBorrowPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
