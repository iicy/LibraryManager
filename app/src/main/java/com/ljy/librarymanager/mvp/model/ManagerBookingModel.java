package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.presenter.ManagerBookingPresenter;
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

public class ManagerBookingModel {

    @Inject
    public ManagerBookingModel() {
    }

    public void getList(final ManagerBookingPresenter managerBookingPresenter) {
        BmobQuery<Booking> bmobQuery = new BmobQuery<Booking>();
        bmobQuery.order("-createdAt");
        bmobQuery.order("-updatedAt");
//        bmobQuery.setLimit(10);
        bmobQuery.findObjects(new FindListener<Booking>() {
            @Override
            public void done(List<Booking> list, BmobException e) {
                if (e == null) {
                    managerBookingPresenter.success(list);
                } else {
                    managerBookingPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void delete(final ManagerBookingPresenter managerBookingPresenter, Booking booking) {
        booking.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    managerBookingPresenter.deleteSuccess();
                } else {
                    managerBookingPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void getBook(final ManagerBookingPresenter managerBookingPresenter, String bookId) {
        BmobQuery<Books> bmobQuery = new BmobQuery<Books>();
        bmobQuery.addWhereEqualTo("objectId", bookId);
        bmobQuery.findObjects(new FindListener<Books>() {
            @Override
            public void done(List<Books> list, BmobException e) {
                if (e == null && list.size() != 0) {
                    managerBookingPresenter.getBookSuccess(list.get(0));
                } else {
                    managerBookingPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
