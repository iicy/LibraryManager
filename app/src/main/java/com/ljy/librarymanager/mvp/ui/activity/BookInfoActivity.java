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
import android.widget.TextView;
import android.widget.Toast;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.AddUserPresenter;
import com.ljy.librarymanager.mvp.view.AddUserView;
import com.ljy.librarymanager.mvp.view.BookInfoView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/4/11.
 */

public class BookInfoActivity extends BaseActivity implements BookInfoView {

    @BindView(R.id.book_info_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.pic)
    ImageView pic;
    @BindView(R.id.book_name)
    TextView tv_bookName;
    @BindView(R.id.book_author)
    TextView tv_bookAuthor;
    @BindView(R.id.book_category)
    TextView tv_bookCategory;
    @BindView(R.id.book_publication)
    TextView tv_bookPublication;
    @BindView(R.id.book_publication_date)
    TextView tv_bookPublicationDate;
    @BindView(R.id.book_stock)
    TextView tv_bookStock;
    @BindView(R.id.book_summary)
    TextView tv_bookSummary;
    @BindView(R.id.bt_booking)
    Button bt_booking;
    private ProgressDialog pg;

    private Books book;

    @Inject
    AddUserPresenter mPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_book_info);
        //注入对象
        mActivityComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(BookInfoActivity.this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage("正在预订！");
        pg.setCancelable(false);
    }

    @Override
    protected void init() {
        book = (Books) getIntent().getSerializableExtra("book");
        mToolbar.setTitle("");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        tv_bookName.setText("书名："+book.getBookName());
        tv_bookAuthor.setText("作者："+book.getAuthor());
        tv_bookCategory.setText("分类："+book.getCategory());
        tv_bookPublication.setText("出版社："+book.getPublication());
        tv_bookPublicationDate.setText("出版日期："+book.getPublicationDate().getDate());
        tv_bookStock.setText("库存："+book.getStock());
        tv_bookSummary.setText("简介："+book.getSummary());
    }

    @Override
    protected void setListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_booking.setOnClickListener(this);
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
            case R.id.bt_booking: {
                showProgress();
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
        Toast.makeText(BookInfoActivity.this, "", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setInfo(Books data) {

    }
}
