package com.example.myapplication.calendarview;

import android.view.View;

import com.example.myapplication.R;

import java.util.Calendar;
import java.util.Date;

public class SampleDecorator implements CalendarCellDecorator {

    @Override
    public void decorate(CalendarCellView cellView, Date date) {
        //设置不可选元素隐藏
        setSelectableItemShowOrHide(cellView);
        //设置当前item文字颜色
        setCellTextStyle(cellView);
        //设置当前文字
        setCellText(cellView);
    }

    private void setSelectableItemShowOrHide(CalendarCellView cellView) {
        if (!cellView.isSelectable()) {  //如果不可选
            if (cellView.isCurrentMonth()) {
                cellView.setVisibility(View.VISIBLE);
            } else {
                cellView.setVisibility(View.INVISIBLE);
            }
        } else {
            cellView.setVisibility(View.VISIBLE);
        }
    }

    private void setCellTextStyle(CalendarCellView cellView) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(cellView.getDate());
        if (cellView.isSelectable()) {//可选
            if (cellView.isSelected()) {//选中
                cellView.setCellTextColor(cellView.getContext().getResources().getColor(R.color.white));
            } else {
                if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {//周末
                    cellView.setCellTextColor(cellView.getContext().getResources().getColor(R.color.text_main_blue));
                } else {
                    cellView.setCellTextColor(cellView.getContext().getResources().getColor(R.color.text_main_black));
                }
            }
        } else {//不可选
            cellView.setCellTextColor(cellView.getContext().getResources().getColor(R.color.text_hint_gray));
        }
    }

    private void setCellText(CalendarCellView cellView) {
        //只有日子16  有说明或者节日14  说明还带节日12
        String dateString = Integer.toString(cellView.getDate().getDate());
        if (cellView.getDayFlag() == 1) {
            cellView.getDayOfMonthTextView().setText(dateString + "\n周一");
            cellView.getDayOfMonthTextView().setTextSize(14);
        } else if (cellView.getDayFlag() == 2) {
            cellView.getDayOfMonthTextView().setText(dateString + "\n周二");
            cellView.getDayOfMonthTextView().setTextSize(14);
        } else if (cellView.getDayFlag() == 3) {
            cellView.getDayOfMonthTextView().setText(dateString + "\n周三");
            cellView.getDayOfMonthTextView().setTextSize(14);
        } else {
            cellView.getDayOfMonthTextView().setText(dateString);
            cellView.getDayOfMonthTextView().setTextSize(16);
        }


        //每个日期显示周几 用的话把上面代码注释掉用这个
//        long time = cellView.getDate().getTime();
//        String s_year = DateUtil.getYear(String.valueOf(time));
//        String s_month = DateUtil.getMonth(String.valueOf(time));
//        String s_day = DateUtil.getDay(String.valueOf(time));
//        int int_year = Integer.parseInt(s_year);
//        int int_month = Integer.parseInt(s_month);
//        int int_day = Integer.parseInt(s_day);
//        String week = DateUtil.getDayofweek(int_year, int_month, int_day);
//        String dateString = Integer.toString(cellView.getDate().getDate());
//        cellView.getDayOfMonthTextView().setText(dateString + "\n" + week + "");
//        cellView.getDayOfMonthTextView().setTextSize(14);
    }

}
