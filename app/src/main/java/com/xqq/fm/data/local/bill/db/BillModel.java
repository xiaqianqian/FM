package com.xqq.fm.data.local.bill.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.mikephil.charting.data.PieEntry;
import com.xqq.fm.MyApplication;
import com.xqq.fm.data.local.bill.IBillModel;
import com.xqq.fm.data.local.bill.entity.Bill;
import com.xqq.fm.data.local.bill.entity.BillBody;
import com.xqq.fm.data.local.bill.entity.BillHeader;
import com.xqq.fm.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xqq on 2017/3/28.
 * <p>
 * 账单的数据处理
 */

public class BillModel implements IBillModel {
    private BillDBHelper helper;

    public BillModel() {
        helper = new BillDBHelper(MyApplication.getContext());
    }

    public void save(Bill bill) {
        String sql = "insert into bill(money, category, cardNumber, date, remark, payOrIncome) " +
                "values(?, ?, ?, ?, ?, ?)";
        SQLiteDatabase database = helper.getWritableDatabase();
        database.execSQL(sql, new Object[]{bill.getMoney(), bill.getCategory(),
                bill.getCardNumber(),
                bill.getDate(), bill.getRemark(), bill.getPayOrIncome()});
    }

    public void updateBill(Bill bill) {
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("money", bill.getMoney());
        values.put("category", bill.getCategory());
        values.put("payOrIncome", bill.getPayOrIncome());
        values.put("cardNumber", bill.getCardNumber());
        values.put("date", bill.getDate());
        values.put("remark", bill.getRemark());
        database.update("bill", values, "id=?", new String[]{String.valueOf(bill.getId())});
    }

    public List<Bill> getBills() {

        List<Bill> bills = new ArrayList<>();
        SQLiteDatabase database = helper.getReadableDatabase();
        String sql = "select * from bill";

        Cursor cursor = database.rawQuery(sql, new String[]{});

        while (cursor.moveToNext()) {
            Bill bill = new Bill();
            bill.setPayOrIncome(cursor.getString(cursor.getColumnIndex("payOrIncome")));
            bill.setMoney(cursor.getDouble(cursor.getColumnIndex("money")));
            bill.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            bill.setCardNumber(cursor.getString(cursor.getColumnIndex("cardNumber")));
            bill.setDate(cursor.getString(cursor.getColumnIndex("date")));
            bill.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
            bills.add(bill);
        }
        return bills;
    }

    public List<BillHeader> getBillHeader() {
        List<BillHeader> headers = new ArrayList<>();
        List<String> dates = getAllDate();
        SQLiteDatabase database = helper.getReadableDatabase();

        for (String date : dates) {
            String sql = "select payOrIncome, sum(money) as sumMoney from bill where date = ? group by payOrIncome";
            BillHeader header = new BillHeader();
            Cursor cursor = null;
            try {
                cursor = database.rawQuery(sql, new String[]{date});

                header.setDate(date);
                if (cursor.moveToNext()) {
                    String payOrIncome = cursor.getString(cursor.getColumnIndex("payOrIncome"));
                    if (payOrIncome != null) {
                        boolean isPay = payOrIncome.equals("支出");
                        if (isPay) {
                            header.setSumPayMoney(cursor.getDouble(cursor.getColumnIndex("sumMoney")));
                        } else {
                            header.setSumInMoney(cursor.getDouble(cursor.getColumnIndex("sumMoney")));
                        }
                    }
                }

                if (cursor.moveToNext()) {
                    header.setSumInMoney(cursor.getDouble(cursor.getColumnIndex("sumMoney")));
                }

                headers.add(header);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return headers;
    }


    public List<BillHeader> getBillHeaderByCategory(String category) {
        List<BillHeader> headers = new ArrayList<>();
        List<String> dates = getDatesByCategory(category);
        SQLiteDatabase database = helper.getReadableDatabase();

        for (String date : dates) {
            String sql = "select payOrIncome, sum(money) as sumMoney from bill where date = ? group by payOrIncome";
            BillHeader header = new BillHeader();
            Cursor cursor = null;
            try {
                cursor = database.rawQuery(sql, new String[]{date});

                header.setDate(date);
                if (cursor.moveToNext()) {
                    boolean isPay = cursor.getString(cursor.getColumnIndex("payOrIncome")).equals("支出");
                    if (isPay) {
                        header.setSumPayMoney(cursor.getDouble(cursor.getColumnIndex("sumMoney")));
                    } else {
                        header.setSumInMoney(cursor.getDouble(cursor.getColumnIndex("sumMoney")));
                    }
                }

                if (cursor.moveToNext()) {
                    header.setSumInMoney(cursor.getDouble(cursor.getColumnIndex("sumMoney")));
                }

                headers.add(header);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return headers;
    }


    public Bill getBillById(int id) {
        Bill bill = new Bill();

        SQLiteDatabase database = helper.getReadableDatabase();

        String sql = "select * from bill where id = ?";
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, new String[]{String.valueOf(id)});

            if (cursor.moveToNext()) {
                bill.setId(id);
                bill.setCardNumber(cursor.getString(cursor.getColumnIndex("cardNumber")));
                bill.setCategory(cursor.getString(cursor.getColumnIndex("category")));
                bill.setDate(cursor.getString(cursor.getColumnIndex("date")));
                bill.setMoney(cursor.getDouble(cursor.getColumnIndex("money")));
                bill.setPayOrIncome(cursor.getString(cursor.getColumnIndex("payOrIncome")));
                bill.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return bill;
    }

    public double getPayOrInMoneyByDate(String payOrIncome, String date) {
        SQLiteDatabase database = helper.getReadableDatabase();

        String sql = "select sum(money) as money from bill where date like ? and payOrIncome = ?";
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, new String[]{date + "%", payOrIncome});
            if (cursor.moveToNext()) {
                return cursor.getDouble(cursor.getColumnIndex("money"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return 0.0;
    }

    public double getPayOrInMoneyByCardNumber(String payOrIncome, String cardNumber) {
        SQLiteDatabase database = helper.getReadableDatabase();

        String sql = "select sum(money) as money from bill where cardNumber like ? and payOrIncome = ?";
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, new String[]{"%" + cardNumber, payOrIncome});
            if (cursor.moveToNext()) {
                return cursor.getDouble(cursor.getColumnIndex("money"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return 0.0;
    }

    public List<String> getCategoriesByDateAndPayOrIncome(String payOrIncome, String date) {
        SQLiteDatabase database = helper.getReadableDatabase();
        List<String> categories = new ArrayList<>();
        String sql = "select distinct category from bill where date like ? and payOrIncome = ?";
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, new String[]{date + "%", payOrIncome});

            while (cursor.moveToNext()) {
                categories.add(cursor.getString(cursor.getColumnIndex("category")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return categories;
    }

    public double getSumMoneyByCategoryAndDateAndPayOrIncome(String category, String payOrIncome, String date) {
        SQLiteDatabase database = helper.getReadableDatabase();
        String sql = "select sum(money) as money from bill where date like ? and payOrIncome = ? and category = ?";

        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, new String[]{date + "%", payOrIncome, category});
            if (cursor.moveToNext()) {
                return cursor.getDouble(cursor.getColumnIndex("money"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return 0.0;
    }

    public List<List<BillBody>> getBillBody(List<BillHeader> headers) {
        List<List<BillBody>> bodies = new ArrayList<>();
        SQLiteDatabase database = helper.getReadableDatabase();

        for (BillHeader header : headers) {
            String sql = "select id, money, remark, category from bill where date = ?";
            List<BillBody> bodyList = new ArrayList<>();

            Cursor cursor = null;
            try {
                cursor = database.rawQuery(sql, new String[]{header.getDate()});

                while (cursor.moveToNext()) {
                    BillBody body = new BillBody();
                    body.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    body.setMoney(cursor.getDouble(cursor.getColumnIndex("money")));
                    body.setCategory(cursor.getString(cursor.getColumnIndex("category")));
                    body.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
                    bodyList.add(body);
                }
                bodies.add(bodyList);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return bodies;
    }

    @Override
    public List<List<BillBody>> getBillBodyByCategory(List<BillHeader> headers, String category) {
        List<List<BillBody>> bodies = new ArrayList<>();
        SQLiteDatabase database = helper.getReadableDatabase();

        for (BillHeader header : headers) {
            String sql = "select id, money, remark, category from bill where date = ? and category = ?";
            List<BillBody> bodyList = new ArrayList<>();

            Cursor cursor = null;
            try {
                cursor = database.rawQuery(sql, new String[]{header.getDate(), category});

                while (cursor.moveToNext()) {
                    BillBody body = new BillBody();
                    body.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    body.setMoney(cursor.getDouble(cursor.getColumnIndex("money")));
                    body.setCategory(cursor.getString(cursor.getColumnIndex("category")));
                    body.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
                    bodyList.add(body);
                }
                bodies.add(bodyList);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return bodies;
    }

    public void delete(int id) {
        SQLiteDatabase database = helper.getWritableDatabase();
        String whereClause = "id=?";
        database.delete("bill", whereClause, new String[]{String.valueOf(id)});
    }

    public double getCurrentMonthPayOrInMoney(String payOrIncome) {
        return getSumMoneyByPayOrIncomeAndDate(payOrIncome, DateUtil.getCurrentMonth());
    }

    public double getSumMoneyByPayOrIncomeAndDate(String payOrIncome, String date) {
        SQLiteDatabase database = helper.getReadableDatabase();

        String sql = "select sum(money) as money from bill where date like ? and payOrIncome = ?";
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, new String[]{date + "%", payOrIncome});
            if (cursor.moveToNext()) {
                return cursor.getDouble(cursor.getColumnIndex("money"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return 0.0;
    }

    private List<String> getDatesByCategory(String category) {
        List<String> dates = new ArrayList<>();
        String sqlPay = "select date from bill where category = ? group by date";
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sqlPay, new String[]{category});

            while (cursor.moveToNext()) {
                dates.add(cursor.getString(cursor.getColumnIndex("date")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return dates;
    }

    private List<String> getAllDate() {
        List<String> dates = new ArrayList<>();
        String sqlPay = "select date from bill group by date";
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sqlPay, new String[]{});

            while (cursor.moveToNext()) {
                dates.add(cursor.getString(cursor.getColumnIndex("date")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return dates;
    }

    public List<PieEntry> getPieEntries(String payOrIncome, String date) {
        List<PieEntry> entries = new ArrayList<>();
        SQLiteDatabase database = helper.getReadableDatabase();
        String sql = "select category, sum(money) as money from bill where payOrIncome = ? and date like ? group by category";
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, new String[]{payOrIncome, date + "%"});
            while (cursor.moveToNext()) {
                entries.add(new PieEntry((float) cursor.getDouble(cursor.getColumnIndex("money"))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return entries;
    }

    public int count(){
        int count = 0;
        SQLiteDatabase database = helper.getReadableDatabase();
        String sql = "select count(id) as rows from bill";
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, new String[]{});
            cursor.moveToNext();
            count = cursor.getInt(cursor.getColumnIndex("rows"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return count;
    }
}
