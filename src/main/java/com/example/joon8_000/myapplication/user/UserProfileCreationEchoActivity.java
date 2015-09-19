package com.example.joon8_000.myapplication.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joon8_000.myapplication.R;
import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;

import java.util.HashMap;
import java.util.Map;

public class UserProfileCreationEchoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //setContentView(R.layout.activity_user_profile_creation_echo);

        Intent intent = getIntent();

        //get the strings
        String weight = intent.getStringExtra(UserProfileCreationActivity.WEIGHT);
        String height = intent.getStringExtra(UserProfileCreationActivity.HEIGHT);
        String gender = intent.getStringExtra(UserProfileCreationActivity.GENDER);

        //create layouts
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView textView1 = new TextView(this);
        textView1.setText(weight);
        TextView textView2= new TextView(this);
        textView2.setText(height);
        TextView textView3= new TextView(this);
        textView3.setText(gender);

        linearLayout.addView(textView1);
        linearLayout.addView(textView2);
        linearLayout.addView(textView3);


        //set this
        setContentView(linearLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile_creation_echo, menu);
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
