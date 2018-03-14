[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](LICENSE)

# UtilAndroid

O Generic é uma solução ORM para a plataforma de desenvolvimento android com SQLite.
Ele gera automaticamente códigos SQL para carregar e guardar os objetos.

# Usage

Basta você criar sua classe com os atributos conforme os campos da tabela do banco de dados SQLite!
Exemplo:

``` java
public class Cnae {
    private int id;
    private String codigo;
    private String ramoAtividade;
    private int mcc;
    private boolean situacao;

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
sb.append("SELECT [id] ");
sb.append(",[ramoAtividade] ");
sb.append(",[mcc] ");
sb.append(",[situacao] ");
sb.append("FROM [Cnae] ");
sb.append("WHERE 1=1 ");
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
Para Salvar as informações no banco SQLite basta utilizar o método toSave conforme exemplo abaixo:
``` java
try{
    Cnae item = new Cnae();
    item.setId(1);
    item.setCodigo("0111301");
    item.setRamoAtividade("Cooperativas Agrícolas");
    item.setMcc(763);
    item.setSituacao(true);
    
    //Sua Classe do SQLite
    SQLiteDatabase db = new SQLiteDatabase();
    
    Generic generic = new Generic(db);
    generic.toSave(item);
} catch (Exception ex){
    //TODO Exception
}
```
Você deve incluír Annotation Key para sinalizar a Primary Key. 
``` java
...
import com.viana.androidutil.db.Key;

public class Cnae {
    @Key
    private int id;
    ...
}
```
O método toSave verifica se o registro já existe na tabela para inserir ou atualizar as informações.

# Gradle
Incluir a biblioteca no arquivo build.gradle do aplicativo
```
dependencies {
    ...
    implementation 'com.viana:androidutil:1.0.1'
}
```
Se necessário basta incluir a referência do repositório no arquivo build.gradle do projeto
```
repositories {
    ...
    maven { url  "https://dl.bintray.com/joaovitorviana/Viana" }
}
```
# Release Notes

Click the link [Release Notes](https://github.com/JoaoVitorViana/UtilAndroid/blob/master/ReleaseNotes.md)

 # License
Copyright 2018 Viana

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
