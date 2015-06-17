/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados;

/**
 *
 * @author Beatriz
 */
public class Decisao implements Tipo {
    
    public boolean decisao;
    
    public Decisao(boolean decisao){
        this.decisao = decisao;
    }
    
    @Override
    public String toString(){ 
        if (decisao)
            return "TRUE";
        else
            return "FALSE";
    }
    
    
}
