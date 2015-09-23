package com.example.joon8_000.myapplication.meallist;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.joon8_000.myapplication.BruinFit;
import com.example.joon8_000.myapplication.R;
import com.example.joon8_000.myapplication.user.UserProfile;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import java.util.HashMap;

public class ChooseDiningHallActivity extends ActionBarActivity {

    public void destroyData(View view){
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("movie", "The Matrix"); //why do I need this?
        ParseCloud.callFunctionInBackground("flushNutrients", params, new FunctionCallback<Float>() {
            @Override
            public void done(Float ratings, ParseException e) {
                if (e == null) {
                    Toast.makeText(null, "it worked", Toast.LENGTH_SHORT);
                }
            }
        });
    }
    public void populateData(View view){
            final HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("movie", "The Matrix"); //why do I need this?
            ParseCloud.callFunctionInBackground("doLunch", params, new FunctionCallback<Float>() {
                @Override
                public void done(Float ratings, ParseException e) {
                    if (e == null) {
                        Toast.makeText(null, "it worked", Toast.LENGTH_SHORT);
                    }
                }
            });
    }

    //helper function for deciding if things are checked
    public int getCheckedTime(){
        if(((RadioButton)findViewById(R.id.breakfastCheck)).isChecked()){
            return UserProfile.BREAKFAST;
        }
        if(((RadioButton)findViewById(R.id.lunchCheck)).isChecked()){
            return UserProfile.LUNCH;
        }
        if(((RadioButton)findViewById(R.id.dinnerCheck)).isChecked()){
            return UserProfile.DINNER;
        }
        else
            return UserProfile.LUNCH; //by default
    }

    public void openHedrick(View view){
        //not implemented right now
        Toast.makeText(getBaseContext(),"not implemented",Toast.LENGTH_SHORT).show();
        ((BruinFit) getApplication()).getMeal().pullDatabase("hedrick",getCheckedTime());
        Intent intent = new Intent(this, PickFoodTypeActivity.class);
        if (((BruinFit) getApplication()).getMeal().getMeals().size() == 0) //it's empty
            Toast.makeText(getBaseContext(),"Nothing you can eat here", Toast.LENGTH_SHORT).show();
        else {
            startActivity(intent);
        }
    }
    public void openFeast(View view){
        ((BruinFit) getApplication()).getMeal().pullDatabase("feast",getCheckedTime());
        Intent intent = new Intent(this, PickFoodTypeActivity.class);
        if (((BruinFit) getApplication()).getMeal().getMeals().size() == 0) //it's empty
            Toast.makeText(getBaseContext(),"Nothing you can eat here", Toast.LENGTH_SHORT).show();
        else {
            startActivity(intent);
        }
    }
    public void openDeNeve(View view){
        ((BruinFit) getApplication()).getMeal().pullDatabase("deneve",getCheckedTime());
        Intent intent = new Intent(this, PickFoodTypeActivity.class);
        if (((BruinFit) getApplication()).getMeal().getMeals().size() == 0) //it's empty
            Toast.makeText(getBaseContext(),"Nothing you can eat here", Toast.LENGTH_SHORT).show();
        else {
            startActivity(intent);
        }
    }
    public void openCovel(View view){

        ((BruinFit) getApplication()).getMeal().pullDatabase("covel",getCheckedTime());
        Intent intent = new Intent(this, PickFoodTypeActivity.class);
        if (((BruinFit) getApplication()).getMeal().getMeals().size() == 0) //it's empty
            Toast.makeText(getBaseContext(),"Nothing you can eat here", Toast.LENGTH_SHORT).show();
        else {
            startActivity(intent);
        }
    }
    public void openSproul(View view){
        Toast.makeText(getBaseContext(),"not implemented",Toast.LENGTH_SHORT).show();

        ((BruinFit) getApplication()).getMeal().pullDatabase("sproul",getCheckedTime());
        Intent intent = new Intent(this, PickFoodTypeActivity.class);
        if (((BruinFit) getApplication()).getMeal().getMeals().size() == 0) //it's empty
            Toast.makeText(getBaseContext(),"Nothing you can eat here", Toast.LENGTH_SHORT);
        else {
            startActivity(intent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_dining_hall);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_dining_hall, menu);
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
