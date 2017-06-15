package com.xqq.fm.datamanagement.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.xqq.fm.R;
import com.xqq.fm.datamanagement.FileManageActivity;
import com.xqq.fm.datamanagement.model.ManagementResponse;
import com.xqq.fm.datamanagement.presenter.IManagementPresenter;
import com.xqq.fm.datamanagement.presenter.ManagementPresenter;

/**
 * 数据导入导出的主界面
 */
public class DataManagementFragment extends Fragment implements IManagementView, View.OnClickListener {

    private Button mBtnPull; // 导出
    private Button mBtnPut;// 导入
    private IManagementPresenter presenter;
    public static final int REQUEST_PULL = 0;
    public static final int REQUEST_PUT = 1;

    public DataManagementFragment() {
    }

    public static DataManagementFragment newInstance() {
        return new DataManagementFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_data_management, container, false);
        initView(layout);
        return layout;
    }

    private void initView(View layout) {
        mBtnPull = (Button) layout.findViewById(R.id.btn_management_pull);
        mBtnPut = (Button) layout.findViewById(R.id.btn_management_put);

        mBtnPull.setOnClickListener(this);
        mBtnPut.setOnClickListener(this);

        presenter = new ManagementPresenter(new ManagementResponse(), (IManagementView) this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_management_pull:  // 导出
                pullBillData();
                break;
            case R.id.btn_management_put:   // 导入
                putBillData();
                break;
        }
    }

    // 导入
    private void putBillData() {
        // 1. 选择XML文件的位置
        Intent intent = new Intent(getActivity(), FileManageActivity.class);
        startActivityForResult(intent, REQUEST_PUT);
    }

    // 导出
    private void pullBillData() {
        // 1. 选择XML文件要保存的位置
        Intent intent = new Intent(getActivity(), FileManageActivity.class);
        startActivityForResult(intent, REQUEST_PULL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_PULL && data != null) {

            String path = data.getStringExtra("path");
            presenter.pullBill(path);
        }

        if (requestCode == REQUEST_PUT && data != null) {
            String path = data.getStringExtra("path");
            presenter.putBill(path);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showResult(String result) {
        Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
    }
}
