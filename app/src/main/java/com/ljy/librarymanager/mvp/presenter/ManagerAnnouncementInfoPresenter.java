package com.ljy.librarymanager.mvp.presenter;

import com.ljy.librarymanager.mvp.base.BasePresenter;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.model.ManagerAnnouncementInfoModel;
import com.ljy.librarymanager.mvp.view.ManagerAnnouncementInfoView;

import javax.inject.Inject;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ManagerAnnouncementInfoPresenter extends BasePresenter<ManagerAnnouncementInfoView, String> {

    private ManagerAnnouncementInfoModel managerAnnouncementInfoModel;

    @Inject
    public ManagerAnnouncementInfoPresenter(ManagerAnnouncementInfoModel managerAnnouncementInfoModel) {
        this.managerAnnouncementInfoModel = managerAnnouncementInfoModel;
    }

    public void save(Announcement announcement) {
        managerAnnouncementInfoModel.save(this,announcement);
    }

    @Override
    public void success(String s) {
        super.success(s);
        mView.saveSuccess();
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }
}
