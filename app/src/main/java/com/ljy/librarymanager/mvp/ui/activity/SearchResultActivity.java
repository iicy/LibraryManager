package com.ljy.librarymanager.mvp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ljy.librarymanager.MyApplication;
import com.ljy.librarymanager.R;
import com.ljy.librarymanager.adapter.AnnouncementListAdapter;
import com.ljy.librarymanager.adapter.BookListAdapter;
import com.ljy.librarymanager.adapter.BookingListAdapter;
import com.ljy.librarymanager.adapter.BorrowListAdapter;
import com.ljy.librarymanager.adapter.CategoryListAdapter;
import com.ljy.librarymanager.adapter.UserListAdapter;
import com.ljy.librarymanager.mvp.base.BaseActivity;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Borrow;
import com.ljy.librarymanager.mvp.entity.Category;
import com.ljy.librarymanager.mvp.entity.User;
import com.ljy.librarymanager.mvp.presenter.SearchResultPresenter;
import com.ljy.librarymanager.mvp.view.SearchResultView;
import com.ljy.librarymanager.widget.DeleteDialog;
import com.ljy.librarymanager.widget.LoadMoreRecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by luojiayu on 2017/5/2.
 */

public class SearchResultActivity extends BaseActivity implements SearchResultView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.no_data)
    TextView no_data;
    @BindView(R.id.list)
    LoadMoreRecyclerView list;

    private ProgressDialog pg;
    private List<Announcement> announcementList;
    private List<Booking> bookingList;
    private List<Books> booksList;
    private List<Borrow> borrowList;
    private List<User> userList;
    private List<Category> categoryList;
    private AnnouncementListAdapter announcementListAdapter;
    private BookingListAdapter bookingListAdapter;
    private BookListAdapter bookListAdapter;
    private BorrowListAdapter borrowListAdapter;
    private UserListAdapter userListAdapter;
    private CategoryListAdapter categoryListAdapter;
    private String searchType;
    private String account;
    private String identity;

    @Inject
    SearchResultPresenter mPresenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_search_result);
        mActivityComponent.inject(this);
        mPresenter.attachView(this);
    }

    @Override
    protected void init() {
        pg = new ProgressDialog(this);
        pg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg.setMessage("请稍候！");
        pg.setCancelable(false);

        mToolbar.setTitle("搜索结果");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));

        searchType = getIntent().getStringExtra("searchType");
        account = getIntent().getStringExtra("account");
        identity = getIntent().getStringExtra("identity");

        if (searchType.equals("announcement")) {
            announcementList = (List<Announcement>) getIntent().getSerializableExtra("list");
            announcementListAdapter = new AnnouncementListAdapter(this, announcementList);
            list.setLayoutManager(new LinearLayoutManager(this));
            list.setAdapter(announcementListAdapter);
            if (announcementList.size() < 1) {
                no_data.setVisibility(View.VISIBLE);
            }
        } else if (searchType.equals("booking")) {
            bookingList = (List<Booking>) getIntent().getSerializableExtra("list");
            bookingListAdapter = new BookingListAdapter(this, bookingList, true);
            list.setLayoutManager(new LinearLayoutManager(this));
            list.setAdapter(bookingListAdapter);
            if (bookingList.size() < 1) {
                no_data.setVisibility(View.VISIBLE);
            }
        } else if (searchType.equals("book")) {
            booksList = (List<Books>) getIntent().getSerializableExtra("list");
            bookListAdapter = new BookListAdapter(this, booksList);
            list.setLayoutManager(new LinearLayoutManager(this));
            list.setAdapter(bookListAdapter);
            if (booksList.size() < 1) {
                no_data.setVisibility(View.VISIBLE);
            }
        } else if (searchType.equals("borrow")) {
            borrowList = (List<Borrow>) getIntent().getSerializableExtra("list");
            borrowListAdapter = new BorrowListAdapter(this, borrowList, true);
            list.setLayoutManager(new LinearLayoutManager(this));
            list.setAdapter(borrowListAdapter);
            if (borrowList.size() < 1) {
                no_data.setVisibility(View.VISIBLE);
            }
        } else if (searchType.equals("user")) {
            userList = (List<User>) getIntent().getSerializableExtra("list");
            userListAdapter = new UserListAdapter(this, userList);
            list.setLayoutManager(new LinearLayoutManager(this));
            list.setAdapter(userListAdapter);
            if (userList.size() < 1) {
                no_data.setVisibility(View.VISIBLE);
            }
        } else if (searchType.equals("category")) {
            categoryList = (List<Category>) getIntent().getSerializableExtra("list");
            categoryListAdapter = new CategoryListAdapter(this, categoryList);
            list.setLayoutManager(new LinearLayoutManager(this));
            list.setAdapter(categoryListAdapter);
            if (categoryList.size() < 1) {
                no_data.setVisibility(View.VISIBLE);
            }
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

        if (announcementListAdapter != null) {
            announcementListAdapter.setOnItemClickListener(new AnnouncementListAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(SearchResultActivity.this, ManagerAnnouncementInfoActivity.class);
                    Bundle bundle = new Bundle();
                    Announcement announcement = announcementList.get(position);
                    announcement.setAccount(account);
                    bundle.putSerializable("announcement", announcement);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            announcementListAdapter.setOnItemLongClickListener(new AnnouncementListAdapter.OnRecyclerViewItemLongClickListener() {
                @Override
                public void onItemLongClick(View view, final int position) {
                    DeleteDialog deleteDialog = new DeleteDialog(SearchResultActivity.this);
                    deleteDialog.setOnConfirmListener(new DeleteDialog.OnConfirmListener() {
                        @Override
                        public void onConfirmListener() {
                            pg.show();
                            Announcement announcement = new Announcement();
                            announcement.setObjectId(announcementList.get(position).getObjectId());
                            mPresenter.deleteAnnouncement(announcement);
                        }
                    });
                    deleteDialog.show();
                }
            });
        }

        if (bookingListAdapter != null) {
            bookingListAdapter.setOnItemClickListener(new BookingListAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    mPresenter.getBook(bookingList.get(position).getBookId());
                }
            });
            bookingListAdapter.setOnItemLongClickListener(new BookingListAdapter.OnRecyclerViewItemLongClickListener() {
                @Override
                public void onItemLongClick(View view, final int position) {
                    DeleteDialog deleteDialog = new DeleteDialog(SearchResultActivity.this);
                    deleteDialog.setOnConfirmListener(new DeleteDialog.OnConfirmListener() {
                        @Override
                        public void onConfirmListener() {
                            pg.show();
                            Booking booking = new Booking();
                            booking.setObjectId(bookingList.get(position).getObjectId());
                            mPresenter.deleteBooking(booking);
                        }
                    });
                    deleteDialog.show();
                }
            });
        }

        if (borrowListAdapter != null) {
            borrowListAdapter.setOnItemClickListener(new BorrowListAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    mPresenter.getBorrow(borrowList.get(position));
                }
            });
            borrowListAdapter.setOnItemLongClickListener(new BorrowListAdapter.OnRecyclerViewItemLongClickListener() {
                @Override
                public void onItemLongClick(View view, final int position) {
                    DeleteDialog deleteDialog = new DeleteDialog(SearchResultActivity.this);
                    deleteDialog.setOnConfirmListener(new DeleteDialog.OnConfirmListener() {
                        @Override
                        public void onConfirmListener() {
                            pg.show();
                            Borrow borrow = new Borrow();
                            borrow.setObjectId(borrowList.get(position).getObjectId());
                            mPresenter.deleteBorrow(borrow);
                        }
                    });
                    deleteDialog.show();
                }
            });
        }

        if (categoryListAdapter != null) {
            categoryListAdapter.setOnItemClickListener(new CategoryListAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(SearchResultActivity.this, ManagerBookActivity.class);
                    intent.putExtra("category", categoryList.get(position).getCategory_name());
                    startActivity(intent);
                }
            });
            categoryListAdapter.setOnItemLongClickListener(new CategoryListAdapter.OnRecyclerViewItemLongClickListener() {
                @Override
                public void onItemLongClick(View view, final int position) {
                    DeleteDialog deleteDialog = new DeleteDialog(SearchResultActivity.this);
                    deleteDialog.setOnConfirmListener(new DeleteDialog.OnConfirmListener() {
                        @Override
                        public void onConfirmListener() {
                            pg.show();
                            Category category = new Category();
                            category.setObjectId(categoryList.get(position).getObjectId());
                            category.setCategory_name(categoryList.get(position).getCategory_name());
                            mPresenter.deleteCategory(category);
                        }
                    });
                    deleteDialog.show();
                }
            });
        }

        if (userListAdapter != null) {
            userListAdapter.setOnItemClickListener(new UserListAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(SearchResultActivity.this, ManagerUserInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", userList.get(position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            userListAdapter.setOnItemLongClickListener(new UserListAdapter.OnRecyclerViewItemLongClickListener() {
                @Override
                public void onItemLongClick(View view, final int position) {
                    DeleteDialog deleteDialog = new DeleteDialog(SearchResultActivity.this);
                    deleteDialog.setOnConfirmListener(new DeleteDialog.OnConfirmListener() {
                        @Override
                        public void onConfirmListener() {
                            pg.show();
                            User user = new User();
                            user.setObjectId(userList.get(position).getObjectId());
                            mPresenter.deleteUser(user);
                        }
                    });
                    deleteDialog.show();
                }
            });
        }

        if (bookListAdapter != null) {
            if (identity == null || !identity.equals("user")) {
                bookListAdapter.setOnItemClickListener(new BookListAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(SearchResultActivity.this, ManagerBookInfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("book", booksList.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                bookListAdapter.setOnItemLongClickListener(new BookListAdapter.OnRecyclerViewItemLongClickListener() {
                    @Override
                    public void onItemLongClick(View view, final int position) {
                        DeleteDialog deleteDialog = new DeleteDialog(SearchResultActivity.this);
                        deleteDialog.setOnConfirmListener(new DeleteDialog.OnConfirmListener() {
                            @Override
                            public void onConfirmListener() {
                                showProgress();
                                Books books = new Books();
                                books.setObjectId(booksList.get(position).getObjectId());
                                mPresenter.deleteBook(books);
                            }
                        });
                        deleteDialog.show();
                    }
                });
            } else if (identity.equals("user")) {
                bookListAdapter.setOnItemClickListener(new BookListAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(SearchResultActivity.this, BookInfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("book", booksList.get(position));
                        intent.putExtra("account", account);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        }
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

    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void showMsg(String message) {

    }

    @Override
    public void getBookSuccess(Books book) {
        Intent intent = new Intent(this, BookInfoActivity.class);
        intent.putExtra("isManager", true);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book", book);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void deleteSuccess() {
        Toast.makeText(this, "删除成功！", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void getBorrowSuccess(Books book, Borrow borrow) {
        Intent intent = new Intent(this, ManagerBorrowInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("book", book);
        bundle.putSerializable("borrow", borrow);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
