package com.viana.androidutil.io;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Joao Viana on 06/03/2018.
 */

public class Io {
    final public static String FormatDateTimeBr = "dd/MM/yyyy HH:mm:ss";
    final public static String FormatDateBr = "dd/MM/yyyy";
    final public static String FormatDateTimeDbBr = "yyyy-MM-dd HH:mm:ss";
    final public static String FormatDateDbBr = "yyyy-MM-dd";

    public static boolean stringToBoolean(String pValor) {
        return numberToBoolean(Integer.valueOf(pValor));
    }

    public static int booleanToNumber(boolean pValor) {
        return (pValor) ? 1 : 0;
    }

    public static boolean numberToBoolean(int pValor) {
        return (pValor == 1);
    }

    public static Date getDateByInput(String pData) throws Exception {
        return getDateTimeByInput(pData, FormatDateBr);
    }

    public static Date getDateTimeByInput(String pData, String pFormato) throws Exception {
        return new SimpleDateFormat(pFormato, Locale.getDefault()).parse(pData);
    }

    public static Date getDateTimeByInput(String pData) throws Exception {
        return getDateTimeByInput(pData, FormatDateTimeBr);
    }

    public static Date stringToDateTime(String pDate) {
        return stringToDateTime(pDate, FormatDateTimeDbBr);
    }

    public static Date stringToDateTime(String pDate, String pFormat) {
        try {
            if (isNullOrEmpty(pDate))
                return null;
            ParsePosition pos = new ParsePosition(0);
            SimpleDateFormat simpledateformat = new SimpleDateFormat(pFormat, Locale.getDefault());
            return simpledateformat.parse(pDate, pos);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String dateTimeToString(Date pDate) {
        return dateTimeToString(pDate, FormatDateTimeDbBr);
    }

    public static String dateTimeToString(Date pDate, String pFormato) {
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