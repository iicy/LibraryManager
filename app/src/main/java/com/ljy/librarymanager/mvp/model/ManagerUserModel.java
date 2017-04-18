package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.ManagerAnnouncementPresenter;
import com.ljy.librarymanager.mvp.presenter.ManagerUserPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ManagerUserModel {

    @Inject
    public ManagerUserModel() {
    }

    public void getList(final ManagerUserPresenter managerUserPresenter) {
        BmobQuery<User> bmobQuery = new BmobQuery<User>();
        bmobQuery.order("-updatedAt");
        bmobQuery.setLimit(10);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    managerUserPresenter.success(list);
                } else {
                    managerUserPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void delete(final ManagerUserPresenter managerUserPresenter,User user){
        user.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    managerUserPresenter.deleteSuccess();
                } else {
                    managerUserPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
