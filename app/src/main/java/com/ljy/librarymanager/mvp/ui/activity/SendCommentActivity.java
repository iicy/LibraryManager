package com.ljy.librarymanager.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.entity.Comment;
import com.ljy.librarymanager.mvp.presenter.AddAnnouncementPresenter;
import com.ljy.librarymanager.mvp.presenter.SendCommentPresenter;
import com.ljy.librarymanager.mvp.view.AddAnnouncementView;
import com.ljy.librarymanager.mvp.view.SendCommentView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/4/11.
 */

public class SendCommentActivity extends BaseActivity implements SendCommentView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.content)
    EditText et_content;
    @BindView(R.id.send)
    Button bt_send;
    private ProgressDialog pg;
    private String content;
    private String account;
    private String bookId;

    @Inject
    SendCommentPresenter mPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_send_comment);
        //注入对象
        mActivityComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(SendCommentActivity.this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage("请稍候！");
        pg.setCancelable(false);
    }

    @Override
    protected void init() {
        mToolbar.setTitle("发表评论");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        bookId = intent.getStringExtra("bookId");
    }

    @Override
    protected void setListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_send.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send: {
                showProgress();
                content = et_content.getText().toString();
                Comment comment= new Comment();
                comment.setUser(account);
                comment.setContent(content);
                comment.setBookId(bookId);
                mPresenter.send(comment);
                break;
            }
        }
    }

    @Override
    public void showProgress() {
        pg.show();
    }

    @Override
    public void hideProgress() {
        pg.dismiss();
    }

    @Override
    public void showMsg(String message) {
        Toast.makeText(SendCommentActivity.this, "发送失败！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void send() {
        Toast.makeText(SendCommentActivity.this, "发送成功！", Toast.LENGTH_LONG).show();
        finish();
    }
}
