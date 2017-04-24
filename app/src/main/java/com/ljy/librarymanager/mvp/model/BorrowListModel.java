package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.presenter.BorrowListPresenter;
import com.ljy.librarymanager.mvp.presenter.ManagerBorrowPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class BorrowListModel {

    @Inject
    public BorrowListModel() {
    }

    public void getList(final BorrowListPresenter borrowListPresenter,String account) {
        BmobQuery<Borrow> bmobQuery = new BmobQuery<Borrow>();
        bmobQuery.addWhereEqualTo("user",account);
        bmobQuery.order("-createdAt");
//        bmobQuery.setLimit(10);
        bmobQuery.findObjects(new FindListener<Borrow>() {
            @Override
            public void done(List<Borrow> list, BmobException e) {
                if (e == null) {
                    borrowListPresenter.success(list);
                } else {
                    borrowListPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void getBook(final BorrowListPresenter borrowListPresenter,String bookId){
        BmobQuery<Books> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("objectId",bookId);
        bmobQuery.findObjects(new FindListener<Books>() {
            @Override
            public void done(List<Books> list, BmobException e) {
                if (e == null&&list.size()>0) {
                    borrowListPresenter.getBookSuccess(list.get(0));
                } else {
                    borrowListPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
