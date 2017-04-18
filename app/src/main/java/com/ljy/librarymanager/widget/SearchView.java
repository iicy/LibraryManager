package com.ljy.librarymanager.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ljy.librarymanager.R;

/**
 * Created by luojiayu on 2017/3/17.
 */

public class SearchView extends LinearLayout implements TextWatcher, View.OnClickListener {

    private ImageButton back;
    private EditText editText;
    private Button bt_clear;

    public SearchView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        /**加载布局文件*/
        LayoutInflater.from(context).inflate(R.layout.layout_search_view, this, true);
        /***找出控件*/
        back = (ImageButton) findViewById(R.id.back);
        editText = (EditText) findViewById(R.id.search_view_edit_text);
        bt_clear = (Button) findViewById(R.id.search_view_clear_bt);
        bt_clear.setVisibility(GONE);
        editText.addTextChangedListener(this);
        bt_clear.setOnClickListener(this);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                mOnSearchListener.onCheckBoxClick(editText);
                return false;
            }
        });
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnBackListener.onBackClick(back);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        /**获取输入文字**/
        String input = editText.getText().toString().trim();
        if (input.isEmpty()) {
            bt_clear.setVisibility(GONE);
        } else {
            bt_clear.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        editText.setText("");
    }


    //声明接口回调 EditText 来让 Activity 获取搜索内容
    public interface OnSearchListener {
        void onCheckBoxClick(EditText v);
    }

    private OnSearchListener mOnSearchListener;

    public void setOnSearchListener(OnSearchListener mOnCheckBoxListener) {
        this.mOnSearchListener = mOnCheckBoxListener;
    }

    public interface OnBackListener {
        void onBackClick(ImageButton v);
    }

    private OnBackListener mOnBackListener;

    public void setOnBackListener(OnBackListener mOnBackListener) {
        this.mOnBackListener = mOnBackListener;
    }
}
