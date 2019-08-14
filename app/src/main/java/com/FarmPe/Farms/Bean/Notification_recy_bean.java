package com.FarmPe.Farms.Bean;

public class Notification_recy_bean {



    String noti_txt;

    String noti_code;
    String noti_id;

    public String getNoti_txt() {
        return noti_txt;
    }

    public void setNoti_txt(String noti_txt) {
        this.noti_txt = noti_txt;
    }

    public String getNoti_code() {
        return noti_code;
    }

    public void setNoti_code(String noti_code) {
        this.noti_code = noti_code;
    }

    public String getNoti_id() {
        return noti_id;
    }

    public void setNoti_id(String noti_id) {
        this.noti_id = noti_id;
    }



    public Notification_recy_bean(String noti_txt, String noti_code, String noti_id) {
        this.noti_txt=noti_txt;
        this.noti_code=noti_code;
        this.noti_id=noti_id;
    }


}


