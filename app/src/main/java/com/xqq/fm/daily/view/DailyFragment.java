package com.xqq.fm.daily.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xqq.fm.R;
import com.xqq.fm.account.view.AccountActivity;
import com.xqq.fm.budget.BudgetSettingActivity;
import com.xqq.fm.budget.BudgetSettingDataOperating;
import com.xqq.fm.daily.adapter.ExpandListAdapter;
import com.xqq.fm.daily.presenter.DailyPresenter;
import com.xqq.fm.daily.presenter.IDailyPresenter;
import com.xqq.fm.data.local.bill.BillsResponse;
import com.xqq.fm.data.local.bill.entity.Bill;

/**
 * 日常生活，主要展示账单记录
 */
public class DailyFragment extends Fragment implements IDailyView,
        View.OnClickListener, AdapterView.OnItemLongClickListener, ExpandableListView.OnChildClickListener {

    private Context context;

    private TextView tvPay;
    private TextView tvBudget;
    private TextView tvIncome;
    private TextView tvBudgetSetting;
    private LinearLayout llBudget;
    private FloatingActionButton fabAddBill;

    private ExpandableListView billDetail;
    private ExpandListAdapter adapter;

    private IDailyPresenter presenter;

    public static final int REQUEST_ADD_BILL = 0;
    public static final int REQUEST_ADD_BILL_SUCCESS = 1;
    public static final int REQUEST_ADD_BILL_FILED = 2;
    public static final int REQUEST_UPDATE_BILL = 3;
    public static final int REQUEST_UPDATE_BILL_SUCCESS = 4;
    public static final int REQUEST_BUDGET = 5;

    public DailyFragment() {
    }

    public static DailyFragment newInstance() {
        return new DailyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily, container, false);
        presenter = new DailyPresenter(new BillsResponse(), (IDailyView) this);
        initView(view);

        return view;
    }

    /**
     * 控件的点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_budget:
                Intent intent = new Intent(getActivity(), BudgetSettingActivity.class);
                startActivityForResult(intent, REQUEST_BUDGET);
                break;
            case R.id.fab_add:
                startActivityForResult(new Intent(context, AccountActivity.class), REQUEST_ADD_BILL);
                break;
        }
    }

    public void initView(View view) {
        tvBudget = (TextView) view.findViewById(R.id.tv_budget);
        tvIncome = (TextView) view.findViewById(R.id.tv_income);
        tvPay = (TextView) view.findViewById(R.id.tv_pay);
        tvBudgetSetting = (TextView) view.findViewById(R.id.tv_budget_setting);
        llBudget = (LinearLayout) view.findViewById(R.id.ll_budget);
        fabAddBill = (FloatingActionButton) view.findViewById(R.id.fab_add);
        billDetail = (ExpandableListView) view.findViewById(R.id.elv_activity_daily);

        llBudget.setOnClickListener(this);
        fabAddBill.setOnClickListener(this);

        initPayAndIncome();
        initBudget();

        adapter = new ExpandListAdapter(context, presenter.getBillHeader(), presenter.getBillBody());
        billDetail.setAdapter(adapter);

        billDetail.setOnItemLongClickListener(this);
        billDetail.setOnChildClickListener(this);
    }

    private void initBudget() {
        boolean isOpen = BudgetSettingDataOperating.getState(getActivity());
        float pay = Float.valueOf(tvPay.getText().toString());
        if (isOpen) {
            float budget = BudgetSettingDataOperating.saveBudgetMoney(getActivity());
            String text = budget == 0 ? "未设置" : String.valueOf((budget - pay));
            tvBudget.setText(text);
            tvBudgetSetting.setText("预算余额");
        } else {
            float income = Float.valueOf(tvIncome.getText().toString());
            tvBudget.setText((income - pay) + "");
            tvBudgetSetting.setText("收支差额");
        }
    }

    private void initPayAndIncome() {
        tvPay.setText(presenter.getCurrentMonthPay());
        tvIncome.setText(presenter.getCurrentMonthIncome());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == REQUEST_ADD_BILL_SUCCESS || requestCode == REQUEST_UPDATE_BILL_SUCCESS) {
            presenter.refreshData(1);
        }
        if (requestCode == REQUEST_BUDGET) {
            initBudget();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 长按监听事件，主要做  长按删除该账单
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        if (ExpandableListView.getPackedPositionType(id)
                == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            long packedPos = ((ExpandableListView) parent).getExpandableListPosition(position);
            int groupPos = ExpandableListView.getPackedPositionGroup(packedPos);
            int childPos = ExpandableListView.getPackedPositionChild(packedPos);

            // 弹出删除对话框
            showDeleteDialog(groupPos, childPos);
            return true;
        }

        return false;
    }

    private void showDeleteDialog(final int groupPos, final int childPos) {
        new AlertDialog.Builder(getActivity())
                .setTitle("是否删除这条账目")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteBill(groupPos, childPos);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

    /**
     * 更新界面的账目信息
     */
    @Override
    public void refreshBills(int groupPosition) {
        adapter.refresh(presenter.getBillHeader(), presenter.getBillBody());
        if(groupPosition >= 0){
            billDetail.expandGroup(groupPosition);
            billDetail.collapseGroup(groupPosition);
        }
        initPayAndIncome();
        initBudget();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent,
                                View v, int groupPosition, int childPosition, long id) {
        Intent intent = new Intent(getActivity(), AccountActivity.class);

        Bundle bundle = new Bundle();
        Bill bill = presenter.getBill(groupPosition, childPosition);
        bundle.putSerializable("bill", bill);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_UPDATE_BILL);
        return true;
    }
}