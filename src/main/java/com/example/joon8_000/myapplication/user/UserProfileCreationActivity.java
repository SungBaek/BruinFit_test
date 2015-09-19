package com.example.joon8_000.myapplication.user;

import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.joon8_000.myapplication.BruinFit;
import com.example.joon8_000.myapplication.R;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import java.util.HashMap;

/**
 * Created by joon8_000 on 8/2/2015.
 */

public class UserProfileCreationActivity extends AppCompatActivity {
    public final static String GENDER = "com.example.joon8_000.myapplication.User.GENDER";
    public final static String WEIGHT = "com.example.joon8_000.myapplication.User.WEIGHT";
    public final static String HEIGHT = "com.example.joon8_000.myapplication.User.HEIGHT";
    public final static String LAST = "com.example.joon8_000.myapplication.User.LAST";
    public final static String FIRST = "com.example.joon8_000.myapplication.User.FIRST";
    private Toolbar toolbar;

    public void testParse() {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("movie", "The Matrix"); //why do I need this?
        ParseCloud.callFunctionInBackground("populateDiningHallMenus", params, new FunctionCallback<Float>() {
            @Override
            public void done(Float ratings, ParseException e) {
                if (e == null) {
                    Toast.makeText(null, "it worked", Toast.LENGTH_SHORT);
                }
            }
        });
    }
    public void createUser(View view){
        String firstName = ((EditText)findViewById(R.id.first_name)).getText().toString();
        String lastName = ((EditText) findViewById(R.id.last_name)).getText().toString();
        int gender=0;

        if(((RadioButton)findViewById(R.id.female)).isChecked()){
            gender = UserProfile.FEMALE;
        }
        else if(((RadioButton)findViewById(R.id.male)).isChecked()){
            gender = UserProfile.MALE;
        }
        else{
            //error
        }
        String Sweight =((EditText) findViewById(R.id.weight)).getText().toString();
        int weight = Integer.parseInt(Sweight);
        int height = Integer.parseInt(((EditText) findViewById(R.id.height)).getText().toString());
        int gain = 0;

        if(((RadioButton)findViewById(R.id.lose)).isChecked()){
            gain = UserProfile.LOSE;
        }
        else if(((RadioButton)findViewById(R.id.stay)).isChecked()){
            gain = UserProfile.STAY;
        }
        else if (((RadioButton)findViewById(R.id.gain)).isChecked()){
            gain = UserProfile.GAIN;
        }
        else{
            //error
        }
        int age = 10;
        UserProfile tempUser = new UserProfile(gender,age,weight,height,firstName,lastName,UserProfile.ACTIVE,gain,true,UserProfile.IMPERIAL);
        //set this
        ((BruinFit) getApplication()).setUser(tempUser);
        Toast.makeText(getApplicationContext(), "Created User!", Toast.LENGTH_SHORT).show();

    }


    public void callEcho(View view) {
        Intent intent = new Intent(this, UserProfileCreationEchoActivity.class);
        EditText weight = (EditText) findViewById(R.id.weight);
        EditText height = (EditText) findViewById(R.id.height);
        EditText last = (EditText) findViewById(R.id.last_name);
        EditText first = (EditText) findViewById(R.id.first_name);
        //convert from edittext to strings
        String msgWeight = weight.getText().toString();
        String msgHeight = height.getText().toString();
        String msgLast = last.getText().toString();
        String msgFirst = first.getText().toString();
        int age = 0;
        //store them 0in intent
        intent.putExtra(WEIGHT, msgWeight);
        intent.putExtra(HEIGHT, msgHeight);
        intent.putExtra(LAST, msgLast);
        intent.putExtra(FIRST, msgFirst);
       // ((BruinFit) this.getApplication()).getUser().setBasicProperties(Integer.getInteger("male"), age, Integer.getInteger(msgWeight),
       //         Integer.getInteger(msgHeight), msgFirst, msgLast);

        testParse();
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_profile_user);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
