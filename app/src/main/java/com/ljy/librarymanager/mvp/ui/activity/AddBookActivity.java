package com.ljy.librarymanager.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.AddBookPresenter;
import com.ljy.librarymanager.mvp.presenter.AddUserPresenter;
import com.ljy.librarymanager.mvp.view.AddBookView;
import com.ljy.librarymanager.mvp.view.AddUserView;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by luojiayu on 2017/4/11.
 */

public class AddBookActivity extends BaseActivity implements AddBookView {

    @BindView(R.id.add_book_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.pic)
    ImageView pic;
    @BindView(R.id.book_name)
    EditText ed_bookName;
    @BindView(R.id.book_author)
    EditText ed_bookAuthor;
    @BindView(R.id.book_category)
    EditText ed_bookCategory;
    @BindView(R.id.book_publication)
    EditText ed_bookPublication;
    @BindView(R.id.book_publication_date)
    EditText ed_bookPublicationDate;
    @BindView(R.id.book_stock)
    EditText ed_bookStock;
    @BindView(R.id.book_summary)
    EditText ed_bookSummary;
    @BindView(R.id.bt_save)
    Button bt_save;
    private ProgressDialog pg;
    private String category;

    @Inject
    AddBookPresenter mPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_add_book);
        //注入对象
        mActivityComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(AddBookActivity.this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage("正在保存！");
        pg.setCancelable(false);
        category = getIntent().getStringExtra("category");
    }

    @Override
    protected void init() {
        mToolbar.setTitle("添加书籍");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        ed_bookCategory.setText(category);
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
            case R.id.bt_save: {
                showProgress();
                Books book = new Books();
                book.setBookName(ed_bookName.getText().toString());
                book.setAuthor(ed_bookAuthor.getText().toString());
                book.setCategory(ed_bookCategory.getText().toString());
                book.setPublication(ed_bookPublication.getText().toString());
//                book.setPublicationDate(ed_bookPublicationDate.getText().toString());
                book.setPublicationDate(BmobDate.createBmobDate("yyyy-MM-dd HH:mm:ss",ed_bookPublicationDate.getText().toString()));
                book.setStock(Integer.parseInt(ed_bookStock.getText().toString()));
                book.setSummary(ed_bookSummary.getText().toString());
                mPresenter.add(book);
                break;
            }
        }
    }

    @Override
    public void add() {
        Toast.makeText(AddBookActivity.this, "保存成功！", Toast.LENGTH_LONG).show();
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
        Toast.makeText(AddBookActivity.this, "保存失败！", Toast.LENGTH_LONG).show();
    }
}
