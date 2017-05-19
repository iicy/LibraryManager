package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.model.HomeListModel;
import com.ljy.librarymanager.mvp.view.HomeListView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class HomeListPresenter extends BasePresenter<HomeListView, List<Announcement>> {

    private HomeListModel homeListModel;

    @Inject
    public HomeListPresenter(HomeListModel homeListModel) {
        this.homeListModel = homeListModel;
    }

    public void getList(int more) {
        mView.showProgress();
        homeListModel.getList(this, more);
    }

    @Override
    public void success(List<Announcement> data) {
        super.success(data);
        mView.setList(data);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
