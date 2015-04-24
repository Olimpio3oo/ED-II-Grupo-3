/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dobra;

/**
 *
 * @author Beatriz
 */
public class Dobra {
    
    
     /*
     * @param codCliente um numero inteiro de 6 digitos
     */
    public static int metodoDobra (int codCliente){ //retorna o endereço de memoria onde sera armazenado o cliente  
        int endereco;
        int div = 100000;
        
        int[] vet = new int[6]; //array para guardar os numero do codigo
        
        //"quebra" o numero inteiro e coloca ele num array
        for (int i =0 ; i < 6 ; i++){
            vet[i] = codCliente/div;
            codCliente -= vet[i]*div; 
            div = div/10;
        }
        
        int x = 0;
        int y = 1;
            
        try{
            while(true){
                int a = vet[x] + vet[x+3];
                int b = vet[y] + vet[y+1];

                if(a > 9){
                    a = a%10;
                }

                if (b > 9){
                    b = b%10;
                }

                vet[x+3] = a;
                vet[y+1] = b;

                x+=2;
                y+=2; 
            }
            
        }catch (Exception e) {
            
        }
        finally {
               endereco = (vet[4]*10)+vet[5];
        }
        
        return endereco;   
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("Endereço onde o código deve ser guaradado é: " +  metodoDobra(813459));
        
    }
    
}
