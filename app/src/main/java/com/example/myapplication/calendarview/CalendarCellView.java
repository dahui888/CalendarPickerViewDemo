package com.example.myapplication.calendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.Date;

public class CalendarCellView extends FrameLayout {

    private static final int[] STATE_SELECTABLE = {
            R.attr.tsquare_state_selectable
    };
    private static final int[] STATE_CURRENT_MONTH = {
            R.attr.tsquare_state_current_month
    };
    private static final int[] STATE_TODAY = {
            R.attr.tsquare_state_today
    };
    private static final int[] STATE_HIGHLIGHTED = {
            R.attr.tsquare_state_highlighted
    };
    private static final int[] STATE_RANGE_FIRST = {
            R.attr.tsquare_state_range_first
    };
    private static final int[] STATE_RANGE_MIDDLE = {
            R.attr.tsquare_state_range_middle
    };
    private static final int[] STATE_RANGE_LAST = {
            R.attr.tsquare_state_range_last
    };

    private boolean isSelectable = false;//是否可选
    private boolean isCurrentMonth = false;//是否当月
    private int dayFlag;//1今天 2明天 3后天
    private boolean isSeleted = false;//是否在选中状态
    private boolean isHighlighted = false;
    private Date date;
    private RangeState rangeState = RangeState.NONE;
    private TextView dayOfMonthTextView;

    @Override
    public String toString() {
        return "CalendarCellView{" +
                "isSelectable=" + isSelectable +
                ", dayFlag=" + dayFlag +
                ", isCurrentMonth=" + isCurrentMonth +
                ", isHighlighted=" + isHighlighted +
                ", rangeState=" + rangeState +
                '}';
    }

    @SuppressWarnings("UnusedDeclaration") //
    public CalendarCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCellTextColor(int resId) {
        TextView dayOfMonthTextView = getDayOfMonthTextView();
        dayOfMonthTextView.setTextColor(resId);
    }

    public void setSelectable(boolean isSelectable) {
        if (this.isSelectable != isSelectable) {
            this.isSelectable = isSelectable;
            refreshDrawableState();
        }
    }

    public void setCurrentMonth(boolean isCurrentMonth) {
        if (this.isCurrentMonth != isCurrentMonth) {
            this.isCurrentMonth = isCurrentMonth;
            refreshDrawableState();
        }
    }

    public void setDayFlag(int dayFlag) {
        if (this.dayFlag != dayFlag) {
            this.dayFlag = dayFlag;
            refreshDrawableState();
        }
    }

    public void setSeleted(boolean isSeleted){
        if (this.isSeleted != isSeleted) {
            this.isSeleted = isSeleted;
            refreshDrawableState();
        }
    }

    public void setDate(Date date){
        if (this.date != date) {
            this.date = date;
            refreshDrawableState();
        }
    }

    public void setRangeState(RangeState rangeState) {
        if (this.rangeState != rangeState) {
            this.rangeState = rangeState;
            refreshDrawableState();
        }
    }

    public void setHighlighted(boolean isHighlighted) {
        if (this.isHighlighted != isHighlighted) {
            this.isHighlighted = isHighlighted;
            refreshDrawableState();
        }
    }

    public boolean isCurrentMonth() {
        return isCurrentMonth;
    }

    public boolean isSelectable() {
        return isSelectable;
    }

    public boolean isSelected() {
        return isSeleted;
    }

    public int getDayFlag(){
        return dayFlag;
    }

    public Date getDate(){
        return date;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public RangeState getRangeState() {
        return rangeState;
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 5);
        if (isSelectable) {
            mergeDrawableStates(drawableState, STATE_SELECTABLE);
        }
        if (isCurrentMonth) {
            mergeDrawableStates(drawableState, STATE_CURRENT_MONTH);
        }
        if (dayFlag>0) {
            mergeDrawableStates(drawableState, STATE_TODAY);
        }
        if (isHighlighted) {
            mergeDrawableStates(drawableState, STATE_HIGHLIGHTED);
        }
        if (rangeState == RangeState.FIRST) {
            mergeDrawableStates(drawableState, STATE_RANGE_FIRST);
        } else if (rangeState == RangeState.MIDDLE) {
            mergeDrawableStates(drawableState, STATE_RANGE_MIDDLE);
        } else if (rangeState == RangeState.LAST) {
            mergeDrawableStates(drawableState, STATE_RANGE_LAST);
        }
        return drawableState;
    }

    public void setDayOfMonthTextView(TextView textView) {
        dayOfMonthTextView = textView;
    }

    public TextView getDayOfMonthTextView() {
        if (dayOfMonthTextView == null) {
            throw new IllegalStateException("You have to setDayOfMonthTextView in your custom DayViewAdapter.");
        }
        return dayOfMonthTextView;
    }

}
