package com.example.android.appculinaria;

import org.parceler.Parcel;

@Parcel
public class Ingredients{

    int mQuantity;
    String mMeasure;
    String mIngredient;

    public Ingredients(){
    }

    public Ingredients(int quantity, String measure, String ingredient) {
        this.mQuantity = quantity;
        this.mMeasure = measure;
        this.mIngredient = ingredient;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public String getIngredient() {
        return mIngredient;
    }

}// fim class
