package com.example.shop.aggregator.notificationCarrier;


import com.example.shop.R;

public class NotificationData
{
    String body;
    String date;
    String eventId;
    String type;
    String seenStatus;


    public NotificationData(String body, String date, String eventId, String type, String seenStatus) {
        this.body = body;
        this.date = date;
        this.eventId = eventId;
        this.type = type;
        this.seenStatus = seenStatus;

        thumbnail= new int[]{
                R.drawable.account_balance_wallet,
                R.drawable.account_circle};
    }


int[] thumbnail;
    public String getBody()
    {
        return body;
    }

    public String getDate()
    {
        return date;
    }

    public String getEventId() {
        return eventId;
    }

    public String getType() {
        return type;
    }

    public String getSeenStatus() {
        return seenStatus;
    }


    public int getThumbnail(int i)
    {
        return thumbnail [0];
    }

}
