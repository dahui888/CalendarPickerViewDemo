package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.calendarview.CalendarCellDecorator;
import com.example.myapplication.calendarview.CalendarPickerView;
import com.example.myapplication.calendarview.DefaultDayViewAdapter;
import com.example.myapplication.calendarview.SampleDecorator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SelectCalendarActivity extends AppCompatActivity implements View.OnClickListener {

    //key
    public static final String CALENDARDATE = "CalendarDate";
    public static final String CUTDAY = "CutDay";
    public static final String MODE = "Mode";
    public static final String DISPLAYONLY = "DisplayOnly";
    public static final String MULTISIZE = "MultiSize";

    public static final int MODE_SINGLE = 0;//单选
    public static final int MODE_RANGLE = 1;//范围选择
    public static final int MODE_MULTI = 2;//多选

    private CalendarPickerView calendar;

    public ArrayList<CalendarDate> select = new ArrayList<>();//当前选中的日期
    public int cutSelect = 30;//可选日期范围(单位：天)
    public int mode;//当前设置选择模式
    public int multi_size;//当前设置选择模式
    public boolean displayOnly = false;//是否禁止选择

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_calendar);
        initData();
        initSet();
    }

    private void initData() {
        select = (ArrayList<CalendarDate>) getIntent().getExtras().get(CALENDARDATE);
        mode = getIntent().getIntExtra(MODE, MODE_SINGLE);
        cutSelect = getIntent().getIntExtra(CUTDAY, 30);
        displayOnly = getIntent().getBooleanExtra(DISPLAYONLY, false);
        multi_size = getIntent().getIntExtra(MULTISIZE, -1);
        TextView tv_sure = (TextView) findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(this);
        if (displayOnly) tv_sure.setVisibility(View.GONE);
    }

    //初始化日历相关参数设置
    private void initSet() {
        CalendarPickerView.SelectionMode now_mode;
        if (mode == 0) {
            now_mode = CalendarPickerView.SelectionMode.SINGLE;
        } else if (mode == 1) {
            now_mode = CalendarPickerView.SelectionMode.RANGE;
        } else {
            now_mode = CalendarPickerView.SelectionMode.MULTIPLE;
        }
        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        calendar.setCustomDayView(new DefaultDayViewAdapter());//设置自定义的cell布局，其实用这个就不需要setDecorators了，懒得动脑子太特么麻烦不搞了
//        setDecorators(Collections.<CalendarCellDecorator>emptyList());
        calendar.setDecorators(Arrays.<CalendarCellDecorator>asList(new SampleDecorator()));//设置自定义的添加设置
        Calendar now = Calendar.getInstance();//当前日期
        Calendar last = Calendar.getInstance();
        last.add(Calendar.DAY_OF_MONTH, cutSelect);//最大可选日期
        calendar.init(now.getTime(), last.getTime()) //设置可选范围
                .inMode(now_mode) //设置选择模式
                .displayOnly(displayOnly)//设置是否可以选择
                .withSelectedDates(getDateList());//设置当前选择日期
    }

    private List<Date> getDateList() {
        //将已选的日期转成data格式
        List<Date> lists = new ArrayList<>();
        if (select == null || select.size() == 0) {//没有传日期的时候
            if (mode == 1) {//范围选择需要做特殊处理
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_MONTH, 1);
                lists.add(c.getTime());
                c.add(Calendar.DAY_OF_MONTH, 1);
                lists.add(c.getTime());
            } else {//没数据添加一个默认的数据
                lists.add(new Date());
            }
        } else {//传了日期的时候
            if (mode == 1) {//范围选择需要做特殊处理
                if (select.size() >= 2) {//大于两条数据，只添加第一条和最后一条
                    lists.add(DateUtil.parseDate(select.get(0).yyyy_MM_dd, DateUtil.pattern2));
                    lists.add(DateUtil.parseDate(select.get(select.size() - 1).yyyy_MM_dd, DateUtil.pattern2));
                } else {//只有一个日期数据，需要再补一个
                    lists.add(DateUtil.parseDate(select.get(0).yyyy_MM_dd, DateUtil.pattern2));
                    Calendar c = Calendar.getInstance();
                    c.set(select.get(0).getYear(), select.get(0).getMonth() - 1, select.get(0).getDay());
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    lists.add(c.getTime());
                }
            } else {
                for (int i = 0; i < select.size(); i++) {
                    lists.add(DateUtil.parseDate(select.get(i).yyyy_MM_dd, DateUtil.pattern2));
                }
            }
        }
        return lists;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_sure) {
            ArrayList<Date> selectedDates = calendar.getSelectedDates();
            if (mode == MODE_MULTI && multi_size != -1 && selectedDates.size() > multi_size) {
                Toast.makeText(this, "只能选择" + multi_size + "个日期", Toast.LENGTH_SHORT).show();
            } else {
                ArrayList<CalendarDate> cds = new ArrayList<>();
                for (int i = 0; i < selectedDates.size(); i++) {
                    cds.add(CalendarDate.parseDate(DateUtil.format(selectedDates.get(i).getTime(), DateUtil.pattern2)));
                }
                Intent intent = new Intent();
                intent.putExtra(CALENDARDATE, cds);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

}
