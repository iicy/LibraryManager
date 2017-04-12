package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.presenter.ManagerAnnouncementPresenter;
import com.ljy.librarymanager.mvp.presenter.SearchBarPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class SearchBarModel {

    @Inject
    public SearchBarModel() {
    }

    public void searchAnnouncement(final SearchBarPresenter searchBarPresenter,String key) {
        BmobQuery<Announcement> bmobQuery = new BmobQuery<Announcement>();
//        bmobQuery.addWhereMatches("content",key);
        bmobQuery.order("-updatedAt");
        bmobQuery.findObjects(new FindListener<Announcement>() {
            @Override
            public void done(List<Announcement> list, BmobException e) {
                if (e == null) {
                    searchBarPresenter.searchAnnouncementSuccess(list);
                } else {
                    searchBarPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }


}
