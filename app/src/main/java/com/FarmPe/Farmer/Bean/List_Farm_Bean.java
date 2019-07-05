package com.FarmPe.Farmer.Bean;

public class List_Farm_Bean {

    String farm_list_name;

    public String getFarm_list_name() {
        return farm_list_name;
    }

    public void setFarm_list_name(String farm_list_name) {
        this.farm_list_name = farm_list_name;
    }

    public String getFarm_list_id() {
        return farm_list_id;
    }

    public void setFarm_list_id(String farm_list_id) {
        this.farm_list_id = farm_list_id;
    }

    String farm_list_id;





    public List_Farm_Bean(String farm_list_name, String farm_list_id) {
        this.farm_list_name = farm_list_name;
        this.farm_list_id = farm_list_id;

    }

}


