package com.example.joon8_000.myapplication.meallist;

/**
 * Created by joon8_000 on 8/3/2015.
 */
public class Nutrients {
    public int calorie;
    public double totalFat;
    public double saturatedFat;
    public double transFat;
    public double cholesterol;     // mg
    public double sodium;          // mg
    public double carbs;
    public double fiber;
    public double protein;
    public double sugar;
    // vitamins are not dependent on caloric intake, shoot for 100% on Nutrition Facts
    //vita, iron, calcium not avaiable on the mobile version.
    public int vitA;
    public int vitC;
    public int iron;
    public int calcium;

    public Nutrients()
    {
        calorie = 0;
        totalFat = 0;
        saturatedFat = 0;
        transFat = 0;
        cholesterol = 0;     // mg
        sodium = 0;          // mg
        carbs = 0;
        fiber = 0;
        protein = 0;
        sugar = 0;
        // vitamins are not dependent on caloric intake, shoot for 100% on Nutrition Facts
        vitA = 0;
        vitC = 0;
        iron = 0;
        calcium = 0;
    }

    public void addNutrients(Nutrients n){
        this.calorie += n.calorie;
        this.totalFat += n.totalFat;
        this.saturatedFat += n.saturatedFat;
        this.transFat += n.transFat;
        this.cholesterol += n.cholesterol;
        this.sodium += n.sodium;
        this.carbs += n.sodium;
        this.fiber += n.fiber;
        this.protein += n.protein;
        this.sugar += n.sugar;
        this.vitA += n.vitA;
        this.vitC += n.vitC;
        this.iron += n.iron;
        this.calcium += n.calcium;
    }
}
