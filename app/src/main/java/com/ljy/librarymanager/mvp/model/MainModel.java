package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.presenter.BookListPresenter;
import com.ljy.librarymanager.mvp.presenter.MainPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class MainModel {

    @Inject
    public MainModel() {
    }

    public void getAllBooks(final MainPresenter mainPresenter) {
        BmobQuery<Books> bmobQuery = new BmobQuery<Books>();
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Books>() {
            @Override
            public void done(List<Books> list, BmobException e) {
                if (e == null) {
                    mainPresenter.success(list);
                } else {
                    mainPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
