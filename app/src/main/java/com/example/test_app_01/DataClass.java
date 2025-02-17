package com.example.test_app_01;

public class DataClass {

    private String dataTitle;
    private int dataDesc;
    private String dataPrice;
    private int dataImage;


    public String getDataTitle() {
        return dataTitle;
    }

    public int getDataDesc() {
        return dataDesc;
    }

    public String getDataPrice() {
        return dataPrice;
    }

    public int getDataImage() {
        return dataImage;
    }


    public DataClass(String dataTitle, int dataDesc, String dataPrice, int dataImage) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataPrice = dataPrice;
        this.dataImage = dataImage;
    }
}
