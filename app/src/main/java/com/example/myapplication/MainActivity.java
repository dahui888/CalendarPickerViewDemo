package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt_single = (Button) findViewById(R.id.bt_single);
        bt_single.setOnClickListener(this);
        Button bt_range = (Button) findViewById(R.id.bt_range);
        bt_range.setOnClickListener(this);
        Button bt_multi = (Button) findViewById(R.id.bt_multi);
        bt_multi.setOnClickListener(this);
        Button bt_multi_limit = (Button) findViewById(R.id.bt_multi_limit);
        bt_multi_limit.setOnClickListener(this);
        tv_choice = (TextView) findViewById(R.id.tv_choice);
    }

    ArrayList<CalendarDate> cur_lists = new ArrayList<>();//当前选中日期

    @Override
    public void onClick(View view) {
        //单选日期
        if (view.getId() == R.id.bt_single) {
            Intent single = new Intent(this, SelectCalendarActivity.class);
            single.putExtra(SelectCalendarActivity.CUTDAY, 60);
            single.putExtra(SelectCalendarActivity.CALENDARDATE, cur_lists);
            single.putExtra(SelectCalendarActivity.MODE, SelectCalendarActivity.MODE_SINGLE);
            startActivityForResult(single, 100);
        }
        //范围日期
        if (view.getId() == R.id.bt_range) {
            Intent range = new Intent(this, SelectCalendarActivity.class);
            range.putExtra(SelectCalendarActivity.CUTDAY, 60);
            range.putExtra(SelectCalendarActivity.CALENDARDATE, cur_lists);
            range.putExtra(SelectCalendarActivity.MODE, SelectCalendarActivity.MODE_RANGLE);
            startActivityForResult(range, 100);
        }
        //多选日期
        if (view.getId() == R.id.bt_multi) {
            Intent multi = new Intent(this, SelectCalendarActivity.class);
            multi.putExtra(SelectCalendarActivity.CUTDAY, 60);
            multi.putExtra(SelectCalendarActivity.CALENDARDATE, cur_lists);
            multi.putExtra(SelectCalendarActivity.MODE, SelectCalendarActivity.MODE_MULTI);
            startActivityForResult(multi, 100);
        }
        //限制多选
        if (view.getId() == R.id.bt_multi_limit) {
            Intent multi_limit = new Intent(this, SelectCalendarActivity.class);
            multi_limit.putExtra(SelectCalendarActivity.CUTDAY, 60);
            multi_limit.putExtra(SelectCalendarActivity.CALENDARDATE, cur_lists);
            multi_limit.putExtra(SelectCalendarActivity.MODE, SelectCalendarActivity.MODE_MULTI);
            multi_limit.putExtra(SelectCalendarActivity.MULTISIZE, 2);
            startActivityForResult(multi_limit, 100);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            ArrayList<CalendarDate> lists = (ArrayList<CalendarDate>) data.getExtras().get(SelectCalendarActivity.CALENDARDATE);
            cur_lists.clear();
            cur_lists.addAll(lists);
            StringBuffer sb = new StringBuffer();
            sb.append("当前选中：\n");
            for (int i = 0; i < cur_lists.size(); i++) {
                sb.append(cur_lists.get(i).yyyy_MM_dd + "\n");
            }
            tv_choice.setText(sb.toString());
        }
    }

}
