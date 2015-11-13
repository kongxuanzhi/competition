package com.example.myapp2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapp2.model.talkRecord;
import com.tuling.util.GetTulingResultThread;
import com.tuling.util.ResultWatcher;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private ListView listView;
    private TextView input;
    private static final int MESSAGE_GET_RESULT=2;
    static final String keyTuling = "1deb50ef7d72b9e73907598a50abc60f";
    static final String keyxuan = "8e62bd9fa524a13a7a0de8510f2e3fa9";
    static String userID="545";
    static String userID2="546";


    List<talkRecord> talkRecords = new ArrayList<talkRecord>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = (ListView)this.findViewById(R.id.content);
        input    = (TextView)this.findViewById(R.id.input);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Toast.makeText(getApplication(),charSequence.toString(),Toast.LENGTH_SHORT);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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

        listView.setSelection(talkRecords.size());
    }
    public void sendMsg(View view){
        String inputs = input.getText().toString();
        if(inputs==null || inputs.equals("")){
            Toast.makeText(this,"您未输入任何内容!",Toast.LENGTH_SHORT);
            return;
        }
        talkRecord talkrecord = new talkRecord();
        talkrecord.setID(11);
        talkrecord.setContent(inputs);
        talkrecord.setTalkWith(R.drawable.xuan);
        talkrecord.setStatus("已读");
        talkrecord.setBackColor(Color.WHITE);
        talkrecord.setSendTime(new Date().toString());
        talkrecord.setIsSelf(true);
        refresh(talkrecord);

        new GetTulingResultThread(keyxuan, inputs, userID, new ResultWatcher() {
            @Override
            public void onResults(String result) {
                JSONObject  jsonData = null;
                String resultBack="消息迷失，请检查网络！";;
                try {
                    jsonData = new JSONObject(result);
                    String code = jsonData.getString("code");
                    if(code.equals("100000")){
                        resultBack = jsonData.getString("text").toString();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                talkRecord resultTuling = new talkRecord();
                resultTuling.setID(11);
                resultTuling.setContent(resultBack);
                resultTuling.setTalkWith(R.drawable.ic_launcher);
                resultTuling.setStatus("已读");
                resultTuling.setBackColor(Color.BLUE);
                resultTuling.setSendTime(new Date().toString());
                resultTuling.setIsSelf(false);
                handler.obtainMessage(1,resultTuling).sendToTarget();
            }
        }).start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            talkRecord result=(talkRecord) msg.obj;
            refresh(result);
        }

    };
    public void  refresh(talkRecord talkrecord){
        talkRecords.add(talkrecord);
        listView.setAdapter(new ListViewAdapter(this, talkRecords));
        listView.setSelection(talkRecords.size());
        input.setText(null);
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
        //从数据库中读取初始数据
        talkRecords.clear();
        for (int i=0;i<2;i++){
            talkRecord  talkrecord =  new talkRecord();
            talkrecord.setID(i);
            talkrecord.setTalkWith(R.drawable.ic_launcher);
            talkrecord.setContent("我每天想你" + (i + 1) + "遍");
            talkrecord.setBackColor(Color.GRAY);
            talkrecord.setSendTime(new Date().toString());
            talkrecord.setStatus("已读");
            talkrecord.setIsSelf(false);
            talkRecords.add(talkrecord);
        }
    }
}
