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
 * Created by ¿×ÐùÖ¾ on 2015/11/12.
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
        View view = inflater.inflate(R.layout.item, null);

        ImageView headPic = (ImageView) view.findViewById(R.id.headPic);
        headPic.setImageResource(talkrecord.getTalkWith());

        TextView contentSon = (TextView)view.findViewById(R.id.talkContent);
        contentSon.setText(talkrecord.getContent());
        contentSon.setBackgroundColor(talkrecord.getBackColor());
        return view;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view != null)
            return ItemsV[i];
        return view;
    }
}
