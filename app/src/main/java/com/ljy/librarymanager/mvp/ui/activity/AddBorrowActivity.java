package com.ljy.librarymanager.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.AddBorrowPresenter;
import com.ljy.librarymanager.mvp.presenter.AddUserPresenter;
import com.ljy.librarymanager.mvp.view.AddBorrowView;
import com.ljy.librarymanager.mvp.view.AddUserView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/4/11.
 */

public class AddBorrowActivity extends BaseActivity implements AddBorrowView {

    @BindView(R.id.add_borrow_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.username)
    EditText et_username;
    @BindView(R.id.bookid)
    EditText et_bookid;
    @BindView(R.id.is_return)
    RadioButton is_return;
    @BindView(R.id.is_not_return)
    RadioButton is_not_return;
    @BindView(R.id.save)
    Button bt_save;
    private ProgressDialog pg;

    private String manager;

    @Inject
    AddBorrowPresenter mPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_add_borrow);
        //注入对象
        mActivityComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(AddBorrowActivity.this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage("正在保存！");
        pg.setCancelable(false);
        manager = getIntent().getStringExtra("manager");
    }

    @Override
    protected void init() {
        mToolbar.setTitle("创建借阅记录");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
    }

    @Override
    protected void setListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_save.setOnClickListener(this);
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
            case R.id.save: {
                showProgress();
                Borrow borrow = new Borrow();
                borrow.setBookId(et_bookid.getText().toString());
                borrow.setManager(manager);
                borrow.setUser(et_username.getText().toString());
                if(is_not_return.isChecked())
                    borrow.setStatus("0");
                else if(is_return.isChecked())
                    borrow.setStatus("1");
                mPresenter.add(borrow);
                break;
            }
        }
    }

    @Override
    public void add() {
        Toast.makeText(AddBorrowActivity.this, "保存成功！", Toast.LENGTH_LONG).show();
        finish();
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
        Toast.makeText(AddBorrowActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
