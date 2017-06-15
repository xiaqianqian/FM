package com.xqq.fm.category.model;

/**
 * Created by xqq on 2017/3/25.
 */

public interface ICategoryModel {

    void saveCategory(Category category);

    String [] getCategoryByPayOrIncome(String payOrIncome);
}
