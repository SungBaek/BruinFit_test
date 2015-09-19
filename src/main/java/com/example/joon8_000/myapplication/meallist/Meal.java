package com.example.joon8_000.myapplication.meallist;

import com.example.joon8_000.myapplication.user.UserProfile;

/**
 * Created by joon8_000 on 7/21/2015.
 */
public class Meal {

    public static final int BREAKFAST = UserProfile.BREAKFAST;
    public static final int LUNCH = UserProfile.LUNCH;
    public static final int DINNER = UserProfile.DINNER;
    public static final int TOTAL = UserProfile.TOTAL;
    public static final int DAILY = UserProfile.DAILY;


    private int brkCount;
    private int lunchCount;
    private int dinnerCount;
    private int totalCount;
    protected String mealName;
    protected boolean canEat;
    protected Nutrients nutrients;

    //methods
    public Meal(String mealName, Nutrients nutrients)
    {
        this.brkCount = 0;
        this.lunchCount = 0;
        this.dinnerCount = 0;
        this.totalCount = 0;
        this.mealName = mealName;
        this.nutrients = nutrients;
        canEat = true;
    }

    public int getCount(int foodTime) {
        if(foodTime == BREAKFAST)
            return this.brkCount;
        else if(foodTime == LUNCH)
            return this.lunchCount;
        else if(foodTime == DINNER)
            return this.dinnerCount;
        else if(foodTime == DAILY)
            return this.totalCount;
        else
            return -1; //it's error
    }

    public void eatThis(MealList mealList, int mealTime) {
        if(mealTime == BREAKFAST){
            brkCount++;
            totalCount++;
            mealList.addCalorie(this.nutrients.calorie);
            mealList.addFat(this.nutrients.totalFat);
        }
        else if(mealTime == LUNCH){
            lunchCount++;
            totalCount++;
            mealList.addCalorie(this.nutrients.calorie);
            mealList.addFat(this.nutrients.totalFat);

        }
        else if(mealTime == DINNER){
            dinnerCount++;
            totalCount++;
            mealList.addCalorie(this.nutrients.calorie);
            mealList.addFat(this.nutrients.totalFat);
        }
        else if(mealTime == DAILY){
            dinnerCount++;
            totalCount++;
            mealList.addCalorie(this.nutrients.calorie);
            mealList.addFat(this.nutrients.totalFat);
        }
        else
            ; //error
        }

    //In case there was a user error.
    public void setCount(int mealTime, int count){
        if (mealTime == BREAKFAST){
            totalCount = totalCount + (count - brkCount);
            brkCount = count;
        }
        else if (mealTime == LUNCH){
            totalCount = totalCount + (count - lunchCount);
            lunchCount = count;
        }
        else if (mealTime == DINNER){
            totalCount = totalCount + (count - dinnerCount);
            dinnerCount = count;
        }
        else  //error
            ;
    }

    public String getName() { return mealName; }
    public double getFat() { return nutrients.totalFat; }
    public int getCalorie() {return nutrients.calorie; }

    public int getBrkCount() {
        return brkCount;
    }

    public int getLunchCount() {
        return lunchCount;
    }

    public int getDinnerCount() {
        return dinnerCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public String getMealName() {
        return mealName;
    }

    public boolean isCanEat() {
        return canEat;
    }

    public void setBrkCount(int brkCount) {
        this.brkCount = brkCount;
    }

    public void setLunchCount(int lunchCount) {
        this.lunchCount = lunchCount;
    }

    public void setDinnerCount(int dinnerCount) {
        this.dinnerCount = dinnerCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public void setNutrients(Nutrients nutrients) {
        this.nutrients = nutrients;
    }

    public void setCanEat(boolean canEat) { this.canEat= canEat; }
    public boolean getCanEat() { return this.canEat; }
    public Nutrients getNutrients() { return this.nutrients; }

}

