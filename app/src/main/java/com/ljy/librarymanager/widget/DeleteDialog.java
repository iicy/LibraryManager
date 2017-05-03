package com.ljy.librarymanager.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ljy.librarymanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiayu on 2016/11/22.
 */

public class DeleteDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.dialog_delete_hint)
    TextView dialog_delete_hint;
    @BindView(R.id.dialog_delete_confirm)
    TextView dialog_delete_confirm;
    @BindView(R.id.dialog_delete_cancel)
    TextView dialog_delete_cancel;

    private String hint;

    private OnConfirmListener mOnConfirmListener = null;

    public DeleteDialog(Context context) {
        super(context);
    }

    public DeleteDialog(Context context,String hint) {
        super(context);
        this.hint = hint;
    }

    protected DeleteDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public DeleteDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_delete);
        ButterKnife.bind(this);
        initview();
    }

    public void initview() {
        if(hint!=null&&!hint.equals("")){
            dialog_delete_hint.setText(hint);
        }
        dialog_delete_confirm.setOnClickListener(this);
        dialog_delete_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_delete_confirm:
                if(mOnConfirmListener!=null)
                    mOnConfirmListener.onConfirmListener();
                dismiss();
                break;
            case R.id.dialog_delete_cancel:
                dismiss();
                break;
        }
    }

    public  interface OnConfirmListener {
         void onConfirmListener();
    }

    public void setOnConfirmListener(OnConfirmListener listener){
        this.mOnConfirmListener = listener;
    }
}

