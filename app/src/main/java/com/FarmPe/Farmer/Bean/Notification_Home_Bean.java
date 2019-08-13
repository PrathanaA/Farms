package com.FarmPe.Farmer.Bean;




public class Notification_Home_Bean {

    public String getNotiftn_mess() {
        return notiftn_mess;
    }

    public void setNotiftn_mess(String notiftn_mess) {
        this.notiftn_mess = notiftn_mess;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String notiftn_mess, id;


    public Notification_Home_Bean( String notiftn_mess, String id) {
        this.notiftn_mess = notiftn_mess;
        this.id = id;
    }
}
