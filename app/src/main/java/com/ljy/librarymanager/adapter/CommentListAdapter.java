package com.ljy.librarymanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ljy.librarymanager.R;
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

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private OnRecyclerViewItemLongClickListener mOnItemLongClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnRecyclerViewItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public CommentListAdapter(Context context, List<Comment> mList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mList = mList;
    }

    public void setNewData(List<Comment> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = mLayoutInflater.inflate(R.layout.item_comment, parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.comment_username.setText(mList.get(position).getUsername() + ":");
        holder.comment_content.setText(mList.get(position).getContent());
        holder.comment_date.setText(mList.get(position).getCreatedAt());
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

        @BindView(R.id.username)
        TextView comment_username;
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
