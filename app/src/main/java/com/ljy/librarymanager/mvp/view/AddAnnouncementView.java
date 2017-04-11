package com.ljy.librarymanager.mvp.view;


import com.ljy.librarymanager.mvp.base.BaseView;
import com.ljy.librarymanager.mvp.entity.Announcement;

/**
 * Created by jiayu on 2017/3/11.
 */

public interface AddAnnouncementView extends BaseView {

    void add(Announcement announcement);
}
