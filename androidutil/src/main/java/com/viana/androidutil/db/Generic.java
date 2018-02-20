package com.viana.androidutil.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.viana.androidutil.io.Util;
import com.viana.androidutil.io.StringBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Joao Viana on 17/02/2018.
 */

public class Generic {
    private SQLiteDatabase mSqLiteDatabase;

    public Generic(SQLiteDatabase pSqLiteDatabase) {
        this.mSqLiteDatabase = pSqLiteDatabase;
    }

    public <T> ArrayList<T> returnList(Class<T> pClass, ArrayList<Parametro> pParametros) throws Exception {
        StringBuilder sbQuery = new StringBuilder();
        sbQuery.appendLine("SELECT * ");
        sbQuery.appendLine("FROM " + pClass.getSimpleName());
        sbQuery.appendLine("WHERE 1=1");
        String[] parametros = null;
        if (pParametros != null) {
            parametros = new String[pParametros.size()];
            for (int i = 0; i < pParametros.size(); i++) {
                sbQuery.appendLine("AND " + pParametros.get(i).getCondicao());
                parametros[i] = pParametros.get(i).getValor();
            }
        }
        return executeQuery(pClass, sbQuery.toString(), parametros);

    }

    public <T> ArrayList<T> executeQuery(Class<T> pClass, String pComandoSql, String[] pParametros) throws Exception {
        ArrayList<T> lista = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mSqLiteDatabase.rawQuery(pComandoSql, pParametros);
            if (cursor.moveToFirst()) {
                do {
                    T objeto = pClass.newInstance();
                    for (Field campo : pClass.getDeclaredFields()) {
                        int index = cursor.getColumnIndex(campo.getName());
                        if (index > -1) {
                            campo.setAccessible(true);
                            if (campo.getType().equals(String.class))
                                campo.set(objeto, cursor.getString(index));
                            else if (campo.getType().equals(Date.class))
                                campo.set(objeto, Util.stringToDate(cursor.getString(index)));
                            else if (campo.getType().equals(boolean.class))
                                campo.set(objeto, Util.stringToBoolean(cursor.getString(index)));
                            else
                                campo.set(objeto, getValorCursor(cursor, index));
                        }
                    }
                    lista.add(objeto);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error while browsing");
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return lista;
    }

    private Object getValorCursor(Cursor pCursor, int pIndex) {
        switch (pCursor.getType(pIndex)) {
            case Cursor.FIELD_TYPE_STRING:
                return pCursor.getString(pIndex);
            case Cursor.FIELD_TYPE_INTEGER:
                return pCursor.getInt(pIndex);
            case Cursor.FIELD_TYPE_FLOAT:
                return pCursor.getDouble(pIndex);
            case Cursor.FIELD_TYPE_BLOB:
                return pCursor.getBlob(pIndex);
            default:
                return null;
        }
    }
}