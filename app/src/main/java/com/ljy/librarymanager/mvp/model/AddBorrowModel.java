package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.AddBorrowPresenter;
import com.ljy.librarymanager.mvp.presenter.AddUserPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class AddBorrowModel {

    @Inject
    public AddBorrowModel() {
    }

    public void add(final AddBorrowPresenter addBorrowPresenter, final Borrow borrow) {
        borrow.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    addBorrowPresenter.success(s);
                } else {
                    addBorrowPresenter.onError("保存失败！");
                }
            }
        });
    }

    public void checkUserAndBook(final AddBorrowPresenter addBorrowPresenter, final Borrow borrow) {
        BmobQuery<Books> bmobQuery = new BmobQuery<Books>();
        bmobQuery.addWhereEqualTo("objectId", borrow.getBookId());
        bmobQuery.findObjects(new FindListener<Books>() {
            @Override
            public void done(List<Books> list, BmobException e) {
                if (e == null && list.size() != 0) {
                    final String bookName = list.get(0).getBookName();
                    final BmobFile pic = list.get(0).getPic();
                    BmobQuery<User> bmobQuery = new BmobQuery<User>();
                    bmobQuery.addWhereEqualTo("account", borrow.getUser());
                    bmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if (e == null && list.size() != 0) {
                                addBorrowPresenter.checkSuccess(borrow, bookName,pic);
                            } else {
                                addBorrowPresenter.onError("该用户或图书不存在！");
                            }
                        }
                    });
                } else {
                    addBorrowPresenter.onError("该用户或图书不存在！");
                }
            }
        });
    }

}
