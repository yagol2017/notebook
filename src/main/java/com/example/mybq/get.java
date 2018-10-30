package com.example.mybq;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.EthiopicCalendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class get extends AppCompatActivity {
    private ListView listView;
    private LayoutInflater inflater;
    private ArrayList<databean> bean, schBean;
    private database database = new database(this);
    private list_adpter adpter = new list_adpter(null, null);
    private String color_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.get);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getReady();
        getBQ();
        deleteBQ();
        changeBQ();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.get:
                    return true;
                case R.id.add:
                    Intent intent_add = new Intent(getApplicationContext(), add.class);
                    startActivity(intent_add);
                    get.this.finish();
                    return true;
                case R.id.option:
                    Intent intent_delete = new Intent(getApplicationContext(), option.class);
                    startActivity(intent_delete);
                    get.this.finish();
                    return true;
            }
            return false;
        }
    };

    private void getReady() {
        listView = (ListView) findViewById(R.id.listview);   //初始化ListView
        inflater = getLayoutInflater();     //得到当前xml布局
        bean = database.getBean();      //得到数据库中的数据
        adpter = new list_adpter(bean, inflater);       //将数据和当前布局给适配器，adpter负责转换ListView的子布局

    }


    private void getBQ() {
        listView.setAdapter(adpter);
    }


    private void changeBQ() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getApplicationContext(), change.class);
                intent.putExtra("id", bean.get(position).getId());
                startActivity(intent);
                get.this.finish();
            }
        });
    }

    private void deleteBQ() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(get.this)
                        .setTitle("删除")
                        .setMessage("是否删除笔记")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                database.deleteBQ(bean.get(position).getId());
                                bean = database.getBean();
                                //TODO:无法做到实时刷新数据
                                Intent intent_get = new Intent(getApplicationContext(), get.class);
                                startActivity(intent_get);
                                get.this.overridePendingTransition(0, 0);
                                get.this.finish();
                                adpter.setBean(bean);
                                adpter.setInflater(inflater);
                                adpter.notifyDataSetChanged();
                                listView.setAdapter(adpter);
                            }
                        })
                        .create().show();
                return true;
            }
        });
    }

}
