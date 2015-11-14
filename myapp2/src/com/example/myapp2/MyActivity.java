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
import android.widget.*;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.example.myapp2.model.talkRecord;
import com.tuling.util.GetTulingResultThread;
import com.tuling.util.ResultWatcher;
import com.tuling.util.TulingManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private ListView listView;
    private TextView input;
    private Button voice_btn;
    private Button send_msg;
    private RelativeLayout edit;
    private TulingManager manager;
    private static final int CHANGE_BTN = 1;
    private static final int VOICE_RECOGNIZE = 2;
    private static final int MESSAGE_GET_RESULT=3;
    static final String keyTuling = "1deb50ef7d72b9e73907598a50abc60f";
    static final String keyxuan = "8e62bd9fa524a13a7a0de8510f2e3fa9";
    static String userID="545";
    static String userID2="546";

    List<talkRecord> talkRecords = new ArrayList<talkRecord>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getData();
        setViewComposeinit();
        //填充list得到数据  从Sqlite数据库中得到数据
    }
    private void setViewComposeinit() {
        listView = (ListView)this.findViewById(R.id.content);
        input    = (TextView)this.findViewById(R.id.input);
        voice_btn = (Button) this.findViewById(R.id.sendVoiceMsg);
        send_msg = (Button) this.findViewById(R.id.sendMsg);
        edit = (RelativeLayout) this.findViewById(R.id.edit);
        manager = new TulingManager(this);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println("beforeTextChanged");
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println("onTextChanged");
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String input_msg = editable.toString();
                if(input_msg.equals("")||input_msg.equals(null)){
                    handler.obtainMessage(CHANGE_BTN, R.drawable.voice).sendToTarget();
                }
                else{
                    handler.obtainMessage(CHANGE_BTN, R.drawable.xuan).sendToTarget();
                }
            }
        });
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
        talkRecord talkrecord = new talkRecord(11,R.drawable.xuan,inputs,
                new Date().toString(), "已读" ,Color.WHITE, true);
        refresh(talkrecord);
        getTulingAnswer(inputs);
    }
    public void sendVoice(View view){
        manager.showRecognizeDialog(new DialogRecognitionListener() {
            @Override  //通过语音给图灵机器说话
            public void onResults(Bundle recog_Result) {
                if (recog_Result != null) {
                    ArrayList<String> list = recog_Result.getStringArrayList("results_recognition");
                    String say_Input = list.get(0);
                    int index = say_Input.lastIndexOf("发送");
                    if(index>-1 && index >=say_Input.length()-3){
                        talkRecord talkrecord = new talkRecord(11,R.drawable.xuan,say_Input.substring(0, index),
                                new Date().toString(), "已读" ,Color.WHITE, true);
                        refresh(talkrecord);
                        getTulingAnswer(say_Input.substring(0,index));
                    }
                    else{
                         input.setText(say_Input);
                    }
                }
            }
        });
    }
    public void getTulingAnswer(String inputs){
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
                talkRecord resultTuling = new talkRecord(11,R.drawable.ic_launcher,resultBack,
                        new Date().toString(), "已读" ,Color.BLUE, false);
                handler.obtainMessage(MESSAGE_GET_RESULT,resultTuling).sendToTarget();
            }
        }).start();
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case MESSAGE_GET_RESULT:
                    talkRecord result=(talkRecord) msg.obj;
                    refresh(result);
                    break;
                case CHANGE_BTN:
                    int btn = Integer.parseInt(msg.obj.toString());
                    if(btn==R.drawable.voice){
                        edit.removeView(send_msg);
                        edit.removeView(voice_btn);
                        edit.addView(voice_btn);
                    }else{
                        edit.removeView(voice_btn);
                        edit.removeView(send_msg);
                        edit.addView(send_msg);
                    }
                    break;
            }
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
            talkRecord talkInit = new talkRecord(i,R.drawable.ic_launcher,"我每天想你" + (i + 1) + "遍",
                    new Date().toString(), "已读" ,Color.GRAY, false);
            talkRecords.add(talkInit);
        }
    }
}
