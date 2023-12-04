package com.object;

import java.util.Random;

public class CodeUtil {
    private CodeUtil(){}

    //method to get code, 5-digit code, consisting of a-z, A-z, 0-9
    public static String getCode() {
        char[] chars = new char[52];
        for (int i = 0; i < chars.length; i++) {
            if(i <= 25) {
                chars[i] = (char)(65 + i);
            } else {
                chars[i] = (char)(97 + i - 26);
            }
        }
        Random r = new Random();
        char[] code = new char[5];
        for (int i = 0; i < code.length - 1; i++) {
            int index = r.nextInt(52);
            code[i] = chars[index];
        }
        code[code.length - 1] = (char)(48 + r.nextInt(10));
        int randomIndex = r.nextInt(4);
        char temp = code[randomIndex];
        code[randomIndex] = code[code.length - 1];
        code[code.length - 1] = temp;
        String verCode = new String(code);
        return verCode;
    }
}
