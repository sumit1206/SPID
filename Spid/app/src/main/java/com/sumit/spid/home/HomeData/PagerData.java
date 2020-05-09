package com.sumit.spid.home.HomeData;

public class PagerData {
    private int Image ;
    private String Title;

    public PagerData(int image, String title) {
        Image = image;
        Title = title;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
