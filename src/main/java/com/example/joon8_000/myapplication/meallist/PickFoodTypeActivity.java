package com.example.joon8_000.myapplication.meallist;


import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joon8_000.myapplication.BruinFit;
import com.example.joon8_000.myapplication.R;
import com.example.joon8_000.myapplication.user.UserProfile;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class PickFoodTypeActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    //test function
    public void testParseQuery(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Nutrients");

        List<Integer> temp = new Vector<>();
        try {
            List<ParseObject> list  = query.find();
            //for each list go through it and put it in the hash map.
            for (ParseObject e : list){
                 Log.i("mealllist",e.getString("name"));
            }

        }
        catch (ParseException e) { //failed to retrieve with given query
            Log.e("meallist", "Failed to find any objects");
        }
    }

    public void testMealList(){
        String[] temp = ((BruinFit) getApplication()).getMeal().printTen();
        for ( String e : temp){
            Log.i("meallist", e);
        }
    }
    public void createMeal(){
        //((BruinFit) getApplication()).setMealList();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_food_type);
        //tool bar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //intialize variables
        //intiations
        createMeal();
        //testMealList();
        TextView totalCalorie = (TextView) findViewById(R.id.totalCalorie);
        TextView eatenCalorie = (TextView) findViewById(R.id.eatenCalorie);
        TextView totalFat = (TextView) findViewById(R.id.totalFat);
        TextView eatenFat = (TextView) findViewById(R.id.eatenFat);

        int iTotalCalorie = ((BruinFit) getApplication()).getUser().getTarget(UserProfile.DAILY).calorie;
        int iEatenCalorie = ((BruinFit) getApplication()).getMeal().getTotalNutrients().calorie;
        double iTotalFat = ((BruinFit) getApplication()).getUser().getTarget(UserProfile.DAILY).totalFat;
        double iEatenFat =  ((BruinFit) getApplication()).getMeal().getTotalNutrients().totalFat;


        totalCalorie.setText("Total Calorie : " + String.valueOf(iTotalCalorie));
        eatenCalorie.setText("Eaten Calorie : " + String.valueOf(iEatenCalorie));
        totalFat.setText("Fat Goal : " + String.valueOf(Math.round(iTotalFat)));
        eatenFat.setText("Eaten Fat: " + String.valueOf(iEatenFat));
        //listview intialization
        ArrayList<Meal> meals = ((BruinFit) getApplication()).getMeal().getMeals();
        //custom adapter
        final MealAdapter adapter = new MealAdapter(this, meals);

        ListView listView = (ListView)findViewById(R.id.listview);
        //display listview
        listView.setAdapter(adapter);

        //set onitemclick listener to handle user inputs
        //when a user clicks on a valid item, it counts as eaten.
        listView.setOnItemClickListener(new OnItemClickListener(){
            //when item is clicked do this.
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Meal m = (Meal) parent.getItemAtPosition(position);
                MealList ml = ((BruinFit) getApplication()).getMeal();
                UserProfile u = ((BruinFit) getApplication()).getUser();

               // Toast.makeText(getBaseContext(), m.getName(), Toast.LENGTH_SHORT).show();
                if (m.getCanEat() ) {
                    m.eatThis(ml, UserProfile.DAILY);
                    ml.calcEateries(u, UserProfile.DAILY);
                    view.setBackgroundColor(Color.BLUE);

                    TextView totalCalorie = (TextView) findViewById(R.id.totalCalorie);
                    TextView eatenCalorie = (TextView) findViewById(R.id.eatenCalorie);
                    TextView eatenFat = (TextView) findViewById(R.id.eatenFat);

                    int iTotalCalorie= ((BruinFit) getApplication()).getUser().getTarget(UserProfile.DAILY).calorie;
                    int iEatenCalorie= ((BruinFit) getApplication()).getMeal().getTotalNutrients().calorie;
                    double dEatenFat = ((BruinFit) getApplication()).getMeal().getTotalNutrients().totalFat;
                    totalCalorie.setText("Total Calorie : " + String.valueOf(iTotalCalorie));
                    eatenCalorie.setText("Eaten Calorie : " + String.valueOf(iEatenCalorie));
                    eatenFat.setText("Eaten Fat : " + String.valueOf(Math.round(dEatenFat)));
                    //refresh the listview
                    adapter.notifyDataSetChanged();
                }
                else{
                    Log.i("listview", "can't eat this");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pick_food_type, menu);
        return true;   }

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
