package com.example.lee.leeframe.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme 判断类（手机，邮箱）
 */

public class CheckUtil {
    
    public static boolean isPhone(String str) {
        Pattern p = Pattern.compile("^1[3-8]\\d{9}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isEmail(String str) {
        Pattern p = Pattern.compile("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$");
        Matcher m = p.matcher(str);
        return m.matches();
    }



}
