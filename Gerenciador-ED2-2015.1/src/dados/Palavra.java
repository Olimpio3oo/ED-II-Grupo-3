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
public class Palavra implements Tipo {
    
    public String palavra;
    
    public Palavra (String palavra){
        this.palavra = palavra;
    }
    
    @Override
    public String toString(){ 
        return this.palavra;
    }
    
       
}
