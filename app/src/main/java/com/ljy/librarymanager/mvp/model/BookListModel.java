package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.presenter.BookListPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class BookListModel {

    @Inject
    public BookListModel() {
    }

    public void getList(final BookListPresenter bookListPresenter, String category) {
        BmobQuery<Books> bmobQuery = new BmobQuery<Books>();
        bmobQuery.addWhereEqualTo("category",category);
        bmobQuery.order("-createdAt");
//        bmobQuery.setLimit(10);
        bmobQuery.findObjects(new FindListener<Books>() {
            @Override
            public void done(List<Books> list, BmobException e) {
                if (e == null) {
                    bookListPresenter.success(list);
                } else {
                    bookListPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
