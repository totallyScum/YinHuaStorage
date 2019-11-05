package com.wandao.myapplication.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;

public class StringUtils {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String ArrayListToString(ArrayList<String> s) {
        String str = String.join(",", s.toArray(new String[s.size()]));

        return str;
    }

    public static ArrayList<String> StringToArrayList(String s) {
if (s.contains("["))
   s= s.split("\\[")[1];
if (s.contains("]"))
    s=s.split("\\]")[0];
        ArrayList<String> b = new ArrayList(Arrays.asList(s.split(",")));
        return b;
    }
}
