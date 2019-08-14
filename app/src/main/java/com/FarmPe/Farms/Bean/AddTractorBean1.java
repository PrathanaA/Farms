package com.FarmPe.Farms.Bean;

public class AddTractorBean1 {

   private String image;
    private String prod_name,id;


    public AddTractorBean1(String image, String prod_name, String id) {

        this.image = image;
        this.prod_name = prod_name;
        this.id = id;

    }


    public String getProd_name() {
        return prod_name;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }


}
