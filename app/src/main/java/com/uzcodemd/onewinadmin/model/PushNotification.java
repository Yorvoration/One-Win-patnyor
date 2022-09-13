package com.uzcodemd.onewinadmin.model;

public class PushNotification {
    private NatificationData data;
    private String to;

    public PushNotification(NatificationData data, String to) {
        this.data = data;
        this.to = to;
    }

    public NatificationData getData() {
        return data;
    }

    public void setData(NatificationData data) {
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
