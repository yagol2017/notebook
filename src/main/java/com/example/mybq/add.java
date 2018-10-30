package com.example.mybq;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class add extends AppCompatActivity implements View.OnClickListener {
    private EditText etID, etTITLE, etNEIRONG;
    private TextView show_id;
    private Button tianjia;
    private RadioGroup color;
    private database database = new database(this);
    private String title, neirong;
    private String color_num;
    private tools tools = new tools();
    private databean databean = new databean();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss);
    Date date = new Date(System.currentTimeMillis());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.add);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getReady();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.get:
                    Intent intent_get = new Intent(getApplicationContext(), get.class);
                    startActivity(intent_get);
                    add.this.finish();
                    return true;
                case R.id.add:
                    return true;
                case R.id.option:
                    Intent intent_delete = new Intent(getApplicationContext(), option.class);
                    startActivity(intent_delete);
                    add.this.finish();
                    return true;
            }
            return false;
        }
    };

    public void getReady() {
        etID = (EditText) findViewById(R.id.out_id);
        etTITLE = (EditText) findViewById(R.id.input_title);
        etNEIRONG = (EditText) findViewById(R.id.input_neirong);
        tianjia = (Button) findViewById(R.id.tianjia);
        show_id = (TextView) findViewById(R.id.show_id);
        color = (RadioGroup) findViewById(R.id.color);
        etID.setText(tools.intToString(database.getLastID()));
        tianjia.setOnClickListener(this);
        etID.setVisibility(View.GONE);
        show_id.setVisibility(View.GONE);
        color.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.color_red) {
                    color_num = "1";
                }
                if (checkedId == R.id.color_blue) {
                    color_num = "2";
                }
                if (checkedId == R.id.color_green) {
                    color_num = "3";
                }
            }
        });
    }

    public void addBQ() {
        databean.setTitle(title);
        databean.setNeirong(neirong);
        databean.setTime(simpleDateFormat.format(date));
        databean.setId(database.getLastID() + 1);
        databean.setColor_num(color_num);
        database.addBQ(databean);
    }

    public void afterADD() {
        etID.setText(tools.intToString(database.getLastID()));
        etTITLE.setText("");
        etNEIRONG.setText("");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tianjia:
                title = etTITLE.getText().toString();
                neirong = etNEIRONG.getText().toString();
                addBQ();
                afterADD();
        }
    }


}