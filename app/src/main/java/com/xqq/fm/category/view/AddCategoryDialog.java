package com.xqq.fm.category.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xqq.fm.R;

/**
 * Created by xqq on 2017/3/25.
 */

public class AddCategoryDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private IAddCategoryDialogClickListener listener;
    private EditText etAddCategory;
    private Button btnExit;
    private Button btnSave;

    public AddCategoryDialog(Context context, IAddCategoryDialogClickListener listener) {
        this(context, R.style.dialog , listener);
    }

    public AddCategoryDialog(Context context, int themeResId, IAddCategoryDialogClickListener listener) {
        super(context, themeResId);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.dialog_category, null);
        initView(layout);
        setContentView(layout);
    }

    private void initView(View layout) {
        etAddCategory = (EditText) layout.findViewById(R.id.et_dialog_category);
        btnExit = (Button) layout.findViewById(R.id.btn_dialog_exit);
        btnSave = (Button) layout.findViewById(R.id.btn_dialog_save);

        btnSave.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dialog_exit:
                dismiss();
                break;
            case R.id.btn_dialog_save:
                saveClick();
                dismiss();
                break;
        }
    }

    private void saveClick() {
        String content = etAddCategory.getText().toString();
        if (!content.equals("")) {
            listener.onSaveClick(content);
        }
    }

    public interface IAddCategoryDialogClickListener {
        void onSaveClick(String content);
    }
}
