package com.example.jitvar.reapassignment.WebEntities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jitvar on 6/3/16.
 */
public class WeData {

    @SerializedName("2329")
    private WeProduct product_0;

    @SerializedName("2335")
    private WeProduct product_1;

    @SerializedName("2338")
    private WeProduct product_2;

    @SerializedName("2345")
    private WeProduct product_3;

    @SerializedName("2347")
    private WeProduct product_4;

    @SerializedName("2353")
    private WeProduct product_5;

    @SerializedName("2356")
    private WeProduct product_6;

    @SerializedName("2431")
    private WeProduct product_7;

    @SerializedName("2436")
    private WeProduct product_8;

    @SerializedName("2438")
    private WeProduct product_9;

    @SerializedName("2439")
    private WeProduct product_10;

    @SerializedName("2444")
    private WeProduct product_11;

    @SerializedName("2446")
    private WeProduct product_12;

    @SerializedName("2454")
    private WeProduct product_13;

    @SerializedName("2464")
    private WeProduct product_14;

    @SerializedName("2473")
    private WeProduct product_15;

    @SerializedName("2475")
    private WeProduct product_16;

    @SerializedName("2543")
    private WeProduct product_17;

    @SerializedName("2554")
    private WeProduct product_18;

    @SerializedName("2573")
    private WeProduct product_19;




    public WeProduct getProduct(int i){
        switch (i){
            case 0:
                return product_0;
            case 1:
                return product_1;
            case 2:
                return product_2;
            case 3:
                return product_3;
            case 4:
                return product_4;
            case 5:
                return product_5;
            case 6:
                return product_6;
            case 7:
                return product_7;
            case 8:
                return product_8;
            case 9:
                return product_9;
            case 10:
                return product_10;
            case 11:
                return product_11;
            case 12:
                return product_12;
            case 13:
                return product_13;
            case 14:
                return product_14;
            case 15:
                return product_15;
            case 16:
                return product_16;
            case 17:
                return product_17;
            case 18:
                return product_18;
            case 19:
                return product_19;


            default:
                return null;
        }
    }
}
