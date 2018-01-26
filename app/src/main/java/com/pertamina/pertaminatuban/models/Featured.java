package com.pertamina.pertaminatuban.models;

public class Featured {
    private String id;
    private String imageUrl;
    private String title;
    private long timestamp;
    private String content;

    public Featured() {
    }

    public Featured(String id, String imageUrl, String title, long timestamp, String content) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.timestamp = timestamp;
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
