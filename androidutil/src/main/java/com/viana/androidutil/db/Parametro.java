package com.viana.androidutil.db;

/**
 * Created by Joao Viana on 17/02/2018.
 */

public class Parametro {
    private String Campo;
    private String Valor;
    private Operador Operador;

    public Parametro(String pCampo, String pValor) {
        this.Campo = pCampo;
        this.Valor = pValor;
        this.Operador = com.viana.androidutil.db.Operador.Igual;
    }

    public Parametro(String pCampo, String pValor, com.viana.androidutil.db.Operador pOperador) {
        this.Campo = pCampo;
        this.Valor = pValor;
        this.Operador = pOperador;
    }

    public String getCondicao() {
        String strFormato = "";
        String CampoTabela = this.Campo;

        if ((Operador == com.viana.androidutil.db.Operador.Igual))
            strFormato = CampoTabela + " = ? ";
        else if (Operador == com.viana.androidutil.db.Operador.Maior)
            strFormato = CampoTabela + " > ? ";
        else if (Operador == com.viana.androidutil.db.Operador.Menor)
            strFormato = CampoTabela + " < ? ";
        else if (Operador == com.viana.androidutil.db.Operador.MaiorIgual)
            strFormato = CampoTabela + " >= ? ";
        else if (Operador == com.viana.androidutil.db.Operador.MenorIgual)
            strFormato = CampoTabela + " <= ? ";
        else if (Operador == com.viana.androidutil.db.Operador.Diferente)
            strFormato = CampoTabela + " <> ? ";
        else if (Operador == com.viana.androidutil.db.Operador.Inicie)
            strFormato = CampoTabela + " LIKE ? || '%'";
        else if (Operador == com.viana.androidutil.db.Operador.Termine)
            strFormato = CampoTabela + " LIKE '%' || ? ";
        else if (Operador == com.viana.androidutil.db.Operador.Contenha)
            strFormato = CampoTabela + " LIKE '%' || ? || '%'";
        else if (Operador == com.viana.androidutil.db.Operador.Nulo)
            strFormato = CampoTabela + " IS NULL";
        else if (Operador == com.viana.androidutil.db.Operador.NaoNulo)
            strFormato = CampoTabela + " IS NOT NULL";
        else if (Operador == com.viana.androidutil.db.Operador.Vazio)
            strFormato = "IFNULL(" + CampoTabela + ", '') = ''";
        else if (Operador == com.viana.androidutil.db.Operador.DiferenteVazio)
            strFormato = "IFNULL(" + CampoTabela + ", '') <> ''";

        return strFormato;
    }

    public String getValor() {
        return this.Valor;
    }
}