package com.FarmPe.Farmer.Bean;

public class ConnectionBean {

    String  name;
    String proffesion;
    String location;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    String state;
    String image;
    String id;
    String phone_no;


    public ConnectionBean(String name, String proffesion, String location,String state, String image,String id,String phone_no) {

        this.name = name;
        this.proffesion = proffesion;
        this.location = location;
        this.state = state;
        this.image = image;
        this.id = id;

        this.phone_no = phone_no;


        }

    public String getName() {
        return name;
    }

    public String getProffesion() {
        return proffesion;
    }

    public String getLocation() {
        return location;
    }

    public String getImage() {
        return image;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public String getId() {
        return id;
    }
}


