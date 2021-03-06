package com.ljy.librarymanager.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.CommentListAdapter;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Collection;
import com.ljy.librarymanager.mvp.entity.Comment;
import com.ljy.librarymanager.mvp.presenter.BookInfoPresenter;
import com.ljy.librarymanager.mvp.view.BookInfoView;
import com.ljy.librarymanager.widget.FullyLinearLayoutManager;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;

import java.util.List;

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
    @BindView(R.id.bt_collect)
    Button bt_collect;
    @BindView(R.id.bt_undo_booking)
    Button bt_undo_booking;
    @BindView(R.id.bt_undo_collect)
    Button bt_undo_collect;
    @BindView(R.id.bt_send_comment)
    Button bt_send_comment;
    @BindView(R.id.no_comment)
    TextView no_comment;
    @BindView(R.id.list)
    LoadMoreRecyclerView list;

    private ProgressDialog pg;

    private Books book;
    private String account;
    private String bookingId;
    private String collectionId;
    private boolean isManager;
    private List<Comment> mList;
    private CommentListAdapter mAdapter;
    private int more = 0;

    @Inject
    BookInfoPresenter mPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_book_info);
        //注入对象
        mActivityComponent.inject(this);
        mPresenter.attachView(this);
        pg = new ProgressDialog(BookInfoActivity.this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage(getString(R.string.waiting));
        pg.setCancelable(false);
    }

    @Override
    protected void init() {
        showProgress();
        account = getIntent().getStringExtra("account");
        book = (Books) getIntent().getSerializableExtra("book");
        isManager = getIntent().getBooleanExtra("isManager", false);
        mToolbar.setTitle("");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        mAdapter = new CommentListAdapter(this, mList);
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(this);
        list.setNestedScrollingEnabled(false);
        //设置布局管理器
        list.setLayoutManager(linearLayoutManager);
//        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(mAdapter);

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
        tv_bookName.setText("书名：" + book.getBookName());
        tv_bookAuthor.setText("作者：" + book.getAuthor());
        tv_bookCategory.setText("分类：" + book.getCategory());
        tv_bookPublication.setText("出版社：" + book.getPublication());
        tv_bookPublicationDate.setText("出版日期：" + book.getPublicationDate().getDate().split(" ")[0]);
        tv_bookStock.setText("库存：" + book.getStock());
        tv_bookSummary.setText("简介：\n\n" + book.getSummary());
        if (isManager) {
            bt_booking.setVisibility(View.GONE);
            bt_collect.setVisibility(View.GONE);
            bt_undo_booking.setVisibility(View.GONE);
            bt_undo_collect.setVisibility(View.GONE);
            bt_send_comment.setVisibility(View.GONE);
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
        bt_booking.setOnClickListener(this);
        bt_collect.setOnClickListener(this);
        bt_undo_booking.setOnClickListener(this);
        bt_undo_collect.setOnClickListener(this);
        bt_send_comment.setOnClickListener(this);
        list.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void loadMore() {
                more += 10;
                mPresenter.getComments(book.getObjectId(), more);
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
            case R.id.bt_booking: {
                showProgress();
                Booking booking = new Booking();
                if(book.getPic()!=null){
                    booking.setPic(book.getPic());
                }
                booking.setUser(account);
                booking.setBookName(book.getBookName());
                booking.setBookId(book.getObjectId());
                mPresenter.addBooking(booking);
                break;
            }
            case R.id.bt_collect: {
                showProgress();
                Collection collection = new Collection();
                if(book.getPic()!=null){
                    collection.setPic(book.getPic());
                }
                collection.setUser(account);
                collection.setBookName(book.getBookName());
                collection.setBookId(book.getObjectId());
                mPresenter.addCollection(collection);
                break;
            }
            case R.id.bt_undo_booking: {
                showProgress();
                if (bookingId != null) {
                    Booking booking = new Booking();
                    booking.setObjectId(bookingId);
                    mPresenter.cancelBooking(booking);
                }
                break;
            }
            case R.id.bt_undo_collect: {
                showProgress();
                if (collectionId != null) {
                    Collection collection = new Collection();
                    collection.setObjectId(collectionId);
                    mPresenter.cancelCollect(collection);
                }
                break;
            }
            case R.id.bt_send_comment:{
                Intent intent = new Intent(BookInfoActivity.this,SendCommentActivity.class);
                intent.putExtra("account",account);
                intent.putExtra("bookId",book.getObjectId());
                startActivity(intent);
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
        Toast.makeText(BookInfoActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void success() {
        mPresenter.hasBooking(account, book.getObjectId());
        mPresenter.hasCollect(account, book.getObjectId());
    }

    @Override
    public void hasBooking(boolean result, String id) {
        if (!isManager) {
            if (result) {
                bt_booking.setVisibility(View.GONE);
                bt_undo_booking.setVisibility(View.VISIBLE);
                bookingId = id;
            } else {
                bt_booking.setVisibility(View.VISIBLE);
                bt_undo_booking.setVisibility(View.GONE);
                bookingId = null;
            }
        }
        hideProgress();
    }

    @Override
    public void hasCollect(boolean result, String id) {
        if (!isManager) {
            if (result) {
                bt_collect.setVisibility(View.GONE);
                bt_undo_collect.setVisibility(View.VISIBLE);
                collectionId = id;
            } else {
                bt_collect.setVisibility(View.VISIBLE);
                bt_undo_collect.setVisibility(View.GONE);
                collectionId = null;
            }
        }
        hideProgress();
    }

    @Override
    public void getComments(List<Comment> data) {
        mList = data;
        if (mList.size() == 0 || mList == null) {
            no_comment.setVisibility(View.VISIBLE);
        } else {
            no_comment.setVisibility(View.GONE);
            mAdapter.setNewData(data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.hasBooking(account, book.getObjectId());
        mPresenter.hasCollect(account, book.getObjectId());
        mPresenter.getComments(book.getObjectId(), 0);
    }
}
