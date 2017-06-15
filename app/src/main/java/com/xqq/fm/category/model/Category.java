package com.xqq.fm.category.model;

/**
 * Created by xqq on 2017/3/25.
 *
 * 类别的实体类
 */
public class Category {

    private String category;
    private String payOrIncome;  // 支出或收入  -1：支出  1：收入

    public Category(){}

    /**
     * 类别构造函数
     *
     * @param category  种类名称
     * @param payOrIncome 收入或支出，收入：1  支出：-1
     */
    public Category(String category, String payOrIncome) {
        this.category = category;
        this.payOrIncome = payOrIncome;
    }

    public String getPayOrIncome() {
        return payOrIncome;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPayOrIncome(String payOrIncome) {
        this.payOrIncome = payOrIncome;
    }

    @Override
    public String toString() {
        return getCategory() + " " + getPayOrIncome();
    }
}
