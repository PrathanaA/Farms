package com.FarmPe.Farms.Bean;

public class Add_New_Address_Bean {

    String add_id;
    String add_pickup_frm;
    String add_name;
    String add_door_no;
    String add_street;
    String add_pincode;
    String add_landmark;
    String add_city;
    String add_mobile;
    String add_state;
    String add_district;
    String add_taluk;
    String add_hobli;
    String add_village;

    public String getAdd_city() {
        return add_city;
    }

    public void setAdd_city(String add_city) {
        this.add_city = add_city;
    }



    public String getAdd_village() {
        return add_village;
    }

    public void setAdd_village(String add_village) {
        this.add_village = add_village;
    }



    Boolean default_addr;


    public String getAdd_pickup_frm() {
        return add_pickup_frm;
    }

    public void setAdd_pickup_frm(String add_pickup_frm) {
        this.add_pickup_frm = add_pickup_frm;
    }


    public String getAdd_door_no() {
        return add_door_no;
    }

    public void setAdd_door_no(String add_door_no) {
        this.add_door_no = add_door_no;
    }



    public String getAdd_state() {
        return add_state;
    }

    public void setAdd_state(String add_state) {
        this.add_state = add_state;
    }

    public String getAdd_district() {
        return add_district;
    }

    public void setAdd_district(String add_district) {
        this.add_district = add_district;
    }

    public String getAdd_taluk() {
        return add_taluk;
    }

    public void setAdd_taluk(String add_taluk) {
        this.add_taluk = add_taluk;
    }

    public String getAdd_hobli() {
        return add_hobli;
    }

    public void setAdd_hobli(String add_hobli) {
        this.add_hobli = add_hobli;
    }



    public Boolean getDefault_addr() {
        return default_addr;
    }

    public void setDefault_addr(Boolean default_addr) {
        this.default_addr = default_addr;
    }


    public String getAdd_id() {
        return add_id;
    }

    public String getAdd_mobile() {
        return add_mobile;
    }

    public void setAdd_mobile(String add_mobile) {
        this.add_mobile = add_mobile;
    }

    public void setAdd_id(String add_id) {
        this.add_id = add_id;
    }

    public String getAdd_name() {
        return add_name;
    }

    public void setAdd_name(String add_name) {
        this.add_name = add_name;
    }

    public String getAdd_street() {
        return add_street;
    }

    public void setAdd_street(String add_street) {
        this.add_street = add_street;
    }

    public String getAdd_pincode() {
        return add_pincode;
    }

    public void setAdd_pincode(String add_pincode) {
        this.add_pincode = add_pincode;
    }

    public String getAdd_landmark() {
        return add_landmark;
    }

    public void setAdd_landmark(String add_landmark) {
        this.add_landmark = add_landmark;
    }


    public Add_New_Address_Bean(String add_name, String  add_door_no, String add_street, String add_landmark, String add_city, String add_pincode, String add_mobile, String add_pickup_frm, String add_state, String add_district, String add_taluk,
                                String add_hobli, String add_village, String add_id, boolean default_addr) {

        this.add_name = add_name;
        this.add_door_no =  add_door_no;
        this.add_street = add_street;
        this.add_landmark = add_landmark;
        this.add_city = add_city;
        this.add_pincode = add_pincode;
        this.add_mobile = add_mobile;
        this.add_pickup_frm = add_pickup_frm;
        this.add_state = add_state;
        this.add_district = add_district;
        this.add_taluk = add_taluk;
        this.add_hobli = add_hobli;
        this.add_village = add_village;
        this.add_id = add_id;
        this.default_addr = default_addr;
    }
}


