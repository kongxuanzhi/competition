package com.example.myapp2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.myapp2.model.talkRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private ListView listView;

    List<talkRecord> talkRecords = new ArrayList<talkRecord>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = (ListView)this.findViewById(R.id.content);

        //填充list得到数据  从Sqlite数据库中得到数据
        getData();
        listView.setAdapter(new ListViewAdapter(this,talkRecords));

        //设置listView 点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    public void getData() {
        talkRecords.clear();
        for (int i=0;i<10;i++){
            talkRecord  talkrecord =  new talkRecord();
            talkrecord.setID(i);
            talkrecord.setContent("我每天想你" + (i + 1) + "遍");
            talkrecord.setBackColor(Color.BLUE);
            talkrecord.setSendTime(new Date().toString());
            talkrecord.setStatus("已读");
            talkRecords.add(talkrecord);
        }
    }
}
