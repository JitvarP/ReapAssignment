package com.example.jitvar.reapassignment.Core;

import com.example.jitvar.reapassignment.Database.UCDatabaseConstants;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Date;

/**
 * Created by jitvar on 6/3/16.
 */

@DatabaseTable(tableName = UCDatabaseConstants.TABLE_PRODUCT)
public class UCProduct extends Model{
    public static final String COLUMN_BRAND_NAME = "brand_Name";
    public static final String COLUMN_DRUG_ID = "drug_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_MANUFACTURE = "manufacturer";
    public static final String COLUMN_SCHEME = "scheme";
    public static final String COLUMN_END_DATE = "end_date";
    public static final String COLUMN_IS_FAV = "is_fav";

    @DatabaseField(columnName = COLUMN_BRAND_NAME)
    private String brandName;

    @DatabaseField(columnName = COLUMN_DRUG_ID)
    private int drugID;

    @DatabaseField(columnName = COLUMN_NAME)
    private String name;

    @DatabaseField(columnName = COLUMN_MANUFACTURE)
    private String manufacturer;

    @DatabaseField(columnName = COLUMN_SCHEME)
    private String scheme;

    @DatabaseField(columnName = COLUMN_END_DATE)
    private String endDate;

    @DatabaseField(columnName = COLUMN_IS_FAV)
    private boolean isFav;


    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getDrugID() {
        return drugID;
    }

    public void setDrugID(int drugID) {
        this.drugID = drugID;
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

    public boolean isFav() {
        return isFav;
    }

    public void setIsFav(boolean isFav) {
        this.isFav = isFav;
    }
}
