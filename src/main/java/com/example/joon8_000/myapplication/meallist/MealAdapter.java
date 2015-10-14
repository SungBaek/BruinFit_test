package com.example.joon8_000.myapplication.meallist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.joon8_000.myapplication.R;

import java.util.ArrayList;

//converts list of meals to be able to displayed in listview
public class MealAdapter extends ArrayAdapter<Meal> {
    public MealAdapter(Context context, ArrayList<Meal> meals){
        super(context,0, meals);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Meal meal = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_meal, parent, false);
        }
        //set text views
        TextView mealName = (TextView) convertView.findViewById(R.id.mealName);
        TextView calorie = (TextView) convertView.findViewById(R.id.hmm);
        TextView fat = (TextView) convertView.findViewById(R.id.fat);
        mealName.setText(meal.getName());
        calorie.setText("Calorie" + String.valueOf(meal.getCalorie()));
        fat.setText("Fat" + String.valueOf(meal.getFat()));
        //set color to white if you can eat, if not, to gray.
        if (meal.getCanEat()) {
            convertView.setBackgroundColor(Color.WHITE);
        }
        else {
            convertView.setBackgroundColor(Color.GRAY);
        }
        return convertView;
    }
/*
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        Meal m = getItem(position);
        return m.getCanEat();

    }
*/
}


