package com.FarmPe.Farmer.Bean;

public class AddTractorBean2 {

   private String image1;
    private String prod_name,id;
   /// private  int image;

    public AddTractorBean2(String image1, String prod_name, String id) {

        this.image1 = image1;
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
        return image1;
    }

/* public String getImage() {
        return image;
    }*/
}
