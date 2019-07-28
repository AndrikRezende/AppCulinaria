package com.example.android.appculinaria;

import java.util.ArrayList;

public class Receita  {

    private String mName;
    private int mServings;
    private ArrayList<Ingredients> mIngredients;
    private ArrayList<Steps> mSteps;

    public Receita(){
    }

    public Receita(String name, int servings) {
        this.mName = name;
        this.mServings = servings;
        this.mIngredients=new ArrayList<Ingredients>();
        this.mSteps = new ArrayList<Steps>();
    }

    public void setIngredients(int quantity, String measure, String ingredient){
        mIngredients.add(new Ingredients(quantity, measure, ingredient));
    }

    public void setSteps(String shortDescription, String description, String videoURL){
        mSteps.add(new Steps(shortDescription, description, videoURL));
    }

    public String getName() {
        return mName;
    }

    public int getServings() {
        return mServings;
    }

    public Ingredients getIngredients(int indice) {
        if(indice>=0 && indice<mIngredients.size())
            return mIngredients.get(indice);
        else
            return null;
    }

    public Steps getSteps(int indice) {
        if(indice>=0 && indice<mSteps.size())
            return mSteps.get(indice);
        else
            return null;
    }

    public int IngredientsSize(){
        return mIngredients.size();
    }

    public int StepsSize(){
        return mSteps.size();
    }
}
