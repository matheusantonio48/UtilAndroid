package com.viana.androidutil.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.viana.androidutil.io.Io;
import com.viana.androidutil.io.StringBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Joao Viana on 17/02/2018.
 * Incluído método toSave on 20/02/2018.
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
        if (pClass.isArray())
            throw new Exception("class type not allowed");

        ArrayList<T> lista = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mSqLiteDatabase.rawQuery(pComandoSql, pParametros);
            if (cursor.moveToFirst()) {
                do {
                    T objeto = pClass.newInstance();
                    for (Field campo : pClass.getDeclaredFields()) {
                        if (campo.getType().isArray())
                            continue;
                        int index = cursor.getColumnIndex(campo.getName());
                        if (index > -1) {
                            campo.setAccessible(true);
                            if (campo.getType().equals(String.class))
                                campo.set(objeto, cursor.getString(index));
                            else if (campo.getType().equals(Date.class))
                                campo.set(objeto, Io.stringToDate(cursor.getString(index)));
                            else if (campo.getType().equals(boolean.class) || campo.getType().equals(Boolean.class))
                                campo.set(objeto, Io.stringToBoolean(cursor.getString(index)));
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

    public void toSave(Object pObject) throws Exception {
        if (pObject.getClass().isArray())
            throw new Exception("class type not allowed");

        ContentValues values = new ContentValues();
        ArrayList<Field> camposChaves = new ArrayList<>();
        for (Field campo : pObject.getClass().getDeclaredFields()) {
            if (!campo.getType().isArray()) {
                campo.setAccessible(true);

                if (campo.get(pObject) != null) {
                    if (campo.getAnnotation(Key.class) != null)
                        camposChaves.add(campo);

                    if (campo.getType().equals(String.class))
                        values.put(campo.getName(), (String) campo.get(pObject));
                    else if (campo.getType().equals(boolean.class) || campo.getType().equals(Boolean.class))
                        values.put(campo.getName(), (boolean) campo.get(pObject));
                    else if (campo.getType().equals(int.class) || campo.getType().equals(Integer.class))
                        values.put(campo.getName(), (int) campo.get(pObject));
                    else if (campo.getType().equals(Date.class))
                        values.put(campo.getName(), Io.dateTimeToString((Date) campo.get(pObject)));
                    else if (campo.getType().equals(Double.class) || campo.getType().equals(double.class)
                            || campo.getType().equals(Float.class) || campo.getType().equals(float.class))
                        values.put(campo.getName(), (double) campo.get(pObject));
                }
            }
        }

        if (values.size() == 0)
            throw new Exception("no parameter informed");

        if (camposChaves.size() > 0) {
            String[] Campos = new String[camposChaves.size()];
            String[] Valores = new String[camposChaves.size()];
            StringBuilder Condicao = new StringBuilder();
            for (int i = 0; i < camposChaves.size(); i++) {
                Campos[i] = camposChaves.get(i).getName();
                Valores[i] = String.valueOf(camposChaves.get(i).get(pObject));
                Condicao.append(String.format("%1s = ?", Campos[i]));
                if (i < camposChaves.size() - 1)
                    Condicao.append(" AND ");
            }
            if (!isCadastrado(pObject.getClass().getSimpleName(), Campos, Valores))
                mSqLiteDatabase.insert(pObject.getClass().getSimpleName(), null, values);
            else
                mSqLiteDatabase.update(pObject.getClass().getSimpleName(), values, Condicao.toString(), Valores);
        } else
            mSqLiteDatabase.insert(pObject.getClass().getSimpleName(), null, values);
    }

    public boolean isCadastrado(String pTabela, String pCampo, String pValor) throws Exception {
        return isCadastrado(pTabela, new String[]{pCampo}, new String[]{pValor});
    }

    public boolean isCadastrado(String pTabela, String[] pCampos, String[] pValores) throws Exception {
        StringBuilder sbCondicao = new StringBuilder();
        for (int i = 0; i < pCampos.length; i++) {
            sbCondicao.append(String.format("%1s = ?", pCampos[i]));
            if (i < pCampos.length - 1)
                sbCondicao.append(" AND ");
        }

        Cursor cursor = mSqLiteDatabase.query(pTabela, pCampos, sbCondicao.toString(), pValores, null, null, null, null);
        try {
            return (cursor != null && cursor.getCount() > 0);
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }
}