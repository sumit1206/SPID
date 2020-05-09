package com.sumit.spid.mydelivery.data;

public class TrackData {
    String packetPosition;
    String timeStamp;

    public TrackData(String packetPosition, String timeStamp) {
        this.packetPosition = packetPosition;
        this.timeStamp = timeStamp;
    }

    public String getPacketPosition() {
        return packetPosition;
    }

    public void setPacketPosition(String packetPosition) {
        this.packetPosition = packetPosition;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
