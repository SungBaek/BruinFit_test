package com.example.joon8_000.myapplication.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.joon8_000.myapplication.R;
import com.example.joon8_000.myapplication.meallist.ChooseDiningHallActivity;
import com.example.joon8_000.myapplication.meallist.PickFoodTypeActivity;

public class HomePageActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    public void createUserProfile(View view) {
        Intent intent = new Intent(this, UserProfileCreationActivity.class);
        startActivity(intent);
    }
    public void viewUserProfile(View view) {
        Intent intent = new Intent(this, ViewUserProfile.class);
        startActivity(intent);
    }
    public void viewMeals(View view){
        Intent intent = new Intent (this, PickFoodTypeActivity.class);
        startActivity(intent);
    }
    public void openMeals(View view) {
        Intent intent = new Intent (this, ChooseDiningHallActivity.class);
        startActivity(intent);
    }
    public void openAbout(View view){
        //TODO: implement this
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
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
