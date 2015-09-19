package com.example.joon8_000.myapplication.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.joon8_000.myapplication.BruinFit;
import com.example.joon8_000.myapplication.R;
import com.example.joon8_000.myapplication.meallist.MealList;

import org.w3c.dom.Text;

public class ViewUserProfile extends Activity {

    TextView tv_name, tv_age, tv_gender, tv_weight, tv_height, tv_exercise, tv_eatBreakfast, tv_goal, tv_measurementSyst;

    public void startNewDay(View view){
        ((BruinFit) getApplication()).getUser().calculateDaily();
        MealList m = new MealList();
        ((BruinFit) getApplication()).setMealList(m);
        //GO TO HOME PAGE
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_user_profile);

        UserProfile tempUser = ((BruinFit) getApplication()).getUser();

        String userName = tempUser.getFirstName() + " " + tempUser.getLastName();
        String age = String.valueOf(tempUser.getAge());
        String gender = "";
        if (tempUser.getGender() == UserProfile.MALE)
            gender = "Male";
        else
            gender = "Female";

        String weight = String.valueOf(tempUser.getWeight());
        String height = String.valueOf(tempUser.getHeight());

        String exercise = "";
        if (tempUser.getExercise() == UserProfile.SEDENTARY)
            exercise = "Sedentary";
        else if (tempUser.getExercise() == UserProfile.LIGHT)
            exercise = "Light";
        else if (tempUser.getExercise() == UserProfile.MODERATE)
            exercise = "Moderate";
        else if (tempUser.getExercise() == UserProfile.ACTIVE)
            exercise = "Active";
        else if (tempUser.getExercise() == UserProfile.EXTRA_ACTIVE)
            exercise = "Very Active";
        else
            exercise = "Error";

        String eatBreakfast = "";
        if (tempUser.getEatBreakfast())
            eatBreakfast = "Yes";
        else
            eatBreakfast = "No";


        String goal = "";
        if (tempUser.getGoal() == UserProfile.LOSE)
            goal = "Lose Weight";
        else if (tempUser.getGoal() == UserProfile.STAY)
            goal = "Maintain Current Weight";
        else if (tempUser.getGoal() == UserProfile.GAIN)
            goal = "Gain Weight";
        else
            goal = "Error";

        String measurementSyst = "";
        if (tempUser.getMeasurementSyst() == UserProfile.IMPERIAL)
            measurementSyst = "Imperial";
        else if (tempUser.getMeasurementSyst() == UserProfile.METRIC)
            measurementSyst = "Metric";
        else
            measurementSyst = "Error";

        String totalCalorie;
        totalCalorie = String.valueOf(((BruinFit) getApplication()).getUser().getTarget(UserProfile.DAILY).calorie);
        String eatenCalorie = String.valueOf(((BruinFit) getApplication()).getMeal().getTotalNutrients().calorie);
        String totalFat = String.valueOf(((BruinFit) getApplication()).getUser().getTarget(UserProfile.DAILY).totalFat);
        String eatenFat = String.valueOf(((BruinFit) getApplication()).getMeal().getTotalNutrients().totalFat);



        tv_name = (TextView) findViewById(R.id.user_name);
        tv_name.setText(userName);

        tv_age = (TextView) findViewById(R.id.user_age);
        tv_age.setText(age);

        tv_gender = (TextView) findViewById(R.id.user_gender);
        tv_gender.setText(gender);

        tv_weight = (TextView) findViewById(R.id.user_weight);
        tv_weight.setText(weight);

        tv_height = (TextView) findViewById(R.id.user_height);
        tv_height.setText(height);


        TextView tv_totalCalorie = (TextView) findViewById(R.id.user_calorie);
        tv_totalCalorie.setText(totalCalorie);

        TextView tv_eatenCalorie = (TextView) findViewById(R.id.user_eatenCalorie);
        tv_eatenCalorie.setText(eatenCalorie);

        TextView tv_totalFat = (TextView) findViewById(R.id.user_fatgoal);
        tv_totalFat.setText(totalFat);

        TextView tv_eatenFat = (TextView) findViewById(R.id.user_eatenFat);
        tv_eatenFat.setText(eatenFat);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
