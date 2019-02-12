package de.schplorg.blelib;

/**
 * Created by Sam Bäumer on 2/18/18.
 * sam.baeumer@schplorg.de
 */

public class D {
    public static String output;
    public static PrintCallback callback = null;

    public static void print(String s){
        if(callback != null){
            callback.print(s);
        }
    }
}

