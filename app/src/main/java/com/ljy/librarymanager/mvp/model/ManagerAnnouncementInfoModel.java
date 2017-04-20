package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.presenter.ManagerAnnouncementInfoPresenter;
import com.ljy.librarymanager.mvp.presenter.ManagerBookInfoPresenter;

import javax.inject.Inject;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ManagerAnnouncementInfoModel {

    @Inject
    public ManagerAnnouncementInfoModel() {
    }

    public void save(final ManagerAnnouncementInfoPresenter managerAnnouncementInfoPresenter, Announcement announcement) {
        announcement.update(announcement.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    managerAnnouncementInfoPresenter.success("success");
                } else {
                    managerAnnouncementInfoPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }


}
