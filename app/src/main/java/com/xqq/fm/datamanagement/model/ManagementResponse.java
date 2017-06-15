package com.xqq.fm.datamanagement.model;

import com.xqq.fm.data.local.bill.IBillModel;
import com.xqq.fm.data.local.bill.db.BillModel;
import com.xqq.fm.data.local.bill.entity.Bill;

import java.util.List;

/**
 * Created by xqq on 2017/4/16.
 * <p>
 * 主要负责数据导出成XML文件格式以及从XML文件导入到程序中
 */

public class ManagementResponse {

    private IBillModel billModel;
    private List<Bill> bills;

    public ManagementResponse() {
        billModel = new BillModel();
    }

    public boolean pullBill(String path) {
        bills = billModel.getBills();

        return XmlOperating.writeBillsToXml(path, bills);
    }

    public int putBill(String path) {

        List<Bill> bills = XmlOperating.readBillsFromXml(path);

        for (Bill bill : bills) {
            billModel.save(bill);
        }
        return bills.size();
    }

    public int getCount(){
        return billModel.count();
    }
}
