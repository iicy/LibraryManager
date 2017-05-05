package com.ljy.librarymanager.mvp.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by luojiayu on 2017/5/5.
 */

@Entity
public class SearchHistory {

    @Id(autoincrement = true)
    private Long id;
    private String account;
    private String content;

    @Generated(hash = 15699545)
    public SearchHistory(Long id, String account, String content) {
        this.id = id;
        this.account = account;
        this.content = content;
    }

    @Generated(hash = 1905904755)
    public SearchHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
