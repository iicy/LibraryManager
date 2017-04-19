package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.AddBookPresenter;
import com.ljy.librarymanager.mvp.presenter.AddUserPresenter;

import javax.inject.Inject;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class AddBookModel {

    @Inject
    public AddBookModel() {
    }

    public void add(final AddBookPresenter addBookPresenter, Books book) {
        book.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    addBookPresenter.success(s);
                } else {
                    addBookPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
