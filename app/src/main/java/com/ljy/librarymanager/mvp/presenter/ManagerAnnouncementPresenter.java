package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.model.HomeListModel;
import com.ljy.librarymanager.mvp.model.ManagerAnnouncementModel;
import com.ljy.librarymanager.mvp.view.HomeListView;
import com.ljy.librarymanager.mvp.view.ManagerAnnouncementView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ManagerAnnouncementPresenter extends BasePresenter<ManagerAnnouncementView, List<Announcement>> {

    private ManagerAnnouncementModel managerAnnouncementModel;

    @Inject
    public ManagerAnnouncementPresenter(ManagerAnnouncementModel managerAnnouncementModel) {
        this.managerAnnouncementModel = managerAnnouncementModel;
    }

    public void getList() {
        managerAnnouncementModel.getList(this);
    }

    public void delete(Announcement announcement){
        managerAnnouncementModel.delete(this,announcement);
    }

    @Override
    public void success(List<Announcement> data) {
        super.success(data);
        mView.setList(data);
    }

    public void deleteSuccess(){
        mView.deleteSuccess();
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
