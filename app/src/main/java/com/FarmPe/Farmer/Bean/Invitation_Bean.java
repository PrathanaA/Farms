package com.FarmPe.Farmer.Bean;

public class Invitation_Bean {
    String name;
    String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String profile_pic;
    String id;
    String address;


    public Invitation_Bean(String name, String type, String profile_pic, String id,String address) {

        this.name = name;
        this.type = type;
        this.profile_pic = profile_pic;
        this.id = id;
        this.address = address;

    }


}



