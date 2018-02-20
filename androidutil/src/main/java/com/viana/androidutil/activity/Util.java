package com.viana.androidutil.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Joao Viana on 17/02/2018.
 */

public class Util {
    public static void openNewActivity(Context pContext, Class<?> pClass, boolean pFechar) {
        Intent intent = new Intent(pContext, pClass);
        ((Activity) pContext).startActivity(intent);
        if (pFechar)
            ((Activity) pContext).finish();
    }
}