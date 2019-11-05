package com.wandao.myapplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static boolean isPhone(String inputText) {          //手机号认证
        Pattern p = Pattern.compile("^((14[0-9])|(13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(inputText);
        return m.matches();

    }
}
