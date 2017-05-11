package com.ljy.librarymanager.mvp.ui.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.presenter.ManagerBookInfoPresenter;
import com.ljy.librarymanager.mvp.view.ManagerBookInfoView;
import com.ljy.librarymanager.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by luojiayu on 2017/4/11.
 */

public class ManagerBookInfoActivity extends BaseActivity implements ManagerBookInfoView {

    @BindView(R.id.book_info_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.pic)
    ImageView pic;
    @BindView(R.id.book_name)
    EditText tv_bookName;
    @BindView(R.id.book_author)
    EditText tv_bookAuthor;
    @BindView(R.id.book_category)
    Spinner tv_bookCategory;
    @BindView(R.id.book_publication)
    EditText tv_bookPublication;
    @BindView(R.id.book_publication_date)
    TextView tv_bookPublicationDate;
    @BindView(R.id.book_stock)
    EditText tv_bookStock;
    @BindView(R.id.book_summary)
    EditText tv_bookSummary;
    @BindView(R.id.bt_manager_comments)
    Button bt_manager_comments;
    @BindView(R.id.bt_save)
    Button bt_save;
    @BindView(R.id.bt_reset)
    Button bt_reset;
    private ProgressDialog pg;
    private String category;
    private List<String> categoryList;
    private ArrayAdapter<String> spinnerAdapter;
    int defaultIndex = 0;
    private Books book;
    private File sdcardTempFile;
    private Calendar c = Calendar.getInstance();

    @Inject
    ManagerBookInfoPresenter mPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_manager_book_info);
        //注入对象
        mActivityComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(ManagerBookInfoActivity.this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage(getString(R.string.waiting));
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
        category = book.getCategory();
        mPresenter.getCategoryList();
        reset();
    }

    @Override
    protected void setListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_manager_comments.setOnClickListener(this);
        bt_save.setOnClickListener(this);
        bt_reset.setOnClickListener(this);
        tv_bookPublicationDate.setOnClickListener(this);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.PICK");
                intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 100);
            }
        });
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
            case R.id.book_publication_date: {
                // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
                new DatePickerDialog(ManagerBookInfoActivity.this,
                        // 绑定监听器
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String month;
                                String day;
                                if ((monthOfYear + 1) < 10) {
                                    month = "0" + (monthOfYear + 1);
                                } else {
                                    month = "" + (monthOfYear + 1);
                                }
                                if (dayOfMonth < 10) {
                                    day = "0" + dayOfMonth;
                                } else {
                                    day = "" + dayOfMonth;
                                }
                                tv_bookPublicationDate.setText(year + "-" + month
                                        + "-" + day);
                            }
                        }
                        // 设置初始日期
                        , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH)).show();
                break;
            }
            case R.id.bt_manager_comments: {
                Intent intent = new Intent(ManagerBookInfoActivity.this, ManagerCommentsActivity.class);
                intent.putExtra("bookId", book.getObjectId());
                startActivity(intent);
                break;
            }
            case R.id.bt_save: {
                showProgress();
                if (sdcardTempFile == null) {
                    save(book);
                } else {
                    BmobFile pic = new BmobFile(sdcardTempFile);
                    mPresenter.uploadPic(pic);
                }
                break;
            }
            case R.id.bt_reset: {
                reset();
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
        Toast.makeText(ManagerBookInfoActivity.this, message, Toast.LENGTH_LONG).show();
    }

    public void save(Books book) {
        book.setBookName(tv_bookName.getText().toString());
        book.setAuthor(tv_bookAuthor.getText().toString());
        book.setCategory(tv_bookCategory.getSelectedItem().toString());
        book.setPublication(tv_bookPublication.getText().toString());
        book.setPublicationDate(new BmobDate(new Date()).createBmobDate("yyyy-MM-dd", tv_bookPublicationDate.getText().toString()));
        book.setStock(Integer.parseInt(tv_bookStock.getText().toString()));
        book.setSummary(tv_bookSummary.getText().toString());
        mPresenter.save(book);
    }

    @Override
    public void saveSuccess() {
        Toast.makeText(ManagerBookInfoActivity.this, "保存成功！", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void uploadPicSuccess(BmobFile pic) {
        book.setPic(pic);
        save(book);
    }

    @Override
    public void reset() {
        if(book.getPic()!=null){
            Glide.with(mContext)
                    .load(book.getPic().getUrl())
                    .fitCenter()
                    .placeholder(R.drawable.ic_image)
                    .thumbnail(0.1f)
                    .into(pic);
        }else{
            Glide.clear(pic);
            pic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.img_add));
        }
        tv_bookName.setText(book.getBookName());
        tv_bookAuthor.setText(book.getAuthor());
        tv_bookCategory.setSelection(defaultIndex);
        tv_bookPublication.setText(book.getPublication());
        tv_bookPublicationDate.setText(book.getPublicationDate().getDate().split(" ")[0]);
        tv_bookStock.setText(book.getStock() + "");
        tv_bookSummary.setText(book.getSummary());
    }

    @Override
    public void getCategory(List<Category> data) {
        hideProgress();
        categoryList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            categoryList.add(data.get(i).getCategory_name());
            if (data.get(i).getCategory_name().equals(category)) {
                defaultIndex = i;
            }
        }
        spinnerAdapter = new ArrayAdapter<>(this, R.layout.item_spinner, R.id.spinner_item, categoryList);
        spinnerAdapter.setDropDownViewResource(R.layout.item_spinner);
        tv_bookCategory.setAdapter(spinnerAdapter);
        tv_bookCategory.setSelection(defaultIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (data != null) {
                Uri uri = data.getData();
                Glide.with(this)
                        .load(uri)
                        .fitCenter()
                        .placeholder(R.drawable.ic_image)
                        .thumbnail(0.1f)
                        .into(pic);
                sdcardTempFile = new File(FileUtil.getPath(uri, this));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
