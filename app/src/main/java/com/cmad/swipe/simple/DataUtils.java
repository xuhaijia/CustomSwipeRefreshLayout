package com.cmad.swipe.simple;

/**
 * Created by Cmad on 2015/5/13.
 */
public class DataUtils {

    public static String[] getData() {
        String[] data = new String[20];
        for (int i = 0; i < 20; i++) {
            data[i] = "item" + i;
        }
        return data;
    }
}
