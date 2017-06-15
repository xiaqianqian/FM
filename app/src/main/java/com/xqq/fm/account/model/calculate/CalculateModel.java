package com.xqq.fm.account.model.calculate;

/**
 * Created by xqq on 2017/3/23.
 * <p>
 * 计算器的逻辑处理类
 */

public class CalculateModel implements ICalculateModel {

    private StringBuilder currDigit; // 当前正在输入的数
    private boolean isAdd;

    public CalculateModel() {
        currDigit = new StringBuilder();
    }

    @Override
    public void count(String currData, CountCallback countCallback) {

        switch (currData) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case ".":
                currDigit.append(currData);
                break;

            case "+":
            case "-":       // 运算
                operation(currData);
                break;

            case "OK":      // 计算
                count();
                break;

            case "<-":      // 回删
                delete();
                break;

            case "C":       // 清零
                clear();
                break;
        }
        showText(countCallback);
    }

    private void showText(CountCallback countCallback) {
        countCallback.onCount(currDigit.toString());
    }

    // 判断如果有两个数就计算，否则继续加
    private void operation(String currData) {

        int position = getSymbolPosition();

        // 已经按过一次符号，则覆盖，即判断最后一个字符是否为符号 是  则替换
        if (position == currDigit.length() - 1) {
            currDigit.setCharAt(position, currData.charAt(0));
            return;
        }

        // 否则，如果已经有一个符号，并且符号不再最后，计算之后的数再加上符号
        if ((position > 0) && (position < currDigit.length())) {
            count();
        }

        // 如果不符合以上两种情况，直接加在原有字符后面
        currDigit.append(currData);
    }

    /**
     * 获得操作符的位置
     *
     * @return -1：表示不含操作符
     */
    private int getSymbolPosition() {
        int position = -1;
        int tempPosition1 = currDigit.lastIndexOf("+");
        int tempPosition2 = currDigit.lastIndexOf("-");
        if (tempPosition1 > 0) {
            position = tempPosition1;
            isAdd = true;
        }
        if (tempPosition2 > 0) {
            position = tempPosition2;
            isAdd = false;
        }
        return position;
    }

    /**
     * 计算
     */
    private void count() {
        String digit1;
        String digit2;

        int segmentationPosition = getSymbolPosition();

        if (segmentationPosition > 0) {
            digit1 = currDigit.substring(0, segmentationPosition);
            digit2 = currDigit.substring(segmentationPosition + 1, currDigit.length());

            double result = isAdd ?
                    Double.parseDouble(digit1) + Double.parseDouble(digit2)
                    : Double.parseDouble(digit1) - Double.parseDouble(digit2);

            currDigit.replace(0, currDigit.length(), result + "");
        }
    }

    private void clear() {
        currDigit.delete(0, currDigit.length() - 1);
    }

    private void delete() {
        currDigit.deleteCharAt(currDigit.length() - 1);
    }
}
