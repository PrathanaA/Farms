package com.FarmPe.Farmer.Bean;

public class ConnectionBean {

    String  name,proffesion,location,image,id,phone_no;


    public ConnectionBean(String name, String proffesion, String location, String image,String id,String phone_no) {

        this.name = name;
        this.proffesion = proffesion;
        this.location = location;
        this.image = image;
        this.phone_no = phone_no;

        this.id = id;

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


