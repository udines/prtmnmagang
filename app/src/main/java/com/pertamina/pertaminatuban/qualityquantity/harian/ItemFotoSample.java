package com.pertamina.pertaminatuban.qualityquantity.harian;

public class ItemFotoSample {
    private String id;
    private String url;
    private String info;
    private String date;

    public ItemFotoSample(String url, String info, String date) {
        this.url = url;
        this.info = info;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getInfo() {
        return info;
    }
}
