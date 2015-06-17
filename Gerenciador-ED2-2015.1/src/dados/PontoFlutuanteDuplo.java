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
public class PontoFlutuanteDuplo implements Tipo {
    
    public double pontoFlutuanteDuplo;
    
    public PontoFlutuanteDuplo (double pontoFlutuanteDuplo){
        this.pontoFlutuanteDuplo = pontoFlutuanteDuplo;
    }
    
     @Override
    public String toString(){ 
        return pontoFlutuanteDuplo + "";
    }
    
}
