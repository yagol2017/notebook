package com.example.mybq;

import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class tools {
    public String intToString(int a) {
        String b = "" + a;
        return b;
    }


    public ArrayList<databean> getSchBean(ArrayList<databean> allBean, String toSch) {
        ArrayList<databean> schBean = new ArrayList<>();
        for (int i = 0; i < allBean.size(); i++) {
            if (allBean.get(i).getNeirong().indexOf(toSch) != -1) {
                Log.d(TAG, "getSchBean: include" + toSch);
                schBean.add(allBean.get(i));
            }
        }
        return schBean;
    }
}
