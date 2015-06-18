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
public class Inteiro implements Tipo {
    
    public int inteiro ;
    
    public Inteiro(int inteiro){
        this.inteiro = inteiro;
    }
    

    Inteiro() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString(){ 
        return Integer.toString(inteiro);
    }
    
}
