package com.example.jitvar.reapassignment.WebEntities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jitvar on 6/3/16.
 */
public class WeProduct  {

    @SerializedName("brand-name")
    private String brandName;

    @SerializedName("drug-id")
    private int drugId;

    private String name;
    private String manufacturer;
    private String scheme;

    @SerializedName("end-date")
    private String endDate;

    @SerializedName("is-fav")
    private String isFav;


    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getIsFav() {
        return isFav;
    }

    public void setIsFav(String isFav) {
        this.isFav = isFav;
    }
}
