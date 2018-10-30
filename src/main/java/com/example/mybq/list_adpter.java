package com.example.mybq;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class list_adpter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<databean> bean;

    public list_adpter(ArrayList<databean> bean, LayoutInflater inflater) {
        this.bean = bean;           //从父中得到数据库中的数据
        this.inflater = inflater;   //得到父的xml布局
    }

    public void setBean(ArrayList<databean> bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return bean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return bean.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.one_list, parent, false);
            vh.tv1 = (TextView) convertView.findViewById(R.id.title);
            vh.tv2 = (TextView) convertView.findViewById(R.id.time);
            vh.tv3 = (TextView) convertView.findViewById(R.id.neirong);
            convertView.setTag(vh);
        }
        vh = (ViewHolder) convertView.getTag();
        vh.tv1.setText(bean.get(position).getTitle());
        vh.tv2.setText(bean.get(position).getTime());
        vh.tv3.setText(bean.get(position).getNeirong());
        if (bean.get(position).getColor_num().equals("1")) {
            vh.tv3.setTextColor(Color.RED);
        }
        if (bean.get(position).getColor_num().equals("2")) {
            vh.tv3.setTextColor(Color.BLUE);
        }
        if (bean.get(position).getColor_num().equals("3")) {
            vh.tv3.setTextColor(Color.GREEN);
        }
        return convertView;
    }

    public class ViewHolder {
        TextView tv1, tv2, tv3;
    }
}

