package com.viana.androidutil.mobile;

import java.util.ArrayList;

/**
 * Created by joao.viana on 21/02/2018.
 */

public class Util {
    public static <T> T firstOrDefault(ArrayList<T> pLista) {
        return (pLista != null && pLista.size() > 0) ? pLista.get(0) : null;
    }
}