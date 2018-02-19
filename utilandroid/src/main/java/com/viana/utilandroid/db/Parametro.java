package com.viana.utilandroid.db;

/**
 * Created by joao.viana on 19/02/2018.
 */

public class Parametro {
    private String Campo;
    private String Valor;
    private Operador Operador;

    public Parametro(String pCampo, String pValor) {
        this.Campo = pCampo;
        this.Valor = pValor;
        this.Operador = com.viana.utilandroid.db.Operador.Igual;
    }

    public Parametro(String pCampo, String pValor, com.viana.utilandroid.db.Operador pOperador) {
        this.Campo = pCampo;
        this.Valor = pValor;
        this.Operador = pOperador;
    }

    public String getCondicao() {
        String strFormato = "";
        String CampoTabela = this.Campo;

        if ((Operador == com.viana.utilandroid.db.Operador.Igual))
            strFormato = CampoTabela + " = ? ";
        else if (Operador == com.viana.utilandroid.db.Operador.Maior)
            strFormato = CampoTabela + " > ? ";
        else if (Operador == com.viana.utilandroid.db.Operador.Menor)
            strFormato = CampoTabela + " < ? ";
        else if (Operador == com.viana.utilandroid.db.Operador.MaiorIgual)
            strFormato = CampoTabela + " >= ? ";
        else if (Operador == com.viana.utilandroid.db.Operador.MenorIgual)
            strFormato = CampoTabela + " <= ? ";
        else if (Operador == com.viana.utilandroid.db.Operador.Diferente)
            strFormato = CampoTabela + " <> ? ";
        else if (Operador == com.viana.utilandroid.db.Operador.Inicie)
            strFormato = CampoTabela + " LIKE ? || '%'";
        else if (Operador == com.viana.utilandroid.db.Operador.Termine)
            strFormato = CampoTabela + " LIKE '%' || ? ";
        else if (Operador == com.viana.utilandroid.db.Operador.Contenha)
            strFormato = CampoTabela + " LIKE '%' || ? || '%'";
        else if (Operador == com.viana.utilandroid.db.Operador.Nulo)
            strFormato = CampoTabela + " IS NULL";
        else if (Operador == com.viana.utilandroid.db.Operador.NaoNulo)
            strFormato = CampoTabela + " IS NOT NULL";
        else if (Operador == com.viana.utilandroid.db.Operador.Vazio)
            strFormato = "IFNULL(" + CampoTabela + ", '') = ''";
        else if (Operador == com.viana.utilandroid.db.Operador.DiferenteVazio)
            strFormato = "IFNULL(" + CampoTabela + ", '') <> ''";

        return strFormato;
    }

    public String getValor() {
        return this.Valor;
    }
}
