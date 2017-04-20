package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.presenter.ManagerBookInfoPresenter;
import com.ljy.librarymanager.mvp.presenter.ManagerBookPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ManagerBookInfoModel {

    @Inject
    public ManagerBookInfoModel() {
    }

    public void save(final ManagerBookInfoPresenter managerBookInfoPresenter, Books books) {
        books.update(books.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    managerBookInfoPresenter.success("success");
                } else {
                    managerBookInfoPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }


}
