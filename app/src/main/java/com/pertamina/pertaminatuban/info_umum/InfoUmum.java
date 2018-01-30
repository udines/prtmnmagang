package com.pertamina.pertaminatuban.info_umum;

import java.sql.Date;
import java.util.HashMap;

public class InfoUmum {
    private String id;
    private String title;
    private String info;
    private String detail;
//    private Date date;
//    private HashMap<String, Object> timestamp;
    private long time;

    public InfoUmum() {
    }

    public InfoUmum(String id, String title, String info, String detail, long time) {
        this.id = id;
        this.title = title;
        this.info = info;
        this.detail = detail;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    /*public Date getDate() {
        return date;
    }

    public void setDate(HashMap<String, Object> timestamp) {
        this.timestamp = timestamp;
        this.date = (Date)timestamp.get("date");
    }*/
}
