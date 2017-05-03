package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.presenter.HomeListPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class HomeListModel {

    @Inject
    public HomeListModel() {
    }

    public void getList(final HomeListPresenter homeListPresenter) {
        BmobQuery<Announcement> bmobQuery = new BmobQuery<Announcement>();
        bmobQuery.order("-updatedAt");
        bmobQuery.findObjects(new FindListener<Announcement>() {
            @Override
            public void done(List<Announcement> list, BmobException e) {
                if (e == null) {
                    homeListPresenter.success(list);
                } else {
                    homeListPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
