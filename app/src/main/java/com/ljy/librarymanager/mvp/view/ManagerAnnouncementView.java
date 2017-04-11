package com.ljy.librarymanager.mvp.view;

import com.ljy.librarymanager.mvp.base.BaseView;
import com.ljy.librarymanager.mvp.entity.Announcement;

import java.util.List;

/**
 * Created by luojiayu on 2017/4/6.
 */

public interface ManagerAnnouncementView extends BaseView{

    void setList(List<Announcement> list);
}
