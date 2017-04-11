package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.model.AddAnnouncementModel;
import com.ljy.librarymanager.mvp.model.HomeListModel;
import com.ljy.librarymanager.mvp.view.AddAnnouncementView;
import com.ljy.librarymanager.mvp.view.HomeListView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class AddAnnouncementPresenter extends BasePresenter<AddAnnouncementView, String> {

    private AddAnnouncementModel addAnnouncementModel;

    @Inject
    public AddAnnouncementPresenter(AddAnnouncementModel addAnnouncementModel) {
        this.addAnnouncementModel = addAnnouncementModel;
    }

    public void add(Announcement announcement) {
        addAnnouncementModel.add(this,announcement);
    }

    @Override
    public void success(String s) {
        super.success(s);
        mView.add();
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
