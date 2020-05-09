package com.sumit.spid.notification;

import com.sumit.spid.R;

public class NotificationData
{
String body;
String date;
String eventId;
int[] thumbnail;
public NotificationData(String body,String date,String eventId)
{
    this.body = body;
    this.date = date;
    this.eventId = eventId;

    thumbnail= new int[]{
            R.drawable.selected_you,
            R.drawable.accepted_thumbnail};
}
    public String getBody()
    {
        return body;
    }

    public String getDate()
    {
        return date;
    }

    public String getEventId(){return eventId;}

    public int getThumbnail(int i)
    {
        return thumbnail [i];
    }
}
