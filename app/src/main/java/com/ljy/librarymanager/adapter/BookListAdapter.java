package com.ljy.librarymanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.entity.Books;
import com.ljy.librarymanager.mvp.entity.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiayu on 2016/11/2.
 */

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<Books> mList;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private OnRecyclerViewItemLongClickListener mOnItemLongClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnRecyclerViewItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public BookListAdapter(Context context, List<Books> mList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mList = mList;
    }

    public void setNewData(List<Books> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = mLayoutInflater.inflate(R.layout.item_book, parent, false);
        ViewHolder vh = new ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(mList.get(position).getBookName());
        holder.author.setText(mList.get(position).getAuthor());
        holder.pulication.setText(mList.get(position).getPublication());
        holder.date.setText(mList.get(position).getPublicationDate().getDate());
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

        @BindView(R.id.item_book_icon)
        ImageView icon;
        @BindView(R.id.item_book_name)
        TextView name;
        @BindView(R.id.item_book_author)
        TextView author;
        @BindView(R.id.item_book_pulication)
        TextView pulication;
        @BindView(R.id.item_book_pulication_date)
        TextView date;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
