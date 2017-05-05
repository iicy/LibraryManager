package com.ljy.librarymanager.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.presenter.ManagerBorrowInfoPresenter;
import com.ljy.librarymanager.mvp.view.ManagerBorrowInfoView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/4/11.
 */

public class ManagerBorrowInfoActivity extends BaseActivity implements ManagerBorrowInfoView {

    @BindView(R.id.toolbar)
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
    @BindView(R.id.updateDate)
    TextView updateDate;
    @BindView(R.id.updateManager)
    TextView updateManager;
    @BindView(R.id.is_return)
    RadioButton is_return;
    @BindView(R.id.is_not_return)
    RadioButton is_not_return;
    @BindView(R.id.bt_save)
    Button bt_save;
    private ProgressDialog pg;

    private Books book;
    private Borrow borrow;

    @Inject
    ManagerBorrowInfoPresenter mPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_manager_borrow_info);
        //注入对象
        mActivityComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(ManagerBorrowInfoActivity.this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage(getString(R.string.waiting));
        pg.setCancelable(false);
    }

    @Override
    protected void init() {
        book = (Books) getIntent().getSerializableExtra("book");
        borrow = (Borrow) getIntent().getSerializableExtra("borrow");
        mToolbar.setTitle("");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        if(book.getPic()!=null){
            Glide.with(mContext)
                    .load(book.getPic().getUrl())
                    .fitCenter()
                    .placeholder(R.drawable.ic_image)
                    .thumbnail(0.1f)
                    .into(pic);
        }else{
            Glide.clear(pic);
            pic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_image));
        }
        tv_bookName.setText("书名："+book.getBookName());
        tv_bookAuthor.setText("作者："+book.getAuthor());
        tv_bookCategory.setText("分类："+book.getCategory());
        tv_bookPublication.setText("出版社："+book.getPublication());
        tv_bookPublicationDate.setText("出版日期："+book.getPublicationDate().getDate());
        tv_bookStock.setText("库存："+book.getStock());
        tv_bookSummary.setText("简介："+book.getSummary());
        if(borrow.getUpdatedAt()!=null&&!borrow.getUpdatedAt().equals(""))
            updateDate.setText("修改日期："+borrow.getUpdatedAt());
        else
            updateDate.setText("修改日期："+borrow.getCreatedAt());
        updateManager.setText("管理员："+borrow.getManager());
        if(borrow.getStatus().equals("1")){
            is_return.setChecked(true);
            is_not_return.setChecked(false);
        }else{
            is_return.setChecked(false);
            is_not_return.setChecked(true);
        }
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
                if(book.getPic()!=null){
                    borrow.setPic(book.getPic());
                }
                if(is_return.isChecked())
                    borrow.setStatus("1");
                else
                    borrow.setStatus("0");
                mPresenter.save(borrow);
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
        Toast.makeText(ManagerBorrowInfoActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void saveSuccess() {
        Toast.makeText(ManagerBorrowInfoActivity.this, "保存成功！", Toast.LENGTH_LONG).show();
        finish();
    }
}
