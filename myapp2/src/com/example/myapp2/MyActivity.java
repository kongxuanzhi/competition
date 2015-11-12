package com.example.myapp2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
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
        listView.setAdapter(new ListViewAdapter(this, talkRecords));

        //设置listView 点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                talkRecord getObject = talkRecords.get(i);   //通过position获取所点击的对象
                int Id = getObject.getID(); //获取信息id
                String Content = getObject.getContent();
                String Status = getObject.getStatus();

                //Toast显示测试
                Toast.makeText(MyActivity.this, "信息:" + Content, Toast.LENGTH_SHORT).show();
            }
        });

        //长按菜单显示
        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            public void onCreateContextMenu(ContextMenu conMenu, View view, ContextMenu.ContextMenuInfo info) {

                conMenu.setHeaderTitle("菜单");
                conMenu.add(0, 0, 0, "条目一");
                conMenu.add(0, 1, 1, "条目二");
                conMenu.add(0, 2, 2, "条目三");
            }
        });
    }

    //长按菜单处理函数
    public boolean onContextItemSelected(MenuItem aItem) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)aItem.getMenuInfo();
        switch (aItem.getItemId()) {
            case 0:
                Toast.makeText(MyActivity.this, "你点击了条目一",Toast.LENGTH_SHORT).show();
                return true;
            case 1:
                Toast.makeText(MyActivity.this, "你点击了条目二",Toast.LENGTH_SHORT).show();

                return true;
            case 2:
                Toast.makeText(MyActivity.this, "你点击了条目三",Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    public void getData() {
        talkRecords.clear();
        for (int i=0;i<10;i++){
            talkRecord  talkrecord =  new talkRecord();
            talkrecord.setID(i);
            talkrecord.setTalkWith(R.drawable.ic_launcher);
            talkrecord.setContent("我每天想你" + (i + 1) + "遍");
            talkrecord.setBackColor(Color.GRAY);
            talkrecord.setSendTime(new Date().toString());
            talkrecord.setStatus("已读");
            talkRecords.add(talkrecord);
        }
    }
}
