package com.example.mybq;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class change extends AppCompatActivity implements View.OnClickListener {
    private EditText etID, etTITLE, etNEIRONG;
    private Button change, back;
    private TextView show_id;
    private RadioGroup color;
    private tools tools = new tools();
    private int id;
    private String color_num;
    private database database = new database(this);
    private ArrayList<databean> bean = new ArrayList<databean>();
    private databean tempbean = new databean();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss);
    private Date date = new Date(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change);
        getReady();
    }

    public void getReady() {
        bean = database.getBean();
        etID = (EditText) findViewById(R.id.out_id);
        etTITLE = (EditText) findViewById(R.id.input_title);
        etNEIRONG = (EditText) findViewById(R.id.input_neirong);
        change = (Button) findViewById(R.id.change);
        back = (Button) findViewById(R.id.back);
        show_id = (TextView) findViewById(R.id.show_id);
        color = (RadioGroup) findViewById(R.id.color);
        Intent intent = this.getIntent();
        id = intent.getIntExtra("id", -1);
        setTempBean();
        etID.setText(tools.intToString(tempbean.getId() - 1));
        etTITLE.setText(tempbean.getTitle());
        etNEIRONG.setText(tempbean.getNeirong());
        if (tempbean.getColor_num() == "1") {
            color.check(R.id.color_red);
        }
        if (tempbean.getColor_num() == "2") {
            color.check(R.id.color_blue);
        }
        if (tempbean.getColor_num() == "3") {
            color.check(R.id.color_green);
        }
        change.setOnClickListener(this);
        back.setOnClickListener(this);
        show_id.setVisibility(View.GONE);
        etID.setVisibility(View.GONE);
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

    public void setTempBean() {
        tempbean = database.fromIDtoBean(id);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change:
                tempbean.setTitle(etTITLE.getText().toString());
                tempbean.setNeirong(etNEIRONG.getText().toString());
                tempbean.setTime(simpleDateFormat.format(date));
                tempbean.setId(id);
                tempbean.setColor_num(color_num);
                database.changeBQ(tempbean);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("修改便签")
                        .setMessage("成功修改")
                        .setNegativeButton("继续修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("返回", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent_get = new Intent(getApplicationContext(), get.class);
                                startActivity(intent_get);
                                change.this.overridePendingTransition(0, 0);
                                change.this.finish();
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
                break;
            case R.id.back:
                Intent intent_get = new Intent(getApplicationContext(), get.class);
                startActivity(intent_get);
                change.this.finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "标题：" + etTITLE.getText() + "\n内容：" + etNEIRONG.getText().toString());
                startActivity(intent.createChooser(intent, "Share to"));
                break;
            case R.id.exit:
                change.this.finish();
            default:
                break;
        }

        return true;
    }
}
