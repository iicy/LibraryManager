package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Collection;
import com.ljy.librarymanager.mvp.presenter.BookingListPresenter;
import com.ljy.librarymanager.mvp.presenter.CollectionListPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class CollectionListModel {

    @Inject
    public CollectionListModel() {
    }

    public void getList(final CollectionListPresenter collectionListPresenter, String account) {
        BmobQuery<Collection> bmobQuery = new BmobQuery<Collection>();
        bmobQuery.addWhereEqualTo("user",account);
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Collection>() {
            @Override
            public void done(List<Collection> list, BmobException e) {
                if (e == null) {
                    collectionListPresenter.success(list);
                } else {
                    collectionListPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
