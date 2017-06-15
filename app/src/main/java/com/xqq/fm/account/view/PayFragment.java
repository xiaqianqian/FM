package com.xqq.fm.account.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xqq.fm.R;
import com.xqq.fm.data.local.bill.entity.Bill;
import com.xqq.fm.util.DateUtil;

/**
 *
 */
public class PayFragment extends Fragment implements View.OnClickListener,
        AccountActivity.PayOrIncomeFragment {

    private LinearLayout llBillMoney;
    private LinearLayout llBillCategory;
    private LinearLayout llBillAccount;
    private LinearLayout llBillDate;
    private EditText etRemark;
    private TextView tvBillMoney;
    private TextView tvBillCategory;
    private TextView tvBillAccount;
    private TextView tvBillDate;
    private Bill defaultBill;
    private boolean isUpdate;

    public static final String PAY_OR_INCOME = "支出";

    private IAccountItemListener accountItemListener;

    public PayFragment() {
    }

    public static PayFragment newInstance() {
        return new PayFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountItemListener = (IAccountItemListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income_or_pay, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        llBillMoney = (LinearLayout) view.findViewById(R.id.ll_account_money);
        llBillCategory = (LinearLayout) view.findViewById(R.id.ll_account_category);
        llBillAccount = (LinearLayout) view.findViewById(R.id.ll_account_account);
        llBillDate = (LinearLayout) view.findViewById(R.id.ll_account_date);
        etRemark = (EditText) view.findViewById(R.id.et_account_remark);
        tvBillMoney = (TextView) view.findViewById(R.id.tv_account_money);
        tvBillCategory = (TextView) view.findViewById(R.id.tv_account_category);
        tvBillAccount = (TextView) view.findViewById(R.id.tv_account_account);
        tvBillDate = (TextView) view.findViewById(R.id.tv_account_date);

        llBillMoney.setOnClickListener(this);
        llBillCategory.setOnClickListener(this);
        llBillAccount.setOnClickListener(this);
        llBillDate.setOnClickListener(this);

        // 设置默认值
        if (defaultBill == null) {
            tvBillMoney.setText("0.00");
            tvBillCategory.setText("请选择");
            tvBillAccount.setText("现金");
            tvBillDate.setText(DateUtil.getCurrentTime());
            accountItemListener.setCalculator(View.VISIBLE);
            isUpdate = false;
        } else {
            tvBillMoney.setText(defaultBill.getMoney() + "");
            tvBillCategory.setText(defaultBill.getCategory());
            tvBillAccount.setText(defaultBill.getCardNumber());
            tvBillDate.setText(defaultBill.getDate());
            accountItemListener.setCalculator(View.GONE);
            isUpdate = true;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_account_money: // 输入金额
                accountItemListener.setCalculator(View.VISIBLE);
                break;
            case R.id.ll_account_category:  // 输入消费种类
                accountItemListener.setCalculator(View.GONE);
                accountItemListener.showCategory(PAY_OR_INCOME);
                break;
            case R.id.ll_account_account:   // 输入消费账户
                accountItemListener.setCalculator(View.GONE);
                accountItemListener.showCardDialog();
                break;
            case R.id.ll_account_date:  // 输入消费日期
                accountItemListener.setCalculator(View.GONE);
                accountItemListener.showDateSelect();
                break;
        }
    }

    public void setBillMoney(String money) {
        tvBillMoney.setText(money);
    }

    public void setBillCategory(String category) {
        tvBillCategory.setText(category);
    }

    public void setBillAccount(String card) {
        tvBillAccount.setText(card);
    }

    public void setBillDate(String date) {
        tvBillDate.setText(date);
    }

    @Override
    public void saveBill() {
        if (isUpdate) {
            accountItemListener.updateBill(getBill());
        } else {
            accountItemListener.saveBill(getBill());
        }
    }

    private Bill getBill() {
        if (defaultBill == null) {
            defaultBill = new Bill();
        }
        double money = Double.valueOf(tvBillMoney.getText().toString());
        String category = tvBillCategory.getText().toString();
        String cardNumber = tvBillAccount.getText().toString();
        String date = tvBillDate.getText().toString();
        String remark = etRemark.getText() == null ? "无" : etRemark.getText().toString();

        defaultBill.setMoney(money);
        defaultBill.setCategory(category);
        defaultBill.setCardNumber(cardNumber);
        defaultBill.setDate(date);
        defaultBill.setRemark(remark);
        defaultBill.setPayOrIncome(PAY_OR_INCOME);
        return defaultBill;
    }

    @Override
    public void setBill(Bill bill) {
        this.defaultBill = bill;
    }
}