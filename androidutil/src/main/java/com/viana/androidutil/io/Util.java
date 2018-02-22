package com.viana.androidutil.io;

import java.text.DecimalFormat;
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
        try {
            if (pDate == null)
                return null;
            ParsePosition pos = new ParsePosition(0);
            SimpleDateFormat simpledateformat = new SimpleDateFormat(pFormat, Locale.getDefault());
            return simpledateformat.parse(pDate, pos);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Date stringToDate(String pDate) {
        return stringToDate(pDate, "yyyy-MM-dd HH:mm:ss");
    }

    public static String dateTimeToString(Date pDate) {
        return dateToString(pDate, "yyyy-MM-dd HH:mm:ss");
    }

    public static String dateToString(Date pDate, String pFormato) {
        try {
            if (pDate == null)
                return "";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pFormato, Locale.getDefault());
            return dateFormat.format(pDate);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static boolean isNullOrEmpty(String pValor) {
        if (pValor == null)
            return true;
        else if (pValor.trim().isEmpty())
            return true;

        return false;
    }

    public static String formatValue(Double valor) {
        DecimalFormat precision = new DecimalFormat("0.00");
        return precision.format(valor);
    }
}