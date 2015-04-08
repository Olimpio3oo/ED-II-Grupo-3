/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;
import java.lang.String;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Romulo Mourão
 */
public class Tabela {

    private String nome;
    private java.util.Map<String, String> atributos;

    public Tabela(String nome, LinkedHashMap<String, String> lista) {
        this.nome = nome;
        this.atributos = lista;

    }

    public Tabela() {
        
        
    }

    public void imprimeAtributos(Tabela tab) {
        String conjunto="";

        for (Map.Entry<String, String> entry : tab.atributos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            
            conjunto += "ATRIBUTO: "+key+" | TIPO: "+value+"\n";
            
        }
 
        System.out.println("_________________"+tab.nome.toUpperCase()+"____________________");
       
        System.out.print(conjunto);
        System.out.print("__________________________________________________________________________________");
    }
    
   public String get(Tabela tab){
       
       String conjunto= tab.nome.toUpperCase()+" = ";

        for (Map.Entry<String, String> entry : tab.atributos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            
            conjunto +=  key.toLowerCase()+" : "+value.toLowerCase()+" ; ";
            
        }
        
       return conjunto;
   }

}
