package com.example.android.newsfeedapp;



public class News {

    private String titleName;
    private String sectionName;
    private String date;
    private String type;
    private String image;
    private String webUrl;

    public News(String titleName, String sectionName, String date, String type, String webUrl, String image) {

        this.titleName = titleName;
        this.sectionName = sectionName;
        this.date = date;
        this.type = type;
        this.image = image;
        this.webUrl = webUrl;
    }

    public String getTitleName() {
        return titleName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }
}
