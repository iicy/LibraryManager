package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Collection;
import com.ljy.librarymanager.mvp.presenter.BookInfoPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class BookInfoModel {

    @Inject
    public BookInfoModel() {
    }

    public void addBooking(final BookInfoPresenter bookInfoPresenter, Booking booking) {
        booking.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    bookInfoPresenter.success(s);
                } else {
                    bookInfoPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void addCollection(final BookInfoPresenter bookInfoPresenter, Collection collection) {
        collection.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    bookInfoPresenter.success(s);
                } else {
                    bookInfoPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void hasBooking(final BookInfoPresenter bookInfoPresenter, String user,String bookId) {
        BmobQuery<Booking> bmobQuery = new BmobQuery<Booking>();
        bmobQuery.addWhereEqualTo("user", user);
        bmobQuery.addWhereEqualTo("bookId", bookId);
        bmobQuery.findObjects(new FindListener<Booking>() {
            @Override
            public void done(List<Booking> list, BmobException e) {
                if (e == null && list.size() != 0) {
                    bookInfoPresenter.hasBooking(true,list.get(0).getObjectId());
                } else if (e == null && list.size() == 0) {
                    bookInfoPresenter.hasBooking(false,null);
                } else {
                    bookInfoPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void hasCollect(final BookInfoPresenter bookInfoPresenter, String user,String bookId) {
        BmobQuery<Collection> bmobQuery = new BmobQuery<Collection>();
        bmobQuery.addWhereEqualTo("user", user);
        bmobQuery.addWhereEqualTo("bookId", bookId);
        bmobQuery.findObjects(new FindListener<Collection>() {
            @Override
            public void done(List<Collection> list, BmobException e) {
                if (e == null && list.size() != 0) {
                    bookInfoPresenter.hasCollect(true,list.get(0).getObjectId());
                } else if (e == null && list.size() == 0) {
                    bookInfoPresenter.hasCollect(false,null);
                } else {
                    bookInfoPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void cancelBooking(final BookInfoPresenter bookInfoPresenter, Booking booking) {
        booking.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    bookInfoPresenter.success("");
                } else {
                    bookInfoPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void cancelCollect(final BookInfoPresenter bookInfoPresenter, Collection collection) {
        collection.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    bookInfoPresenter.success("");
                } else {
                    bookInfoPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
}
