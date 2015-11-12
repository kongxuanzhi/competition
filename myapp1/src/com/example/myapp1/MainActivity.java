package com.example.myapp1;


import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.tuling.util.GetTulingResultThread;
import com.tuling.util.ResultWatcher;
import com.tuling.util.TulingManager;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    Button btn_recognize;
    TulingManager manager;
    TextView tv_content;
    TextView tv_recoginize;
    private static final int MESSAGE_RECOGNIZE=1;
    private static final int MESSAGE_GET_RESULT=2;
    static final String keyTuling = "1deb50ef7d72b9e73907598a50abc60f";
    static final String keyxuan = "8e62bd9fa524a13a7a0de8510f2e3fa9";
    static String userID="545";
    static String userID2="546";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        manager=new TulingManager(this);
        tv_content = (TextView) findViewById(R.id.answers);
        tv_recoginize=(TextView)findViewById(R.id.result);
        btn_recognize=(Button)findViewById(R.id.btn);
//        new GetTulingUserIDThread(keyxuan, new ResultWatcher() {
//
//            @Override
//            public void onResults(String result) {
//                if(result!=null&&result.length()>0){
//                    userID=result;
//                    System.out.println("userID ="+ userID);
//                }
//            }
//        });


        btn_recognize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.showRecognizeDialog(new DialogRecognitionListener() {
                    @Override  //通过语音给图灵机器说话
                    public void onResults(Bundle result) {
                        if(result!=null) {
                            ArrayList<String> list = result.getStringArrayList("results_recognition");
                           // tv_recoginize.setText();
                          //  tv_recoginize.setText(result);
                            //  String say = tv_recoginize.getText().toString();
                            String say = list.get(0);
                            handler.obtainMessage(MESSAGE_RECOGNIZE, "{'code':100000,'text':'" + say + "'}").sendToTarget();  //将消息发送给处理器
                        }
                    }
                });
            }
        });
    }
    Handler handler =new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MESSAGE_RECOGNIZE:
                    String result=(String) msg.obj;
                    JSONObject dataJson1= null;
                    try {
                        dataJson1 = new JSONObject(result);
                        String responseCode = dataJson1.getString("code");
                        if(responseCode.equals("100000")){
                            String answer = dataJson1.getString("text");
                            System.out.println(answer);
                            tv_recoginize.setText(answer);
                            new GetTulingResultThread(keyxuan, answer, userID, new ResultWatcher() {
                                @Override
                                public void onResults(String result) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    handler.obtainMessage(MESSAGE_GET_RESULT,result).sendToTarget();
                                }
                            }).start();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case MESSAGE_GET_RESULT:
                    String result_tuling=(String) msg.obj;
                    JSONObject dataJson= null;
                    try {
                        dataJson = new JSONObject(result_tuling);
                        String responseCode = dataJson.getString("code");
                        if(responseCode.equals("100000")){
                            String answer = dataJson.getString("text");
                            System.out.println(answer);
                            tv_content.setText(answer);
                            new GetTulingResultThread(keyTuling, answer, userID2, new ResultWatcher() {
                                @Override
                                public void onResults(String s) {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    handler.obtainMessage(MESSAGE_RECOGNIZE, s).sendToTarget();
                                }
                            }).start();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        };
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}


//
//TulingManage.showRecognizeDialog(new DialogRecognitionListener() {
//@Override
//public void onResults(Bundle result) {
//        ArrayList  resultEnd = result != null? result.getStringArrayList("results_recognition"):null;
//        if(resultEnd != null && resultEnd.size()>0){
//        //  Toast.makeText(getApplication(), (Integer) resultEnd.get(0), Toast.LENGTH_SHORT);
//        System.out.println(resultEnd.get(0));
//
//        recognizeResult.setText(resultEnd.get(0).toString());
//        String input =  resultEnd.get(0).toString();
//
//        new GetTulingResultThread( key,input,userId, new ResultWatcher(){
////@Override
///public void onResults(String s) {
//        System.out.println(s);
//        JSONObject dataJson= null;
//        try {
//        dataJson = new JSONObject(s);
//        String responseCode = dataJson.getString("code");
//        if(responseCode.equals("100000")){
//        String answer = dataJson.getString("text");
//        System.out.println(answer);
//        answers.setText(answer);
//        }
//        } catch (JSONException e) {
//        e.printStackTrace();
//        }
//        }
//        }).start();
//        }
//        }
//        });