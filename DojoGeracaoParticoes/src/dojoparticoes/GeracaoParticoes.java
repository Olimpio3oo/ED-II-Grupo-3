package dojoparticoes;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GeracaoParticoes {
    /**
     * Executa o algoritmo de geração de partições por Classificação Interna
     * @param nomeArquivoEntrada arquivo de entrada          
     * @param nomeArquivoSaida array list contendo os nomes dos arquivos de saída a serem gerados
     * @param M tamanho do array em memória para manipulação dos registros
     */
    public void executaClassificacaoInterna(String nomeArquivoEntrada, List<String> nomeArquivoSaida, int M) throws Exception {
        
            //TODO: Inserir aqui o código do algoritmo de geração de partições

    }
    
    
    /**
     * Executa o algoritmo de geração de partições por Seleção com Substituição 
     * @param nomeArquivoEntrada arquivo de entrada          
     * @param nomeArquivoSaida array list contendo os nomes dos arquivos de saída a serem gerados
     * @param M tamanho do array em memória para manipulação dos registros
     */
    public void executaSelecaoComSubstituicao(String nomeArquivoEntrada, List<String> nomeArquivoSaida, int M) throws Exception {
        //Input para ler o arquivo de entrada e o Output para gravar as partição de saída
        DataInputStream in = null;
        DataOutputStream out = null;
        //variavel para controlar a partição que estaremos.
        int particaoAtual = 0;
        //Inicia um vetor de clientes de tamanho M e um vetor de congelados com o mesmo tamanho
        Cliente[] cliente = new Cliente[M];
        boolean[] congelado = new boolean[M];
        boolean fimDeArquivo = false;
        int posMenorChave;
        try {
            //abre o arquivo de entrada para leitura
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeArquivoEntrada)));
            int indice = 0;
            
            try {
                do {
                    // preenche o vetor com as informações dos M primeiros clientes do arquivo de entrada
                    cliente[indice] = Cliente.le(in);
                    indice++;
                   
                } while(indice < M);
            } catch(EOFException e) {  // fim de arquivo
                   
              }
            // se posMax = 0,  o arquivo está vazio, então salvamos um arquivo de saida vazio
            if(indice == 0) {
                out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(nomeArquivoSaida.get(0))));
                out.close();
                fimDeArquivo = true;
            }
            
           
            
            while(!fimDeArquivo) {
                
                //Seta FALSE para todas as posições do vetor CONGELADO
                descongelar(congelado);
                //Abre para gravação a partição em questão 
                out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(nomeArquivoSaida.get(particaoAtual))));
                
                //recebe o indice da menor posição do vetor no estado em questão 
                posMenorChave = buscarMenor(cliente, congelado, indice);
                while(posMenorChave != -1) {
                    //guarda em chaveAnterior o codigo do ultimo cliente salvo na partição em questão
                    int chaveAnterior = cliente[posMenorChave].codCliente;
                    //salva na partição o cliente na posição posMenorChave
                    cliente[posMenorChave].salva(out);
                    try {
                        //Lê o proximo cliente do arquivo de entrada(in) e coloca na posição (posMenorChave). 
                        //Esta posição é a que acabamos de salvar na partição de saída(out) no passo anterior
                        cliente[posMenorChave] = Cliente.le(in);  
                        //Caso não consigo ler um proximo valor cairá no CATCH
                    } catch(EOFException e) {
                        //a posição posMenorChave no vetor CONGELADO é setada como TRUE
                        //fimDeArquivo recebe TRUE
                        congelado[posMenorChave] = true;
                        fimDeArquivo = true;
                    }
                    //Se a posMenorChave em CONGELADO estiver descongelado e o codigo do ultimo cliente adicionado na partição for maior que o codigo do cliente na posição posMenorChave
                    //Então essa posição no vetor CONGELADO fica TRUE, ou seja, está congelado 
                    if((!congelado[posMenorChave]) && (chaveAnterior > cliente[posMenorChave].codCliente)) {
                        congelado[posMenorChave] = true;

                    }
                    //posMenorChave receberá o indice do "novo" menor no vetor CLIENTE
                    posMenorChave = buscarMenor(cliente, congelado, indice);
                }
                //fecha a partição atual
                out.close();
                //incrementa particaoAtual para que na proxima vez seja aberta a proxima partição pra gravação
                particaoAtual++;
            }
            
        } catch(IOException e) {
            
        } finally {
            if(in != null) {
                in.close();
            }
            if(out != null) {
                out.close();
            }
        }   
        

    }
    
    /**
     * Executa o algoritmo de geração de partições por Seleção Natural 
     * @param nomeArquivoEntrada arquivo de entrada          
     * @param nomeArquivoSaida array list contendo os nomes dos arquivos de saída a serem gerados
     * @param M tamanho do array em memória para manipulação dos registros
     * @param n tamanho do reservatório
     */
    public void executaSelecaoNatural(String nomeArquivoEntrada, List<String> nomeArquivoSaida, int M, int n) throws Exception {
        
            //TODO: Inserir aqui o código do algoritmo de geração de partições

    }
    
      private int buscarMenor(Cliente[] cliente, boolean[] congelados, int indice) {
        int menor = Integer.MAX_VALUE;
        int posicao = -1;
        for(int i = 0; i < indice; i++) {
            if((!congelados[i]) && (menor > cliente[i].codCliente)) {
                menor = cliente[i].codCliente;
                posicao = i;                
            }
        }
        return posicao;
    }

    private void descongelar(boolean[] congelados) {
        //percorre o vetor ate a posição INDICE que 
        for(int i = 0; i < congelados.length; i++) {
            congelados[i] = false;
        }
    }
    
}