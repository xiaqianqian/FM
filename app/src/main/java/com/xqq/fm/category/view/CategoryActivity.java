package com.xqq.fm.category.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xqq.fm.R;
import com.xqq.fm.account.view.AccountActivity;
import com.xqq.fm.category.model.Category;
import com.xqq.fm.category.model.db.CategoryModel;
import com.xqq.fm.category.model.ICategoryModel;

public class CategoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private ListView lvCategory;

    private ICategoryModel categoryModel;
    private String[] categories;
    private CategoryAdapter adapter;

    private String payOrIncome;   // 查询类别是属于 收入，还是支出

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        // 初始化ActionBar
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(R.string.title_pay_category);

        lvCategory = (ListView) findViewById(R.id.lv_category);

        Intent intent = getIntent();
        if (intent != null) {
            payOrIncome = intent.getExtras().getString("payOrIncome", "支出");
        }

        // 获取数据
        categoryModel = new CategoryModel(this);
        categories = categoryModel.getCategoryByPayOrIncome(payOrIncome);

        adapter = new CategoryAdapter(categories);
        lvCategory.setAdapter(adapter);
        lvCategory.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String category = categories[position];

        // 将选择的种类返回
        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra("category", category);
        setResult(AccountActivity.CATEGORY_RESULT, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.add_category:// 添加分类
                showAddCategoryDialog();
                break;
        }
        return true;
    }

    private void showAddCategoryDialog() {
        AddCategoryDialog dialog = new AddCategoryDialog(this,
                new AddCategoryDialog.IAddCategoryDialogClickListener() {

                    @Override
                    public void onSaveClick(String content) {// 保存到数据库，并刷新类别列表

                        saveCategory(content);
                    }
                });

        dialog.show();
    }

    private void saveCategory(String content) {
        Category category = new Category(content, payOrIncome);
        categoryModel.saveCategory(category);

        categories = categoryModel.getCategoryByPayOrIncome(payOrIncome);
        adapter.replaceData(categories);
    }

    private static class CategoryAdapter extends BaseAdapter {

        private String[] categories;

        public CategoryAdapter(String[] categories) {
            setCategories(categories);
        }

        private void setCategories(String[] categories) {
            this.categories = categories;
        }

        public void replaceData(String[] categories) {
            setCategories(categories);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return categories.length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                convertView = inflater.inflate(R.layout.activity_category_item, null);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.tv_item_category);
            textView.setText(categories[position]);
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return categories[position];
        }
    }
}
