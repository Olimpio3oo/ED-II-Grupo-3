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
public class PontoFlutuante implements Tipo{
    
    public float pontoFlutuante;
    
    public PontoFlutuante(float pontoFlutuante){
        this.pontoFlutuante = pontoFlutuante;
    }
    
    @Override
    public String toString(){ 
        return pontoFlutuante + "";
    }
    
    
}
