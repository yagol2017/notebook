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

import java.util.ArrayList;

public class option extends AppCompatActivity implements View.OnClickListener {
    private Button deleteAll, quit, sch;
    private EditText schText;
    private database database = new database(this);
    private tools tools = new tools();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.option);
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
                    option.this.finish();
                    return true;
                case R.id.add:
                    Intent intent_add = new Intent(getApplicationContext(), add.class);
                    startActivity(intent_add);
                    option.this.finish();
                    return true;
                case R.id.option:
                    return true;
            }
            return false;
        }
    };

    public void getReady() {
        deleteAll = (Button) findViewById(R.id.deleteAll);
        quit = (Button) findViewById(R.id.quit);
        sch = (Button) findViewById(R.id.sch);
        schText = (EditText) findViewById(R.id.schText);
        deleteAll.setOnClickListener(this);
        quit.setOnClickListener(this);
        sch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteAll:
                database.deleteAll();
                break;
            case R.id.sch:
                String schString = schText.getText().toString();
                ArrayList<databean> schBean = tools.getSchBean(database.getBean(), schString);
                Intent intent = new Intent(getApplicationContext(), sch.class);
                intent.putExtra("schBean", schBean);
                startActivity(intent);
                option.this.finish();
                break;
            case R.id.quit:
                this.finish();
                break;
        }
    }

    public void sch_database(View v) {
        String schString = schText.getText().toString();
        ArrayList<databean> schBean = database.sch_database(schString);
        Intent intent = new Intent(getApplicationContext(), sch.class);
        intent.putExtra("schBean", schBean);
        startActivity(intent);
        option.this.finish();
    }
}