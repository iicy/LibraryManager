package com.ljy.librarymanager.mvp.model;

import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.ModifyPasswordPresenter;

import java.util.List;

import javax.inject.Inject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by luojiayu on 2017/3/15.
 */

public class ModifyPasswordModel {

    @Inject
    public ModifyPasswordModel() {
    }

    public void modify(final ModifyPasswordPresenter modifyPasswordPresenter, final User user, String account) {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("account",account);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null&&list.size()==1) {
                    user.update(list.get(0).getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                modifyPasswordPresenter.success("success");
                            } else {
                                modifyPasswordPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });
                } else {
                    modifyPasswordPresenter.onError("bmobFail:" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

}
