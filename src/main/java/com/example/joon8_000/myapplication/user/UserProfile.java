package com.example.joon8_000.myapplication.user;

import com.example.joon8_000.myapplication.meallist.MealList;
import com.example.joon8_000.myapplication.meallist.Nutrients;

/**
 * Created by joon8_000 on 7/23/2015.
 */

//USERTARGET
public class UserProfile {
    //GENDER
    public static final int MALE = 0;
    public static final int FEMALE = 1;
    //If the user wants to gain, stay, or lose
    public static final int GAIN = 10;      // a lb a week
    public static final int STAY = 11;
    public static final int LOSE = 12;      // a lb a week

    //Lifestyle
    public static final int SEDENTARY = 15;     // little to no exercise a week
    public static final int LIGHT = 16;         // 1-3 days of exercise a week
    public static final int MODERATE = 17;      // 3-5 days of exercise a week
    public static final int ACTIVE = 18;        // 6-7 days of exercise a week
    public static final int EXTRA_ACTIVE = 19;  // very intensive/twice a day of strenuous exercise

    public static final int BREAKFAST = 20;
    public static final int LUNCH = 21;
    public static final int DINNER = 22;
    public static final int DAILY = 23;
    public static final int TOTAL = 24;

    // weight-height measurement
    public static final int METRIC = 30;
    public static final int IMPERIAL = 31; // default system

    public static final double BREAKFAST_PERCENT = 0.20;
    public static final double LUNCH_PERCENT = 0.40;
    public static final double DINNER_PERCENT = 0.40;


    public Nutrients brkTarget;
    public Nutrients lunchTarget;
    public Nutrients dinnerTarget;
    public Nutrients dailyTarget;

    public Nutrients lifeTotal;
    /*user input variables
    BMR, GENDER, ETC.
    */
    private String firstName;
    private String lastName;

    private int gender;
    private int age;
    private int weight;
    private int height;
    private double BMR;   // basal metabolic rate
    private int exercise;
    private int goal;
    private int measurementSyst;
    private boolean eatBreakfast;

    private MealList mealList;

    //constructor
    public UserProfile(){
       this.gender = UserProfile.MALE;
        this.age = 10;
        this.weight = 130;
        this.height = 20;
        this.firstName = "smith";
        this.lastName = "jake";
        this.exercise = 1;
        this.goal = UserProfile.STAY;
        this.eatBreakfast = true;
        this.measurementSyst = UserProfile.IMPERIAL;
        this.exercise = UserProfile.ACTIVE;
        brkTarget = new Nutrients();
        lunchTarget = new Nutrients();
        dinnerTarget = new Nutrients();
        dailyTarget =  new Nutrients();
        calcBMR();
        calculateDaily();

    }

    public UserProfile(int gender, int age, int weight, int height, String firstName, String lastName, int exercise, int goal, boolean eatBreakfast, int measurementSyst){
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.firstName = firstName;
        this.lastName = lastName;
        this.exercise = UserProfile.ACTIVE;
        this.goal = goal;
        this.eatBreakfast = eatBreakfast;
        this.measurementSyst = measurementSyst;
        brkTarget = new Nutrients();
        lunchTarget = new Nutrients();
        dinnerTarget = new Nutrients();
        dailyTarget = new Nutrients();

        calcBMR();
        calculateDaily();

    }

    //BMRcalculator, calculates BMR using Harris-Benedict Equation
    public void calcBMR(){
        if (measurementSyst == METRIC)
        {
            if (gender == MALE)
                BMR = 88.362 + ((double)weight * 13.397) + ((double)height * 4.799) - ((double)age * 5.677);
            else
                BMR = 447.593 + ((double)weight * 9.247) + ((double)height * 3.098) - ((double)age * 4.330);
        }
        else // default to IMPERIAL
        {
            if (gender == MALE)
                BMR = 88.362 + (((double)weight*0.454) * 13.397) + (((double)height*2.54) * 4.799) - ((double)age * 5.677);
            else
                BMR = 447.593 + (((double)weight*0.454) * 9.247) + (((double)height*2.54) * 3.098) - ((double)age * 4.330);
        }
    }

    //Depending on if the user wants to gain, lose or stay set 4 targets
    //brkTarget, lunchTarget, dinnerTarget, dailyTarget
    public int calculateTarget() {
        if (calculateDaily() == -1)
            return -1;
        if (eatBreakfast) {
            setMealsTarget(brkTarget, BREAKFAST_PERCENT);
            setMealsTarget(lunchTarget, LUNCH_PERCENT);
            setMealsTarget(dinnerTarget, DINNER_PERCENT);
        }
        else
        {
            setMealsTarget(brkTarget, 0);
            setMealsTarget(lunchTarget, 0.50);
            setMealsTarget(dinnerTarget, 0.50);
        }

        return 0;

    }
    public int calculateDaily(){
        int base = 0;
        if (exercise == SEDENTARY)
            base = (int)Math.round(BMR * 1.2);
        else if (exercise == LIGHT)
            base = (int)Math.round(BMR * 1.375);
        else if (exercise == MODERATE)
            base = (int)Math.round(BMR * 1.55);
        else if (exercise == ACTIVE)
            base = (int)Math.round(BMR * 1.725);
        else if (exercise == EXTRA_ACTIVE)
            base = (int)Math.round(BMR * 1.9);
        else
            return -1;

        if (goal == LOSE)
            dailyTarget.calorie = base - 500; // must burn extra 3500 calories a week
        else if (goal == GAIN)
            dailyTarget.calorie = base + 500; // must eat extra 3500 calories a week
        else if (goal == STAY)
            dailyTarget.calorie = base;
        else
            return -1;

        dailyTarget.totalFat = (dailyTarget.calorie * 0.275)/9;        // recommended
        dailyTarget.saturatedFat = (dailyTarget.calorie * 0.10)/9;   // max
        dailyTarget.transFat = 1;                                                               // max
        dailyTarget.cholesterol = 300;                                                          // max
        dailyTarget.sodium = 2300;                                                              // max
        dailyTarget.carbs = (dailyTarget.calorie * 0.55)/4;            // recommended
        if (gender == MALE) {
            dailyTarget.fiber = 31;                                                             // recommended
            dailyTarget.sugar = 36;                                                             // max
        }
        else if (gender == FEMALE){
            dailyTarget.fiber = 25;                                                             // recommended
            dailyTarget.sugar = 24;                                                             // max
        }
        else
            return -1;
        dailyTarget.protein = (dailyTarget.calorie * 0.225)/4;         // recommended

        // vitamins are not dependent on caloric intake, shoot for 100% on Nutrition Facts
        dailyTarget.vitA = 100;
        dailyTarget.vitC = 100;
        dailyTarget.calcium = 100;
        dailyTarget.iron = 100;

        return 0;
    }

    // calculate proportion of meal's nutrition based on daily total
    public void setMealsTarget(Nutrients n, double percentage) {
        n.calorie = (int)Math.round(percentage * dailyTarget.calorie);
        n.totalFat = percentage * dailyTarget.totalFat;
        n.saturatedFat = percentage * dailyTarget.saturatedFat;
        n.transFat = percentage * dailyTarget.transFat;
        n.cholesterol = percentage * dailyTarget.cholesterol;
        n.sodium = percentage * dailyTarget.sodium;
        n.carbs = percentage * dailyTarget.carbs;
        n.fiber = percentage * dailyTarget.fiber;
        n.sugar = percentage * dailyTarget.sugar;
        n.protein = percentage * dailyTarget.protein;
        n.vitA = (int)Math.round(percentage * dailyTarget.vitA);
        n.vitC = (int)Math.round(percentage * dailyTarget.vitC);
        n.calcium = (int)Math.round(percentage * dailyTarget.calcium);
        n.iron = (int)Math.round(percentage * dailyTarget.iron);
    }

    public Nutrients getTarget(int meal){
        if (meal == BREAKFAST)
           return brkTarget;
        else if (meal == LUNCH)
            return lunchTarget;
        else if (meal == DINNER)
            return dinnerTarget;
        else if (meal == DAILY)
            return dailyTarget;
        else
            return dailyTarget; // error message

    }


    //some AUX functions
    public int setGender(int gender){
        if (gender != MALE && gender != FEMALE){
            return -1; // error
        }
        this.gender = gender;
        return 0; //success
    }

    public void setWeight(int weight) { this.weight = weight; }
    public void setHeight(int height) {this.height = height; }
    public void setAge(int age) {this.age = age; }
    public void setFirstName(String name) {this.firstName = name; }
    public void setLastName(String name) {this.lastName = name; }
    public void setExercise(int exercise) {this.exercise = exercise; }
    public void setGoal(int goal) {this.goal = goal; }
    public void setMeasurementSyst(int measurementSyst) {this.measurementSyst = measurementSyst; }
    public void setEatBreakfast(boolean eat) {eatBreakfast = eat; }

    public int getGender() { return gender;}
    public int getWeight() { return weight;}
    public int getHeight() {return height;}
    public int getAge() {return age;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public int getExercise() {return exercise;}
    public int getGoal() {return goal;}
    public int getMeasurementSyst() {return measurementSyst;}
    public boolean getEatBreakfast() {return eatBreakfast;}


}
