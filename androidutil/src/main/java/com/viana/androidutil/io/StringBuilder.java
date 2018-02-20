package com.viana.androidutil.io;

/**
 * Created by Joao Viana on 17/02/2018.
 */

public class StringBuilder {
    private java.lang.StringBuilder sb;

    public StringBuilder() {
        this.sb = new java.lang.StringBuilder();
    }

    public void append(Object pObject) {
        this.sb.append(pObject);
    }

    public void appendLine(Object pObject) {
        this.sb.append(pObject).append("\n");
    }

    public String toString() {
        return this.sb.toString();
    }
}