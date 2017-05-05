package com.ljy.librarymanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.entity.Announcement;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.Comment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiayu on 2016/11/2.
 */

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<Comment> mList;
    private Books book;
    private String bookingId;
    private String collectionId;
    private boolean isManager;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private OnRecyclerViewItemLongClickListener mOnItemLongClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnRecyclerViewItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public CommentListAdapter(Context context, List<Comment> mList, Books book) {
        this.book = book;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mList = mList;
    }

    public void setNewData(List<Comment> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = mLayoutInflater.inflate(R.layout.item_book_info, parent, false);
        } else {
            view = mLayoutInflater.inflate(R.layout.item_comment, parent, false);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (position == 0) {
            if (book.getPic() != null) {
                Glide.with(mContext)
                        .load(book.getPic().getUrl())
                        .fitCenter()
                        .placeholder(R.drawable.ic_image)
                        .thumbnail(0.1f)
                        .into(holder.pic);
            } else {
                Glide.clear(holder.pic);
                holder.pic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_image));
            }
            holder.tv_bookName.setText("书名：" + book.getBookName());
            holder.tv_bookAuthor.setText("作者：" + book.getAuthor());
            holder.tv_bookCategory.setText("分类：" + book.getCategory());
            holder.tv_bookPublication.setText("出版社：" + book.getPublication());
            holder.tv_bookPublicationDate.setText("出版日期：" + book.getPublicationDate().getDate());
            holder.tv_bookStock.setText("库存：" + book.getStock());
            holder.tv_bookSummary.setText("简介：" + book.getSummary());
            if (isManager) {
                holder.bt_booking.setVisibility(View.GONE);
                holder.bt_collect.setVisibility(View.GONE);
                holder.bt_undo_booking.setVisibility(View.GONE);
                holder.bt_undo_collect.setVisibility(View.GONE);
                holder.bt_send_comment.setVisibility(View.GONE);
            }
        } else {

        }
        holder.itemView.setTag(position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemLongClickListener != null) {
            mOnItemLongClickListener.onItemLongClick(v, (int) v.getTag());
        }
        return false;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

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

        @BindView(R.id.account)
        TextView comment_account;
        @BindView(R.id.content)
        TextView comment_content;
        @BindView(R.id.date)
        TextView comment_date;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
