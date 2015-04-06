/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dojointercalacao;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
     * Executa o algoritmo da Árvore dos Vencedores
     * @param nomeParticoes array com os nomes dos arquivos que contêm as partições de entrada
     * @param nomeArquivoSaida nome do arquivo de saída resultante da execução do algoritmo
     */


public class Intercalacao {
    public static Cliente[] memoria;
    public DataInputStream in = null;
    public Cliente clienteGenerico ;
    
    public void executa(List<String> nomeParticoes, String nomeArquivoSaida) throws Exception {
        int qtdMemoria = nomeParticoes.size();
    
         memoria = new Cliente[qtdMemoria];
        
        //inicializar memoria com null
        for(int i=0; i < qtdMemoria ; i++){
            memoria[i]= null;
        }
    }
    
    //Preencher a memoria com registros que ainda permanecem nas particões
    public void preecheMemoria (Cliente[] memoria, List<String> nomeParticoes ) throws FileNotFoundException, IOException{
        List<Cliente> clientes = null;
        
        for(int i =0 ; i < memoria.length ; i++){
                in = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeParticoes.get(i))));
                memoria[i] = clienteGenerico.le(in);
                atualizaParticao(nomeParticoes, i);
            
        }
    }
    
    
    public void atualizaParticao (List<String> nomeParticoes, int chave){
        
        List<Cliente> clientes = client.leCliente (nomeParticoes);
        int j=0;
        
       for(int i =1 ; i<clientes.size(); i++){   
           clientes.set(j, clientes.get(i));
           j++;
       }
       
      clientes.set((clientes.size()-1), null);
        
       Arquivos.salva(nomeParticoes.get(chave), clientes);
    }
    
    //metodo para char o menor valor que está na memória e escreve ele no arquivo de saida 
    public void menorValor (Cliente[] memoria , String nomeArquivoSaida ){

       Cliente novoRegistro = arvoreVencedores(memoria);     
       Arquivos.salva(nomeArquivoSaida, novoRegistro);
        
       
    }
    
    Cliente arvoreVencedores (Cliente[] memoria){
        Cliente vencedor = null;   
        List<No> arvore = null;
        int nivelAtual = 0; //nivel da arvore de baixo para cima 

        
        for(int i =0 ; i < memoria.length ; i++){
            No novo = new No(null, null, memoria[i]);
            arvore.add(novo);
        }

        while (vencedor == null ){
           int cont=0;
           No novo;
           int i;
           
           if(nivelAtual != arvore.size()-1){
               for ( i = nivelAtual ; i <=  arvore.size() ; i+=2)  {
               
                    if (i == arvore.size()-1)
                        novo = new No(arvore.get(i-1), arvore.get(i), arvore.get(i).getValor());
                    else{
                        if (arvore.get(i).getValor().codCliente < arvore.get(i+1).getValor().codCliente)
                         novo = new No(arvore.get(i), arvore.get(i+1), arvore.get(i).getValor());
                        else
                         novo =  new No(arvore.get(i), arvore.get(i+1), arvore.get(i+1).getValor());
                    }
                    arvore.add(novo);
                    cont++;
                }
           
                if ( cont ==  memoria.length/2 || cont == memoria.length+1)
                    nivelAtual+=memoria.length;
                else 
                    nivelAtual += cont;
           }
           else 
               vencedor = (arvore.get(arvore.size()-1)).getValor(); 
        }//fim do while                
        
        
        return vencedor;
    }
    

}
