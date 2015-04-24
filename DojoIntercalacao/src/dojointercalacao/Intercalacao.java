/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dojointercalacao;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

/**
     * Executa o algoritmo Intercalação Ótima
     * @param nomeParticoes array com os nomes dos arquivos que contêm as partições de entrada
     * @param nomeArquivoSaida nome do arquivo de saída resultante da execução do algoritmo
     */
public class Intercalacao {
    static final int N = 4; /* Restrição de numero de arquivos abertos no sistema */
    public void executa(List<String> nomeParticoes, String nomeArquivoSaida) throws Exception {
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(nomeArquivoSaida)));
        DataInputStream in = null;
        DataOutputStream aux = null;
        DataInputStream[] ins = null;
        boolean isEOF = false;
        int cont = 0;
        Cliente[] cs = new Cliente[N-1];
        try{
            while(nomeParticoes.size()>1){
                ins = new DataInputStream[N-1];
                for(DataInputStream ent : ins){
                    ent = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeParticoes.get(0))));
                    nomeParticoes.remove(0);
                }
                do {
                    for(int i=0; i<N-1; i++) {
                        cs[i] = Cliente.le(ins[i]);
                        if(cs[i].codCliente==Integer.MAX_VALUE) isEOF = true;
                    }
                    ordena(cs, 0 , cs.length-1);
                    aux = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("aux"+ cont +".dat")));
                    nomeParticoes.add("aux"+cont+".dat");
                    cont++;
                    for( Cliente c : cs ){
                        c.salva(aux);
                    }
                    Cliente m = new Cliente(Integer.MAX_VALUE,"","");
                    m.salva(aux);
                    aux.close();
                }while(!isEOF);
            }
            if(nomeParticoes.size()==1){
                in = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeParticoes.get(0))));
                Cliente cliente;
                do {
                  cliente = Cliente.le(in);
                  cliente.salva(out);
                } while(cliente.codCliente != Integer.MAX_VALUE);
            }
        } finally {
            if(aux!=null) aux.close();
            if(out!=null) out.close();
            if(in!=null){
                for(DataInputStream ent : ins){
                    if(ent != null) ent.close();
                }
            }
            
        }
    }
    private void ordena(Cliente[] arr, int low, int high){
        if (arr == null || arr.length == 0) return;
        if (low >= high) return;
        int middle = low + (high - low) / 2;
        int pivot = arr[middle].codCliente;
        int i = low, j = high;
        while (i <= j) {
                while (arr[i].codCliente < pivot) i++;
                while (arr[j].codCliente > pivot) j--;
                if (i <= j) {
                    Cliente temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    i++;
                    j--;
                }
        }
        if (low < j) ordena(arr, low, j);
        if (high > i) ordena(arr, i, high);
    }
}
