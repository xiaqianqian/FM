package com.xqq.fm.data.local.bill.entity;

import java.io.Serializable;

/**
 * Created by xqq on 2017/3/18.
 * <p>
 * 账单的实体类
 */
public class Bill implements Serializable {

    private int id;
    private double money;      // 钱
    private String payOrIncome;  // 账单类型：支出：-1，收入:1
    private String category;  // 类别：支出，收入不一样
    private String cardNumber;
    private String date;  // 消费日期
    private String remark;     // 备注

    public Bill() {
    }

    public Bill(double money, String category, String cardNumber, String date, String remark, String payOrIncome) {
        this.money = money;
        this.payOrIncome = payOrIncome;
        this.cardNumber = cardNumber;
        this.category = category;
        this.date = date;
        this.remark = remark;
    }

    public Bill(int id, double money, String category, String cardNumber, String date, String remark, String payOrIncome) {
        setId(id);
        this.money = money;
        this.payOrIncome = payOrIncome;
        this.cardNumber = cardNumber;
        this.category = category;
        this.date = date;
        this.remark = remark;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getMoney() {
        return money;
    }

    public String getPayOrIncome() {
        return payOrIncome;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getRemark() {
        return remark;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setPayOrIncome(String payOrIncome) {
        this.payOrIncome = payOrIncome;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return getMoney() + " " + getPayOrIncome() + " " + getCategory() + " " + getDate() + " "
                + getRemark();
    }
}