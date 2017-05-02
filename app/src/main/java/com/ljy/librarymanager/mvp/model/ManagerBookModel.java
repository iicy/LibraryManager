package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.presenter.ManagerBookPresenter;
import com.ljy.librarymanager.mvp.presenter.ManagerCategoryPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ManagerBookModel {

    @Inject
    public ManagerBookModel() {
    }

    public void getAllBooks(final ManagerBookPresenter managerBookPresenter) {
        BmobQuery<Books> bmobQuery = new BmobQuery<Books>();
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Books>() {
            @Override
            public void done(List<Books> list, BmobException e) {
                if (e == null) {
                    managerBookPresenter.getAllSuccess(list);
                } else {
                    managerBookPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void getList(final ManagerBookPresenter managerBookPresenter,String category) {
        BmobQuery<Books> bmobQuery = new BmobQuery<Books>();
        bmobQuery.addWhereEqualTo("category",category);
        bmobQuery.order("-createdAt");
//        bmobQuery.setLimit(10);
        bmobQuery.findObjects(new FindListener<Books>() {
            @Override
            public void done(List<Books> list, BmobException e) {
                if (e == null) {
                    managerBookPresenter.success(list);
                } else {
                    managerBookPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void delete(final ManagerBookPresenter managerBookPresenter,Books books){
        books.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    managerBookPresenter.deleteSuccess();
                } else {
                    managerBookPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
