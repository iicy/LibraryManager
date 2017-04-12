package com.ljy.librarymanager.mvp.view;


import com.ljy.librarymanager.mvp.base.BaseView;
import com.ljy.librarymanager.mvp.entity.Announcement;

import java.util.List;
import java.util.Map;

/**
 * Created by jiayu on 2017/3/11.
 */

public interface SearchBarView extends BaseView {

    void searchAnnouncement(List<Announcement> mData);

}
