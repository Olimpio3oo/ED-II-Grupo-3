/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Beatriz
 */
public class Data implements Tipo {
    
    public Date data ;
    
    public Data (Date data){
        this.data = data;
    }
    
    @Override
    public String toString(){ 
        
        SimpleDateFormat mda = new SimpleDateFormat("MM/dd/yyyy");
        
        return mda.format(data) + "";
    }

}
