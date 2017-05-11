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

public class ModifyDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.dialog_edit_text)
    TextView dialog_edit_text;
    @BindView(R.id.dialog_confirm)
    TextView dialog_confirm;
    @BindView(R.id.dialog_cancel)
    TextView dialog_cancel;

    private String username;

    private OnConfirmListener mOnConfirmListener = null;

    public ModifyDialog(Context context) {
        super(context);
    }

    public ModifyDialog(Context context, String username) {
        super(context);
        this.username = username;
    }

    protected ModifyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public ModifyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_modify);
        ButterKnife.bind(this);
        initview();
    }

    public void initview() {
        if (username != null && !username.equals("")) {
            dialog_edit_text.setText(username);
        }
        dialog_confirm.setOnClickListener(this);
        dialog_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_confirm:
                if (mOnConfirmListener != null)
                    mOnConfirmListener.onConfirmListener();
                dismiss();
                break;
            case R.id.dialog_cancel:
                dismiss();
                break;
        }
    }

    public interface OnConfirmListener {
        void onConfirmListener();
    }

    public void setOnConfirmListener(OnConfirmListener listener) {
        this.mOnConfirmListener = listener;
    }

    public String getUsername() {
        return dialog_edit_text.getText().toString();
    }
}

