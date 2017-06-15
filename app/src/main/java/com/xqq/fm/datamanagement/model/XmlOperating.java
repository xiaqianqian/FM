package com.xqq.fm.datamanagement.model;

import android.util.Xml;

import com.xqq.fm.data.local.bill.entity.Bill;
import com.xqq.fm.util.DateUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xqq on 2017/4/16.
 * <p>
 * XML文件的读取与写入
 */

public class XmlOperating {
    public static List<Bill> readBillsFromXml(String path) {
        XmlPullParser xmlParser = Xml.newPullParser();
        List<Bill> bills = null;
        File file = new File(path);
        if (!file.exists()) {
            return bills;
        }
        int eventType = 0;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            xmlParser.setInput(fis, "UTF-8");
            eventType = xmlParser.getEventType();

            Bill bill = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        bills = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        switch (xmlParser.getName()) {
                            case "bill":
                                bill = new Bill();
                                break;
                            case "payOrIncome":
                                xmlParser.next();
                                String payOrIncome = xmlParser.getText();
                                bill.setPayOrIncome(payOrIncome);
                                break;
                            case "money":
                                xmlParser.next();
                                bill.setMoney(Double.valueOf(xmlParser.getText()));
                                break;
                            case "category":
                                xmlParser.next();
                                bill.setCategory(xmlParser.getText());
                                break;
                            case "cardNumber":
                                xmlParser.next();
                                bill.setCardNumber(xmlParser.getText());
                                break;
                            case "date":
                                xmlParser.next();
                                bill.setDate(xmlParser.getText());
                                break;
                            case "remark":
                                xmlParser.next();
                                bill.setRemark(xmlParser.getText());
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("bill".equals(xmlParser.getName())) {
                            bills.add(bill);
                        }
                        break;
                }
                eventType = xmlParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bills;
    }

    public static boolean writeBillsToXml(String path, List<Bill> bills) {

        FileOutputStream fos = null;
        File file = new File(path + "/" + DateUtil.getCurrentTime() + ".xml");

        try {
            fos = new FileOutputStream(file);
            XmlSerializer serializer = Xml.newSerializer();
            serializer.setOutput(fos, "UTF-8");

            // 缩进
            serializer.setFeature(
                    "http://xmlpull.org/v1/doc/features.html#indent-output",
                    true);
            serializer.startDocument("UTF-8", true);
            serializer.startTag(null, "bills");

            for (Bill bill : bills) {
                serializer.startTag(null, "bill");

                serializer.startTag(null, "payOrIncome");
                serializer.text(bill.getPayOrIncome());
                serializer.endTag(null, "payOrIncome");

                serializer.startTag(null, "money");
                serializer.text(String.valueOf(bill.getMoney()));
                serializer.endTag(null, "money");

                serializer.startTag(null, "category");
                serializer.text(bill.getCategory());
                serializer.endTag(null, "category");

                serializer.startTag(null, "cardNumber");
                serializer.text(bill.getCardNumber());
                serializer.endTag(null, "cardNumber");

                serializer.startTag(null, "date");
                serializer.text(bill.getDate());
                serializer.endTag(null, "date");

                serializer.startTag(null, "remark");
                serializer.text(bill.getRemark());
                serializer.endTag(null, "remark");

                serializer.endTag(null, "bill");
            }

            serializer.endTag(null, "bills");
            serializer.endDocument();
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
