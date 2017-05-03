package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.presenter.ManagerAnnouncementPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ManagerAnnouncementModel {

    @Inject
    public ManagerAnnouncementModel() {
    }

    public void getList(final ManagerAnnouncementPresenter managerAnnouncementPresenter) {
        BmobQuery<Announcement> bmobQuery = new BmobQuery<Announcement>();
        bmobQuery.order("-updatedAt");
        bmobQuery.setLimit(10);
        bmobQuery.findObjects(new FindListener<Announcement>() {
            @Override
            public void done(List<Announcement> list, BmobException e) {
                if (e == null) {
                    managerAnnouncementPresenter.success(list);
                } else {
                    managerAnnouncementPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void delete(final ManagerAnnouncementPresenter managerAnnouncementPresenter,Announcement announcement){
        announcement.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    managerAnnouncementPresenter.deleteSuccess();
                } else {
                    managerAnnouncementPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
