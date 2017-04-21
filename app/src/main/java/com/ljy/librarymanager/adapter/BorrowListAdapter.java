package com.ljy.librarymanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljy.librarymanager.R;
import com.ljy.librarymanager.mvp.entity.Booking;
import com.ljy.librarymanager.mvp.entity.Borrow;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiayu on 2016/11/2.
 */

public class BorrowListAdapter extends RecyclerView.Adapter<BorrowListAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<Borrow> mList;
    private boolean SHOW_USER_TAG;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private OnRecyclerViewItemLongClickListener mOnItemLongClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnRecyclerViewItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public BorrowListAdapter(Context context, List<Borrow> mList, boolean tag) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mList = mList;
        this.SHOW_USER_TAG = tag;
    }

    public void setNewData(List<Borrow> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = mLayoutInflater.inflate(R.layout.item_borrow, parent, false);
        ViewHolder vh = new ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String tag = "";
        if(mList.get(position).getStatus().equals("1"))
            tag = "(已归还)";
        holder.bookName.setText("书名："+mList.get(position).getBookName()+"   "+tag);
        if(SHOW_USER_TAG){
            holder.userName.setText("用户："+mList.get(position).getUser());
            holder.userName.setVisibility(View.VISIBLE);
        }
        if(mList.get(position).getUpdatedAt()!=null&&!mList.get(position).getUpdatedAt().equals(""))
        {
            holder.date.setText(mList.get(position).getUpdatedAt());
        }else{
            holder.date.setText(mList.get(position).getCreatedAt());
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

        @BindView(R.id.item_borrow_icon)
        ImageView icon;
        @BindView(R.id.item_borrow_name)
        TextView bookName;
        @BindView(R.id.item_borrow_username)
        TextView userName;
        @BindView(R.id.item_borrow_date)
        TextView date;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
