package com.example.myapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myapp2.model.talkRecord;

import java.util.List;

/**
 * Created by ����־ on 2015/11/12.
 */
public class ListViewAdapter extends BaseAdapter {

    View[] ItemsV;
    Context context;

    public ListViewAdapter(Context context, List<talkRecord> talkRecords){
        this.context = context;
        ItemsV = new View[talkRecords.size()];
        for (int i=0;i<talkRecords.size();i++){
            talkRecord  talkrecord = talkRecords.get(i);
            ItemsV[i] = makeItemView(talkrecord);
        }
    }
    private  View makeItemView(talkRecord talkrecord){
        LayoutInflater  inflater = (LayoutInflater)context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView headPic;
        TextView sendTime;
        TextView contentSon;
        TextView status;
        View view = null;
        if(talkrecord.getIsSelf()==true) {
            view= inflater.inflate(R.layout.item, null);
            headPic = (ImageView) view.findViewById(R.id.send_headPic);
            sendTime = (TextView) view.findViewById(R.id.send_Time);
            contentSon = (TextView)view.findViewById(R.id.send_talkContent);
            status = (TextView) view.findViewById(R.id.send_status);
        }
        else{
            view = inflater.inflate(R.layout.receiveitem, null);
            headPic = (ImageView) view.findViewById(R.id.rec_headPic);
            sendTime = (TextView) view.findViewById(R.id.rec_Time);
            contentSon = (TextView)view.findViewById(R.id.rec_talkContent);
            status = (TextView) view.findViewById(R.id.rec_status);
        }
        headPic.setImageResource(talkrecord.getTalkWith());
        sendTime.setText(talkrecord.getSendTime());
        contentSon.setText(talkrecord.getContent());
        contentSon.setBackgroundColor(talkrecord.getBackColor());
        status.setText(talkrecord.getStatus());
        return view;
    }

    @Override
    public int getCount() {
        return ItemsV.length;
    }

    @Override
    public Object getItem(int i) {
        return ItemsV[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       // if (view == null)
            return ItemsV[i];
       // return view;
    }
}
