package com.example.joon8_000.myapplication.parser;

import org.json.JSONObject;
import java.net.*;
import java.io.*;
/**
 * Created by joon8_000 on 8/3/2015.
 */

//NO USE FOR THIS CODE RIGHT NOW
    //ARCHIEVED
public class Parser {

    public static double stringToDouble(String s){
        try {
            return Double.parseDouble(s);
        }
        catch(NumberFormatException e) {
            return 0;
        }
    }

    public static int stringToInt(String s){
        try {
            return Integer.parseInt(s);
        }
        catch(NumberFormatException e){
            return 0;
        }
    }
}