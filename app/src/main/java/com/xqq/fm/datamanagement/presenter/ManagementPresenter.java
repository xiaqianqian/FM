package com.xqq.fm.datamanagement.presenter;

import com.xqq.fm.datamanagement.model.ManagementResponse;
import com.xqq.fm.datamanagement.view.IManagementView;

/**
 * Created by xqq on 2017/4/16.
 * <p>
 * 数据导入导出的控制器，逻辑处理器
 */

public class ManagementPresenter implements IManagementPresenter {

    private IManagementView view;
    private ManagementResponse response;

    public ManagementPresenter(ManagementResponse response, IManagementView view) {
        this.response = response;
        this.view = view;
    }

    /**
     * 导出账单信息
     *
     * @param path 导出账单信息保存路径
     */
    @Override
    public void pullBill(String path) {
        if(response.pullBill(path)){
            view.showResult("成功导出" + response.getCount() + "条账目信息");
            return;
        }
        view.showResult("导出失败");
    }

    /**
     * 导入账单信息
     *
     * @param path 导入账单文件路径
     */
    @Override
    public void putBill(String path) {
        int result = response.putBill(path);
        if(result > 0){
            view.showResult("成功导入" + result + "条账目信息");
            return;
        }
        view.showResult("导入失败");
    }
}
