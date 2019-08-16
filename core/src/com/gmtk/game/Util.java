package com.gmtk.game;

import com.badlogic.gdx.Gdx;

public class Util {
    public static String IntegerToRoman(int n){
        String roman="";
        int repeat;
        int magnitude[]={1000,900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String symbol[]={"M","CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for(int x=0; x<magnitude.length; x++){
            repeat=n/magnitude[x];
            for(int i=1; i<=repeat; i++){
                roman=roman + symbol[x];
            }
            n=n%magnitude[x];
        }
        return roman;
    }

    public static void betterLog(String tag, int i) {
        Gdx.app.log(tag, Integer.toString(i));
    }
    public static void betterLog(String tag, float i) {
        Gdx.app.log(tag, Float.toString(i));
    }
    public static void betterLog(String tag, boolean i) {
        Gdx.app.log(tag, Boolean.toString(i));
    }
    public static void betterLog(String tag, String i) {
        Gdx.app.log(tag, i);
    }
    public static void betterLog(String message) {
        Gdx.app.log("LOG", message);
    }
}

/*
To do:
Make score display larger
Refine the menu screens
 */