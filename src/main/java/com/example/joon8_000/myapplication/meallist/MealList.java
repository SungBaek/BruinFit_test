package com.example.joon8_000.myapplication.meallist;

import android.util.Log;
import android.widget.Toast;

import com.example.joon8_000.myapplication.parser.Parser;
import com.example.joon8_000.myapplication.user.UserProfile;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by joon8_000 on 7/21/2015.
 */

/*
        Just tracking calories and fat for now. will add stuff later
        Need to decide if we are able to take the "food triangle" into consideration.
        eventually add a variable for
 */
public class MealList {

    protected ArrayList<Meal> meals;

    public Nutrients totalNutrients;

    public String[] printTen(){
        String[] poo = new String[10];
        for (int i = 0 ; i < 10 && i < meals.size(); i++){
            poo[i] = meals.get(i).getName();
        }
        return poo;
    }

    //get all objects and save it into the hash map.
    public MealList()    {
        /*
       ParseQuery<ParseObject> query = ParseQuery.getQuery("Nutrients");
       this.totalNutrients = new Nutrients();*/
       this.meals = new ArrayList<>();/*
       try {
           List<ParseObject> list  = query.find();
           //for each list go through it and put it in the hash map.
           for (ParseObject e : list){
               meals.add(objectParser(e));
           }
       }
       catch (ParseException e) { //failed to retrieve with given query
            Log.e("meallist", "Failed to find any objects");
       }*/
        this.totalNutrients = new Nutrients();
    }

    public String getTimeString(int time){
        if (time == UserProfile.BREAKFAST){
            return "breakfast";
        }
        else if(time == UserProfile.LUNCH){
            return "lunch";
        }
        else if(time == UserProfile.DINNER){
            return "dinner";
        }
        else //error
            return "lunch"; //by default
    }

    public void pullDatabase(String dininghall, int time){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Nutrients");
        query.whereEqualTo("diningHall", dininghall);
        query.whereEqualTo("time", getTimeString(time));
        query.setLimit(500);
        this.meals = new ArrayList<>();
        Log.i("pulldata", "pulling data with dining hall : " + dininghall + " and time" + getTimeString(time));
        try{
            List<ParseObject> list = query.find();
            for (ParseObject e : list){
                meals.add(objectParser(e));
            }
        }
        catch(ParseException e) {
            Log.e("meallist", "failed");
        }
        //sort according to calorie
        Collections.sort(this.meals, new Comparator<Meal>() {
            @Override
            public int compare(Meal lhs, Meal rhs) {
                if (lhs.getCalorie() > rhs.getCalorie())
                    return -1;
                else if(lhs.getCalorie() < rhs.getCalorie())
                    return 1;
                else
                    return 0;

            }
        });
        //for test
        printTen();
    }

    //helper function for calculating total
    protected void calcTotal(int foodTime){
        //for each meal get the total fat and total calorie already eaten.
        //iterate through every entry and add them all
        for(Meal e : meals) {
            totalNutrients.addNutrients(e.getNutrients());
        }
    }
    //helper function for parsing parseobject
    public Meal objectParser(ParseObject e){
        Meal m;

        Nutrients nutrient = new Nutrients();
        //calorie
        nutrient.calorie = Parser.stringToInt(e.getString("calorie")); // data are stored as an array of size 1
        //totalfat
        nutrient.totalFat = Parser.stringToDouble(e.getString("totFat"));
        //saturatedfat
        nutrient.saturatedFat = Parser.stringToDouble(e.getString("satFat"));
        //transfat
        nutrient.transFat = Parser.stringToDouble(e.getString("transFat"));
        //cholesterol
        nutrient.cholesterol = Parser.stringToDouble(e.getString("chol"));
        //sodium
        nutrient.sodium = Parser.stringToDouble(e.getString("sod"));
        //fiber
        nutrient.fiber = Parser.stringToDouble(e.getString("fiber"));
        //protein.
        nutrient.protein = Parser.stringToDouble(e.getString("protein"));
        //sugar
        nutrient.sugar = Parser.stringToDouble(e.getString("sugar"));
        //name
        String name = e.getString("name");

        m = new Meal(name, nutrient);
        return m;
    }

    //separate for breakfast, lunch, dinner?
    public int calcEateries(UserProfile userProfile, int foodTime)
    {
        for(Meal chosenMeal : meals) {
            //go through every elements in meals then see if nutrientvalue + current >= maxallowed.
            if (chosenMeal.getNutrients().calorie > userProfile.getTarget(foodTime).calorie - this.totalNutrients.calorie
                    || chosenMeal.getNutrients().totalFat > userProfile.getTarget(foodTime).totalFat - this.totalNutrients.totalFat) {
                chosenMeal.setCanEat(false);
            }
            else
                chosenMeal.setCanEat(true);

        }
        return 0; //so it compiles
    }

    //AUX FUNCTIONS
    public Meal getMeal(String s) {
        for (Meal e : this.meals){
            if (e.getName() == s){
                return e;
            }
        }
        //couldn't find it, return a dummy meal?
        Nutrients tempN = new Nutrients();
        Meal temp = new Meal("dummy", tempN);
        return temp;
    }
    public ArrayList<Meal> getMeals()
    { return meals; }
    public void addCalorie(int calorie){
        this.totalNutrients.calorie += calorie;
    }
    public void addFat(double fat){
        this.totalNutrients.totalFat += fat;
    }
    public Nutrients getTotalNutrients(){
        return this.totalNutrients;
    }

}
