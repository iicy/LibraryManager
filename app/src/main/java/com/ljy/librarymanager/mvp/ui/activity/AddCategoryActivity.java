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
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.AddCategoryPresenter;
import com.ljy.librarymanager.mvp.presenter.AddUserPresenter;
import com.ljy.librarymanager.mvp.view.AddCategoryView;
import com.ljy.librarymanager.mvp.view.AddUserView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/4/11.
 */

public class AddCategoryActivity extends BaseActivity implements AddCategoryView {

    @BindView(R.id.add_category_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.content)
    EditText et_content;
    @BindView(R.id.save)
    Button bt_save;
    private ProgressDialog pg;

    private String content;

    @Inject
    AddCategoryPresenter mPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_add_category);
        //注入对象
        mActivityComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(AddCategoryActivity.this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage("正在保存！");
        pg.setCancelable(false);
    }

    @Override
    protected void init() {
        mToolbar.setTitle("添加分类");
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
                content = et_content.getText().toString();
                Category category = new Category();
                category.setCategory_name(content);
                mPresenter.add(category);
                break;
            }
        }
    }

    @Override
    public void add() {
        Toast.makeText(AddCategoryActivity.this, "保存成功！", Toast.LENGTH_LONG).show();
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
        Toast.makeText(AddCategoryActivity.this, "保存失败！该分类已存在！", Toast.LENGTH_LONG).show();
    }
}
