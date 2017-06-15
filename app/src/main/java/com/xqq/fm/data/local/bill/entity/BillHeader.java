package com.xqq.fm.data.local.bill.entity;

/**
 * Created by xqq on 2017/3/28.
 * <p>
 * 耳机ListView列表的以及列表
 */

public class BillHeader {
    private String date;
    private double sumPayMoney;// date这天所消费的总金额
    private double sumInMoney;

    public BillHeader() {
    }

    public BillHeader(String date, double sumPayMoney, double sumInMoney) {
        this.date = date;
        this.sumPayMoney = sumPayMoney;
        this.sumInMoney = sumInMoney;
    }

    public double getSumPayMoney() {
        return sumPayMoney;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSumPayMoney(double sumPayMoney) {
        this.sumPayMoney = sumPayMoney;
    }

    public double getSumInMoney() {
        return sumInMoney;
    }

    public void setSumInMoney(double sumInMoney) {
        this.sumInMoney = sumInMoney;
    }
}
