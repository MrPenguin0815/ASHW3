package com.example.homework3;

public class Data {

    private int chapterId;

    private String shareUser;

    private String title;

    private String link;


    public Data(int chapterId, String shareUser, String title, String link) {
        this.chapterId = chapterId;
        this.shareUser = shareUser;
        this.title = title;
        this.link = link;
    }

    public int getChapterId() {
        return chapterId;
    }

    public String getShareUser() {
        return shareUser;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}
