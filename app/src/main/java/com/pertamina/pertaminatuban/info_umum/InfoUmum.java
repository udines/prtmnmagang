package com.pertamina.pertaminatuban.info_umum;

import java.sql.Timestamp;

/**
 * Created by laptop on 1/29/2018.
 */

public class InfoUmum {
    private String id;
    private String title;
    private String info;
    private String detail;
    private Timestamp timestamp;

    public InfoUmum(String id, String title, String info, String detail, Timestamp timestamp) {
        this.id = id;
        this.title = title;
        this.info = info;
        this.detail = detail;
        this.timestamp = timestamp;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
