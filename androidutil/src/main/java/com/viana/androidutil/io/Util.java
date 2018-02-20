package com.viana.androidutil.io;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Joao Viana on 17/02/2018.
 */

public class Util {
    public static boolean stringToBoolean(String pValor) {
        return numberToBoolean(Integer.valueOf(pValor));
    }

    public static boolean numberToBoolean(int pValor) {
        return (pValor == 1);
    }

    public static Date stringToDate(String pDate, String pFormat) {
        if (pDate == null)
            return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(pFormat, Locale.getDefault());
        return simpledateformat.parse(pDate, pos);
    }

    public static Date stringToDate(String pDate) {
        return stringToDate(pDate, "yyyy-MM-dd HH:mm:ss");
    }
}