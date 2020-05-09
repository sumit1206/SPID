package com.sumit.spid.home.HomeData;

public class RecommentedData
{
    int recommentThumbnail;
    String recommendedTitle;

    public RecommentedData(int recommentThumbnail,String recommendedTitle)
    {
        this.recommentThumbnail = recommentThumbnail;
        this.recommendedTitle = recommendedTitle;
    }

    public int getRecommentThumbnail()
    {
        return recommentThumbnail;
    }

    public void setRecommentThumbnail(int recommentThumbnail)
    {
        this.recommentThumbnail = recommentThumbnail;
    }

    public String getRecommendedTitle() {
        return recommendedTitle;
    }

    public void setRecommendedTitle(String recommendedTitle) {
        this.recommendedTitle = recommendedTitle;
    }
}
