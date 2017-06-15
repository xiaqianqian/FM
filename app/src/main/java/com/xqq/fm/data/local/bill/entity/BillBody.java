package com.xqq.fm.data.local.bill.entity;

/**
 * Created by xqq on 2017/3/28.
 */

public class BillBody {
    private int id;
    private double money;
    private String category;
    private String remark;

    public BillBody() {
    }

    public BillBody(int id, double money, String category, String remark) {
        this.id = id;
        this.money = money;
        this.category = category;
        this.remark = remark;
    }

    public double getMoney() {
        return money;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
