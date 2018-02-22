package com.pertamina.pertaminatuban.qualityquantity.harian;

public class ItemTestReport {
    private String id;
    private String date;
    private String url;

    public ItemTestReport(String id, String date, String url) {
        this.id = id;
        this.date = date;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
