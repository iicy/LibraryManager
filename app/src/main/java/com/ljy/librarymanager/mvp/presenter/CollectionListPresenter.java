package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Collection;
import com.ljy.librarymanager.mvp.model.BookingListModel;
import com.ljy.librarymanager.mvp.model.CollectionListModel;
import com.ljy.librarymanager.mvp.view.BookingListView;
import com.ljy.librarymanager.mvp.view.CollectionListView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class CollectionListPresenter extends BasePresenter<CollectionListView, List<Collection>> {

    private CollectionListModel collectionListModel;

    @Inject
    public CollectionListPresenter(CollectionListModel collectionListModel) {
        this.collectionListModel = collectionListModel;
    }

    public void getList(String account) {
        collectionListModel.getList(this,account);
    }

    @Override
    public void success(List<Collection> data) {
        super.success(data);
        mView.setList(data);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
