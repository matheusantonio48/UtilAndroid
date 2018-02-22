[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](LICENSE)

# UtilAndroid

O Generic é uma solução ORM para a plataforma de desenvolvimento android com SQLite.
Ele gera automaticamente códigos SQL para carregar e guardar os objetos.

# Usage

Basta você criar sua classe com os atributos conforme os campos da tabela do banco de dados SQLite!
Exemplo:

``` java
public class Cnae {
    @Key
    private int id;
    private String codigo;
    private String ramoAtividade;
    private String mcc;
    private bool situacao;

    //TODO getters e setters
}
```
Para retornar a lista de objetos basta utilizar o método returnList conforme exemplo abaixo:
* Informar a Classe
* Informar a lista de parâmetros para gerar o filtro da consulta

``` java

//Sua Classe do SQLite
SQLiteDatabase db = new SQLiteDatabase();

ArrayList<Cnae> list = null;
try{
    Generic generic = new Generic(db);
    ArrayList<Parametro> pmts = new ArrayList<>();
    pmts.add(new Parametro("id", "2", Operador.Igual));
    list = generic.returnList(Cnae.class, pmts);
} catch (Exception ex){
    //TODO Exception
}

```
Ou você pode informar a query da consulta conforme exemplo abaixo:
``` java
int id = 2;

StringBuilder sb = new StringBuilder();
sb.append("SELECT [id]");
sb.append(",[ramoAtividade]");
sb.append(",[mcc]");
sb.append(",[situacao]");
sb.append("FROM [Cnae]");
sb.append("AND [id] = ?");

//Sua Classe do SQLite
SQLiteDatabase db = new SQLiteDatabase();

ArrayList<Cnae> list = null;
try{
    Generic generic = new Generic(db);
   list = generic.executeQuery(Cnae.class, sb.toString(), new String[]{String.valueOf(id)});
} catch (Exception ex){
    //TODO Exception
}

```
# Gradle
Incluir a biblioteca no arquivo build.gradle do aplicativo
```
dependencies {
    ...
    implementation 'com.viana:androidutil:0.0.7'
}
```
Se necessário basta incluir a referência do repositório no arquivo build.gradle do projeto
```
repositories {
    ...
    maven { url  "https://dl.bintray.com/joaovitorviana/Viana" }
}
```
# Changelog
* **0.0.7**
    * Included method toSave
* **0.0.6**
    * Updated minSdkVersion 17
* **0.0.5**
    * Initial release
