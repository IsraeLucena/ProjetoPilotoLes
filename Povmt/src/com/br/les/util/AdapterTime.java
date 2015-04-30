package com.br.les.util;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.br.les.povmt.R;
import com.br.les.timeitup.ActivityTI;

public class AdapterTime extends BaseAdapter{
    
    private List<ActivityTI> list;
    private Context ctx;

    public AdapterTime(List<ActivityTI> list, Context ctx) {
        super();
        this.list = list;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(ctx, R.layout.adapter_time, null);
        ActivityTI atv = (ActivityTI) getItem(position);
        
        TextView name = (TextView) v.findViewById(R.id.atvName);
        name.setText(atv.getName());
        TextView duration = (TextView) v.findViewById(R.id.atvDuration);
        duration.setText(atv.getDuration());
        TextView priority = (TextView) v.findViewById(R.id.atvPriority);
        priority.setText(atv.getPriority());
        
        
        return v;
    }

}
