package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.presenter.AddAnnouncementPresenter;
import com.ljy.librarymanager.mvp.presenter.HomeListPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class AddAnnouncementModel {

    @Inject
    public AddAnnouncementModel() {
    }

    public void add(final AddAnnouncementPresenter addAnnouncementPresenter, Announcement announcement) {
        announcement.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    addAnnouncementPresenter.success(s);
                } else {
                    addAnnouncementPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
