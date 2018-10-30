package com.example.mybq;

import java.io.Serializable;

public class databean implements Serializable {
    private int id;
    private String title;
    private String neirong;
    private String time;
    private String color_num;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNeirong() {
        return neirong;
    }

    public String getTime() {
        return time;
    }

    public String getColor_num() {
        return color_num;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNeirong(String neirong) {
        this.neirong = neirong;
    }

    public void setColor_num(String color_num) {
        this.color_num = color_num;
    }
}
