package com.FarmPe.Farms.Bean;

public class SelectLanguageBean {

    String vendor,imageicon;

    int languageid;


    public String getVendor() {
        return vendor;
    }

    public String getImageicon() {
        return imageicon;
    }

    public int getLanguageid() {
        return languageid;
    }

    public SelectLanguageBean( String vendor, int languageid,String imageicon) {
        this.languageid=languageid;
        this.vendor = vendor;
        this.imageicon=imageicon;
    }
}


