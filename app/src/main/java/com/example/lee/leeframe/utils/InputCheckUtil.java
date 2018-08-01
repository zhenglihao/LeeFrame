package com.example.lee.leeframe.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme 输入框判断检测工具类
 */

public class InputCheckUtil {

    public static final int CHINESS_ENGLISH = 0;
    public static final int CHINESS = 1;
    public static final int ENGLISH_NUM = 2;
    public static final int WEIXIN = 3;

    public static void addTextLimit(final EditText et, final int limitType) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = et.getText().toString();
                String str;
                switch (limitType) {
                    case CHINESS_ENGLISH:
                        str = stringECFilter(editable.toString());
                        break;
                    case CHINESS:
                        str = stringCFilter(editable.toString());
                        break;
                    case ENGLISH_NUM:
                        str = stringENFilter(editable.toString());
                        break;
                    case WEIXIN:
                        str = stringWeixinFilter(editable.toString());
                        break;
                    default:
                        str = stringALLFilter(editable.toString());
                }
                if (!editable.equals(str)) {
                    et.setText(str);
                    //设置新的光标所在位置
                    et.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // 只允许中英文
    public static String stringECFilter(String str) throws PatternSyntaxException {
        // 仅仅同意字母和汉字
        String regEx = "[^a-zA-Z\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    // 只允许中文
    public static String stringCFilter(String str) throws PatternSyntaxException {
        // 仅仅同意汉字
        String regEx = "[^\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    //  只允许英文和数字
    public static String stringENFilter(String str) throws PatternSyntaxException {
        // 仅仅同意字母和数字
        String regEx = "[^a-zA-Z0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    // 只允许 中英数
    public static String stringALLFilter(String str) throws PatternSyntaxException {
        // 仅仅同意字母、数字和汉字
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    // 微信》仅支持6-20个字母、数字、下划线或减号，以字母开头。
    public static String stringWeixinFilter(String str) throws PatternSyntaxException {
        // 仅仅同意字母、数字和汉字
        String regEx = "[^a-zA-Z0-9_-]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }


    //邮箱
    public static boolean emailCheck(String email) {
        String regEx = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(email);
        return m.matches();
    }


    // 手机号码
    public static boolean isPhone(String str) {
        Pattern p = Pattern.compile("^1[3-8]\\d{9}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    //微信号
    public static boolean weixinCheck(String weixin) {
        String regEx = "^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}+$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(weixin);
        return m.matches();
    }




}
