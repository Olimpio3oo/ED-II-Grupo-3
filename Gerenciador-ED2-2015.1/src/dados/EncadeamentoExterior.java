/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dados;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;

public class EncadeamentoExterior {

    /**
     * Cria uma tabela hash vazia de tamanho tam, e salva no arquivo
     * nomeArquivoHash Compartimento que não tem lista encadeada associada deve
     * ter valor igual a -1
     *
     * @param nomeArquivoHash nome do arquivo hash a ser criado
     * @param tam tamanho da tabela hash a ser criada
     */
    public void criaHash(RandomAccessFile tabelaHash) {
        //TODO: criar a tabela hash
        //RandomAccessFile tabelaHash;

        try {
            //tabelaHash = new RandomAccessFile(new File(nomeArquivoHash), "rw");

            for (int i = 0; i < 7; i++) {
                new CompartimentoHash(-1).salva(tabelaHash);

            }
        } catch (Exception e) {

        }
    }

    /**
     * Executa busca em Arquivos por Encadeamento Exterior (Hash) Assumir que
     * ponteiro para próximo nó é igual a -1 quando não houver próximo nó
     *
     * @param chave: chave do cliente que está sendo buscado
     * @param nomeArquivoHash nome do arquivo que contém a tabela Hash
     * @param nomeArquivoDados nome do arquivo onde os dados estão armazenados
     * @return o endereco onde o cliente foi encontrado, ou -1 se não for
     * encontrado
     */
    public int busca(int chave, String nomeArquivoHash, String nomeArquivoDados, String nomeTabela) throws Exception {
        //TODO: Inserir aqui o código do algoritmo   

        RandomAccessFile tabelaHash = new RandomAccessFile(new File(nomeArquivoHash), "rw");
        RandomAccessFile arquivo = new RandomAccessFile(new File(nomeArquivoDados), "rw");
        DataInputStream atributos = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeTabela.toLowerCase() + "_atributos.dat")));
        
        int tam = Registro.tamanhoReg(nomeTabela);
        try {

            int index = chave % 7;

            tabelaHash.seek(index * CompartimentoHash.tamanhoRegistro);

            CompartimentoHash compartimento = CompartimentoHash.le(tabelaHash);
            if (compartimento.prox == -1) {
                return -1;
            }
            Registro registro = Registro.le(atributos, arquivo, nomeTabela);
            arquivo.seek(compartimento.prox * tam);

            int anterior = -1;
            if (registro.chave == chave && registro.flag == registro.OCUPADO) {

                return compartimento.prox;
            } else {
                while (registro.prox != -1 && registro.chave != chave) {
                    anterior = registro.prox;
                    arquivo.seek(anterior * tam);
                    registro = Registro.le(atributos, arquivo, nomeTabela);

                }
                if (registro.chave == chave && registro.flag == registro.OCUPADO) {

                    return anterior;
                } else {
                    if (registro.flag == true) {
                        while (registro.prox != -1) {
                            anterior = registro.prox;
                            arquivo.seek(anterior * tam);
                            registro = Registro.le(atributos, arquivo, nomeTabela);
                            if (registro.chave == chave && registro.flag == registro.OCUPADO) {

                                return anterior;
                            }
                        }

                    }

                }
            }
        } finally {
            tabelaHash.close();
            arquivo.close();
        }

        return -1;
    }

    /**
     * Executa inserção em Arquivos por Encadeamento Exterior (Hash)
     *
     * @param chave: código do cliente a ser inserido
     * @param atr
     * @param nomeCli: nome do Cliente a ser inserido
     * @param nomeArquivoHash nome do arquivo que contém a tabela Hash
     * @param nomeArquivoDados nome do arquivo onde os dados estão armazenados
     * @param nomeTabela
     * @param numRegistros numero de registros que já existem gravados no
     * arquivo
     * @return endereço onde o cliente foi inserido, -1 se não conseguiu inserir
     * @throws java.lang.Exception
     */
    //public int insere(int chave, ArrayList<Tipo> atr, String nomeArquivoHash, String nomeArquivoDados, String nomeTabela, int numRegistros) throws Exception {
    public int insere( Registro registroInserir, String nomeArquivoHash, String nomeArquivoDados,ArrayList<String> tipos) throws Exception {
        //TODO: Inserir aqui o código do algoritmo de inserção
        
        //Declarações de variáveis 
        RandomAccessFile tabelaHash = new RandomAccessFile(new File(nomeArquivoHash), "rw"); //abrindo tabela hash
        RandomAccessFile arquivoRegistros = new RandomAccessFile(new File(nomeArquivoDados), "rw"); //abrindo o arquivo de registros
        int qtdRegistros = 0;
        int i = 1; //auxiliar para percorrer arraylist tipos
        int termina = 0 ;//auxiliar de parada 
        int atualAnterior ; // auxiliar para calcular o seek
        
        
        //Calcula a hashCode
        int hashCode = registroInserir.chave%7;
        
        //Colsultar na tabela se aquela posição do hashCode está livre
        tabelaHash.seek(hashCode * 4); //o tamanho do registro na tabela hash é sempre 4
        int posicaoHash = tabelaHash.readInt(); //lendo o registro 
        
        try{
                //consultar quantros registros já tem no arquivo
                while(true){
                    arquivoRegistros.readInt();
                    qtdRegistros++; //quantos registros já tem no arquivo 
                    arquivoRegistros.seek(qtdRegistros*registroInserir.tamanhoRegistro); //ja deixa o ponteiro na posição certa para inserção
                }
                
        }catch (Exception e){
                 
                  if(posicaoHash == -1){ //Caso 1 - posição está vazia, insere na posição o resgistro
                     
                     //escrevendo no arquivo o novo registro - chave, atributos , flag e prox
                     arquivoRegistros.writeInt(registroInserir.chave);

                     try{
                         //escrever os atributos
                         for (Tipo atributo : registroInserir.atributos){

                            if (tipos.get(i).equalsIgnoreCase("integer ")) { 
                                Inteiro inteiro = (Inteiro) registroInserir.atributos.get(i);
                                arquivoRegistros.writeInt(inteiro.inteiro);

                            }
                            if (tipos.get(i).equalsIgnoreCase("float   ")) {
                              PontoFlutuante ponto = (PontoFlutuante) registroInserir.atributos.get(i);
                              arquivoRegistros.writeFloat(ponto.pontoFlutuante);
                            }
                            if (tipos.get(i).equalsIgnoreCase("double  ")) {
                              PontoFlutuanteDuplo ponto = (PontoFlutuanteDuplo) registroInserir.atributos.get(i);
                              arquivoRegistros.writeDouble(ponto.pontoFlutuanteDuplo);
                            }
                            if (tipos.get(i).equalsIgnoreCase("char    ")) {
                               Palavra palavra = (Palavra) registroInserir.atributos.get(i);
                               arquivoRegistros.writeUTF(palavra.palavra);
                            }
                            if (tipos.get(i).equalsIgnoreCase("boolean ")) {
                                Decisao decisao = (Decisao) registroInserir.atributos.get(i);
                                arquivoRegistros.writeBoolean(decisao.decisao);
                            }
                            if (tipos.get(i).equalsIgnoreCase("date    ")) {
                                Data data = (Data) registroInserir.atributos.get(i);
                                arquivoRegistros.writeUTF(data.data.toString());
                            }
                            if (tipos.get(i).equalsIgnoreCase("string  ")) {
                                Palavra palavra = (Palavra) registroInserir.atributos.get(i);
                                arquivoRegistros.writeUTF(palavra.palavra);
                            }
                            i++;
                         }
                     }catch (Exception g ){
                        //escrever a flag 
                         arquivoRegistros.writeBoolean(registroInserir.flag);

                         //escrever proximo
                         arquivoRegistros.writeLong(registroInserir.prox);


                         tabelaHash.seek(hashCode * 4);
                         tabelaHash.writeInt(qtdRegistros); //escrevendo na tabela e ocupando a posição 
                     }

                  }else{ //Caso 2 - A posição na tabela hash está ocupada 
                        arquivoRegistros.seek(posicaoHash*registroInserir.tamanhoRegistro); //vou para a posição que já esta ocupada no arquivo

                        //lendo o registros atual que está ocupando a posição
                        Registro atual = new Registro();          
                        atual.chave = arquivoRegistros.readInt();
                        i=1; 
                        
                        try{
                            //percorrer array de tipos e ler
                        for (Tipo atributo : registroInserir.atributos){ 
                                if (tipos.get(i).equalsIgnoreCase("integer ")) { 
                                    Inteiro inteiro = new Inteiro (arquivoRegistros.readInt());
                                    atual.atributos.add(inteiro);
                                }
                                if (tipos.get(i).equalsIgnoreCase("float   ")) {
                                  PontoFlutuante ponto = new PontoFlutuante (arquivoRegistros.readFloat());
                                     atual.atributos.add(ponto);
                                }
                                if (tipos.get(i).equalsIgnoreCase("double  ")) {
                                  PontoFlutuanteDuplo ponto = new PontoFlutuanteDuplo (arquivoRegistros.readDouble());
                                  atual.atributos.add(ponto);
                                }
                                if (tipos.get(i).equalsIgnoreCase("char    ")) {
                                   Palavra palavra = new Palavra (arquivoRegistros.readUTF());
                                   atual.atributos.add(palavra);
                                }
                                if (tipos.get(i).equalsIgnoreCase("boolean ")) {
                                    Decisao decisao = new Decisao (arquivoRegistros.readBoolean());
                                    atual.atributos.add(decisao);
                                }
                                if (tipos.get(i).equalsIgnoreCase("date    ")) {
                                    String dataAux = arquivoRegistros.readUTF(); //leio a data em string do arquivo
                                    Date data = new Date (dataAux); //crio um objeto dat com a data que estava no arquivo
                                    Data dataFinal = new Data (data); // crio um objeto Data( Tipo ) com os dados pra pode adicionar nos atributos 
                                    atual.atributos.add(dataFinal); //adiciona em atributos                 
                                }
                                if (tipos.get(i).equalsIgnoreCase("string  ")) {
                                    Palavra palavra = new Palavra (arquivoRegistros.readUTF());
                                    atual.atributos.add(palavra);
                                }
                                i++;
                            }
                        }catch(Exception t) {
                            atual.flag = arquivoRegistros.readBoolean();
                            atual.prox = arquivoRegistros.readInt();
                        }

                        if(atual.flag){ //estado da flag

                            //se a flag tiver livre - essa situação se aplica se é o mesmo registro que a hash já aponta 

                             if (atual.chave == registroInserir.chave){ // se o registros que estava anteriormente é igual ao novo
                                 //muda só a flag
                                 atual.flag = false;

                                 arquivoRegistros.seek(posicaoHash*(registroInserir.tamanhoRegistro-6)); // posiciona para começo do boolean

                                 arquivoRegistros.writeBoolean(atual.flag);

                             }

                             if(atual.chave != registroInserir.chave){ // se o registros que estava anteriormente é diferente do novo

                                 arquivoRegistros.seek(posicaoHash*(registroInserir.tamanhoRegistro)); // posiciona para começo da linha 

                                 //escrevendo no arquivo o novo registro - chave, atributos , flag e prox
                                 arquivoRegistros.writeInt(registroInserir.chave);


                                 //escrever os atributos
                                 i=1; // pq 1 ? pq eu quero olhar o primeiro atributo com segndo tipo ( o primeiro tipo é o inteiro da chave ) 
                                 for (Tipo atributo : registroInserir.atributos){

                                    if (tipos.get(i).equalsIgnoreCase("integer ")) { 
                                        Inteiro inteiro = (Inteiro) registroInserir.atributos.get(i);
                                        arquivoRegistros.writeInt(inteiro.inteiro);

                                    }
                                    if (tipos.get(i).equalsIgnoreCase("float   ")) {
                                      PontoFlutuante ponto = (PontoFlutuante) registroInserir.atributos.get(i);
                                      arquivoRegistros.writeFloat(ponto.pontoFlutuante);
                                    }
                                    if (tipos.get(i).equalsIgnoreCase("double  ")) {
                                      PontoFlutuanteDuplo ponto = (PontoFlutuanteDuplo) registroInserir.atributos.get(i);
                                      arquivoRegistros.writeDouble(ponto.pontoFlutuanteDuplo);
                                    }
                                    if (tipos.get(i).equalsIgnoreCase("char    ")) {
                                       Palavra palavra = (Palavra) registroInserir.atributos.get(i);
                                       arquivoRegistros.writeUTF(palavra.palavra);
                                    }
                                    if (tipos.get(i).equalsIgnoreCase("boolean ")) {
                                        Decisao decisao = (Decisao) registroInserir.atributos.get(i);
                                        arquivoRegistros.writeBoolean(decisao.decisao);
                                    }
                                    if (tipos.get(i).equalsIgnoreCase("date    ")) {
                                        Data data = (Data) registroInserir.atributos.get(i);
                                        arquivoRegistros.writeUTF(data.data.toString());
                                    }
                                    if (tipos.get(i).equalsIgnoreCase("string  ")) {
                                        Palavra palavra = (Palavra) registroInserir.atributos.get(i);
                                        arquivoRegistros.writeUTF(palavra.palavra);
                                    }
                                    i++;
                                 }
                                 //escrever a flag 
                                 arquivoRegistros.writeBoolean(registroInserir.flag);

                                 //escrever proximo
                                 //arquivoRegistros.writeLong(registroInserir.prox);
                             }

                      }else{ // a flag já está ocupada -

                                 if(atual.prox == -1){
                                    //atualizo registro atual e faço encadeamento
                                    atual.prox = qtdRegistros;

                                    arquivoRegistros.seek(posicaoHash*(registroInserir.tamanhoRegistro-4)); // posiciona para começo do proximo

                                    arquivoRegistros.writeInt(atual.prox); //atualiza proximo do atual

                                    //insiro o novo registro na linha livre

                                    arquivoRegistros.seek(qtdRegistros*registroInserir.tamanhoRegistro);

                                    //escrevendo no arquivo o novo registro - chave, atributos , flag e prox
                                     arquivoRegistros.writeInt(registroInserir.chave);

                                     //escrever os atributos
                                     i=1; // pq 1 ? pq eu quero olhar o primeiro atributo com segndo tipo ( o primeiro tipo é o inteiro da chave ) 
                                     for (Tipo atributo : registroInserir.atributos){

                                        if (tipos.get(i).equalsIgnoreCase("integer ")) { 
                                            Inteiro inteiro = (Inteiro) registroInserir.atributos.get(i);
                                            arquivoRegistros.writeInt(inteiro.inteiro);

                                        }
                                        if (tipos.get(i).equalsIgnoreCase("float   ")) {
                                          PontoFlutuante ponto = (PontoFlutuante) registroInserir.atributos.get(i);
                                          arquivoRegistros.writeFloat(ponto.pontoFlutuante);
                                        }
                                        if (tipos.get(i).equalsIgnoreCase("double  ")) {
                                          PontoFlutuanteDuplo ponto = (PontoFlutuanteDuplo) registroInserir.atributos.get(i);
                                          arquivoRegistros.writeDouble(ponto.pontoFlutuanteDuplo);
                                        }
                                        if (tipos.get(i).equalsIgnoreCase("char    ")) {
                                           Palavra palavra = (Palavra) registroInserir.atributos.get(i);
                                           arquivoRegistros.writeUTF(palavra.palavra);
                                        }
                                        if (tipos.get(i).equalsIgnoreCase("boolean ")) {
                                            Decisao decisao = (Decisao) registroInserir.atributos.get(i);
                                            arquivoRegistros.writeBoolean(decisao.decisao);
                                        }
                                        if (tipos.get(i).equalsIgnoreCase("date    ")) {
                                            Data data = (Data) registroInserir.atributos.get(i);
                                            arquivoRegistros.writeUTF(data.data.toString());
                                        }
                                        if (tipos.get(i).equalsIgnoreCase("string  ")) {
                                            Palavra palavra = (Palavra) registroInserir.atributos.get(i);
                                            arquivoRegistros.writeUTF(palavra.palavra);
                                        }
                                        i++;
                                     }
                                     //escrever a flag 
                                     arquivoRegistros.writeBoolean(registroInserir.flag);

                                     //escrever proximo
                                     arquivoRegistros.writeLong(registroInserir.prox);

                                 }else{ // vou começar a percorrer o encadeamento 
                                     while(atual.prox != -1 || termina != 1){

                                        arquivoRegistros.seek(atual.prox*registroInserir.tamanhoRegistro); //vou para a posição que já esta ocupada no arquivo

                                        //lendo o registros atual que está ocupando a posição         
                                        atual.chave = arquivoRegistros.readInt();
                                        i=1; 

                                        //percorrer array de tipos e ler
                                        for (Tipo atributo : registroInserir.atributos){ 
                                                if (tipos.get(i).equalsIgnoreCase("integer ")) { 
                                                    Inteiro inteiro = new Inteiro (arquivoRegistros.readInt());
                                                    atual.atributos.add(inteiro);
                                                }
                                                if (tipos.get(i).equalsIgnoreCase("float   ")) {
                                                  PontoFlutuante ponto = new PontoFlutuante (arquivoRegistros.readFloat());
                                                     atual.atributos.add(ponto);
                                                }
                                                if (tipos.get(i).equalsIgnoreCase("double  ")) {
                                                  PontoFlutuanteDuplo ponto = new PontoFlutuanteDuplo (arquivoRegistros.readDouble());
                                                  atual.atributos.add(ponto);
                                                }
                                                if (tipos.get(i).equalsIgnoreCase("char    ")) {
                                                   Palavra palavra = new Palavra (arquivoRegistros.readUTF());
                                                   atual.atributos.add(palavra);
                                                }
                                                if (tipos.get(i).equalsIgnoreCase("boolean ")) {
                                                    Decisao decisao = new Decisao (arquivoRegistros.readBoolean());
                                                    atual.atributos.add(decisao);
                                                }
                                                if (tipos.get(i).equalsIgnoreCase("date    ")) {
                                                    String dataAux = arquivoRegistros.readUTF(); //leio a data em string do arquivo
                                                    Date data = new Date (dataAux); //crio um objeto dat com a data que estava no arquivo
                                                    Data dataFinal = new Data (data); // crio um objeto Data( Tipo ) com os dados pra pode adicionar nos atributos 
                                                    atual.atributos.add(dataFinal); //adiciona em atributos                 
                                                }
                                                if (tipos.get(i).equalsIgnoreCase("string  ")) {
                                                    Palavra palavra = new Palavra (arquivoRegistros.readUTF());
                                                    atual.atributos.add(palavra);
                                                }
                                                i++;
                                            }

                                            atual.flag = arquivoRegistros.readBoolean();

                                            atualAnterior = atual.prox; //salvar o ponteiro para o começo da linha atual;
                                            atual.prox = arquivoRegistros.readInt();

                                            if(atual.flag){ //estado da flag - escrevendo no meio 

                                                //se a flag tiver livre 

                                                 if (atual.chave == registroInserir.chave){ //se o registro é o mesmo do anterior que já estava ali
                                                     //muda só a flag
                                                     atual.flag = false;

                                                     arquivoRegistros.seek(atualAnterior*(registroInserir.tamanhoRegistro-6)); // posiciona para começo do boolean

                                                     arquivoRegistros.writeBoolean(atual.flag);

                                                     termina = 1; //sai do loop
                                                 }

                                                 if(atual.chave != registroInserir.chave){ // se o registros que estava anteriormente é diferente do novo

                                                         arquivoRegistros.seek(atualAnterior*(registroInserir.tamanhoRegistro)); // posiciona para começo da linha 

                                                         //escrevendo no arquivo o novo registro - chave, atributos , flag e prox
                                                         arquivoRegistros.writeInt(registroInserir.chave);


                                                         //escrever os atributos
                                                         for (Tipo atributo : registroInserir.atributos){

                                                            if (tipos.get(i).equalsIgnoreCase("integer ")) { 
                                                                Inteiro inteiro = (Inteiro) registroInserir.atributos.get(i);
                                                                arquivoRegistros.writeInt(inteiro.inteiro);

                                                            }
                                                            if (tipos.get(i).equalsIgnoreCase("float   ")) {
                                                              PontoFlutuante ponto = (PontoFlutuante) registroInserir.atributos.get(i);
                                                              arquivoRegistros.writeFloat(ponto.pontoFlutuante);
                                                            }
                                                            if (tipos.get(i).equalsIgnoreCase("double  ")) {
                                                              PontoFlutuanteDuplo ponto = (PontoFlutuanteDuplo) registroInserir.atributos.get(i);
                                                              arquivoRegistros.writeDouble(ponto.pontoFlutuanteDuplo);
                                                            }
                                                            if (tipos.get(i).equalsIgnoreCase("char    ")) {
                                                               Palavra palavra = (Palavra) registroInserir.atributos.get(i);
                                                               arquivoRegistros.writeUTF(palavra.palavra);
                                                            }
                                                            if (tipos.get(i).equalsIgnoreCase("boolean ")) {
                                                                Decisao decisao = (Decisao) registroInserir.atributos.get(i);
                                                                arquivoRegistros.writeBoolean(decisao.decisao);
                                                            }
                                                            if (tipos.get(i).equalsIgnoreCase("date    ")) {
                                                                Data data = (Data) registroInserir.atributos.get(i);
                                                                arquivoRegistros.writeUTF(data.data.toString());
                                                            }
                                                            if (tipos.get(i).equalsIgnoreCase("string  ")) {
                                                                Palavra palavra = (Palavra) registroInserir.atributos.get(i);
                                                                arquivoRegistros.writeUTF(palavra.palavra);
                                                            }
                                                            i++;
                                                         }
                                                         //escrever a flag 
                                                         arquivoRegistros.writeBoolean(registroInserir.flag);

                                                         //escrever proximo
                                                         //arquivoRegistros.writeLong(registroInserir.prox);

                                                         termina = 1; //saio do loop
                                                     }

                                             }else{

                                                 if(atual.prox == -1){
                                                        //atualizo registro atual e faço encadeamento
                                                        atual.prox = qtdRegistros;

                                                        arquivoRegistros.seek(atualAnterior*(registroInserir.tamanhoRegistro-4)); // posiciona para começo do boolean

                                                        arquivoRegistros.writeInt(atual.prox);

                                                        //insiro o novo registro na linha livre

                                                        arquivoRegistros.seek(qtdRegistros*registroInserir.tamanhoRegistro);

                                                        //escrevendo no arquivo o novo registro - chave, atributos , flag e prox
                                                         arquivoRegistros.writeInt(registroInserir.chave);

                                                         //escrever os atributos
                                                         for (Tipo atributo : registroInserir.atributos){

                                                            if (tipos.get(i).equalsIgnoreCase("integer ")) { 
                                                                Inteiro inteiro = (Inteiro) registroInserir.atributos.get(i);
                                                                arquivoRegistros.writeInt(inteiro.inteiro);

                                                            }
                                                            if (tipos.get(i).equalsIgnoreCase("float   ")) {
                                                              PontoFlutuante ponto = (PontoFlutuante) registroInserir.atributos.get(i);
                                                              arquivoRegistros.writeFloat(ponto.pontoFlutuante);
                                                            }
                                                            if (tipos.get(i).equalsIgnoreCase("double  ")) {
                                                              PontoFlutuanteDuplo ponto = (PontoFlutuanteDuplo) registroInserir.atributos.get(i);
                                                              arquivoRegistros.writeDouble(ponto.pontoFlutuanteDuplo);
                                                            }
                                                            if (tipos.get(i).equalsIgnoreCase("char    ")) {
                                                               Palavra palavra = (Palavra) registroInserir.atributos.get(i);
                                                               arquivoRegistros.writeUTF(palavra.palavra);
                                                            }
                                                            if (tipos.get(i).equalsIgnoreCase("boolean ")) {
                                                                Decisao decisao = (Decisao) registroInserir.atributos.get(i);
                                                                arquivoRegistros.writeBoolean(decisao.decisao);
                                                            }
                                                            if (tipos.get(i).equalsIgnoreCase("date    ")) {
                                                                Data data = (Data) registroInserir.atributos.get(i);
                                                                arquivoRegistros.writeUTF(data.data.toString());
                                                            }
                                                            if (tipos.get(i).equalsIgnoreCase("string  ")) {
                                                                Palavra palavra = (Palavra) registroInserir.atributos.get(i);
                                                                arquivoRegistros.writeUTF(palavra.palavra);
                                                            }
                                                            i++;
                                                         }
                                                         //escrever a flag 
                                                         arquivoRegistros.writeBoolean(registroInserir.flag);

                                                         //escrever proximo
                                                         arquivoRegistros.writeLong(registroInserir.prox);

                                                         termina =1;
                                             }
                                    }

                                 }

                             }

                        }

                     }   
 
            }//fim do catch
           finally{
            arquivoRegistros.close();
            tabelaHash.close();
            return registroInserir.prox;
        }

        /*RandomAccessFile tabelaHash = new RandomAccessFile(new File(nomeArquivoHash), "rw");
        RandomAccessFile arquivoRegistros = new RandomAccessFile(new File(nomeArquivoDados), "rw");
        DataInputStream atributos = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeTabela.toLowerCase() + "_atributos.dat")));
        Registro registro = null;
        int tam = Registro.tamanhoReg(nomeTabela);

        int hashcode = chave % 7;

        tabelaHash.seek(hashcode * CompartimentoHash.tamanhoRegistro);
        CompartimentoHash end = CompartimentoHash.le(tabelaHash);
        Registro proxRegistro = null;
        int proxEndereco = -2;
        int ultimoEnd = -2;
        try {
            while (end.prox != -1) {
                arquivoRegistros.seek(end.prox * tam);
                registro = Registro.le(atributos, arquivoRegistros, nomeTabela);
                if (chave == registro.chave && registro.flag == registro.OCUPADO) {
                    return -1;
                }
                if (registro.flag == registro.LIBERADO) {
                    proxRegistro = registro;
                    proxEndereco = end.prox;
                }
                ultimoEnd = end.prox;
                end.prox = registro.prox;
            }
            if (proxRegistro != null) {
                proxRegistro.chave = chave;
                proxRegistro.atributos = atr;
                proxRegistro.flag = registro.OCUPADO;
                arquivoRegistros.seek(proxEndereco * tam);
                proxRegistro.salva(arquivoRegistros);
                return proxEndereco;
            }
            end.prox = numRegistros;
            arquivoRegistros.seek(end.prox * tam);

            new Registro(chave, atr, -1, registro.OCUPADO).salva(arquivoRegistros);
            if (registro == null) {
                tabelaHash.seek(hashcode * CompartimentoHash.tamanhoRegistro);
                end.salva(tabelaHash);
                return end.prox;
            } else {
                registro.prox = numRegistros;
                arquivoRegistros.seek(ultimoEnd * tam);
                registro.salva(arquivoRegistros);
            }
            return registro.prox;
        } finally {
            tabelaHash.close();
            arquivoRegistros.close();
        }*/
       
    }

    /**
     * Executa exclusão em Arquivos por Encadeamento Exterior (Hash)
     *
     * @param codCli: chave do cliente a ser excluído
     * @param nomeArquivoHash nome do arquivo que contém a tabela Hash
     * @param nomeArquivoDados nome do arquivo onde os dados estão armazenados
     * @return endereço do cliente que foi excluído, -1 se cliente não existe
     */
    public int exclui(int CodCli, String nomeArquivoHash, String nomeArquivoDados, String nomeTabela) throws Exception {
        //TODO: Inserir aqui o código do algoritmo de remoção
        RandomAccessFile tabelaHash = new RandomAccessFile(new File(nomeArquivoHash), "rw");
        RandomAccessFile arquivo = new RandomAccessFile(new File(nomeArquivoDados), "rw");
        DataInputStream atributos = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeTabela.toLowerCase() + "_atributos.dat")));
        int anterior = -1;
        int tam = Registro.tamanhoReg(nomeTabela);
        try {
            int index = busca(CodCli, nomeArquivoHash, nomeArquivoDados, nomeTabela);

            if (index == -1) {

                return -1;

            } else {
                int reg = CodCli % 7;

                tabelaHash.seek(reg * CompartimentoHash.tamanhoRegistro);
                CompartimentoHash compartimento = CompartimentoHash.le(tabelaHash);

                arquivo.seek(compartimento.prox * tam);

                Registro registro = Registro.le(atributos, arquivo, nomeTabela);

                if (registro.chave == CodCli) {
                    registro.flag = registro.LIBERADO;
                    arquivo.seek(compartimento.prox * tam);
                    registro.salva(arquivo);

                    return compartimento.prox;
                } else {
                    arquivo.seek(index * tam);
                    registro = Registro.le(atributos, arquivo, nomeTabela);
                    registro.flag = registro.LIBERADO;
                    arquivo.seek(index * tam);
                    registro.salva(arquivo);
                    return index;
                }
            }

        } finally {
            tabelaHash.close();
            arquivo.close();

        }

    }

//   public static int numeroRegistros(RandomAccessFile in) throws IOException{
//        int contador = 0;
//        try{
//            if(in == null){
//                return 0;
//            }else{
//               in.read();
//            }
//        }catch(Exception e){
//            return contador;
//        }finally{
//            in.close();
//        }
//        
//    }
    public int numeroRegistros(String nomeArquivoHash, boolean aumenta, boolean diminui) throws IOException {

        RandomAccessFile arquivo = null;

        try {
            //Se arquivo não existe cria
            File f = new File(nomeArquivoHash);
            if (!f.exists()) {
                arquivo = new RandomAccessFile(f, "rw");
                arquivo.seek(0);
                arquivo.writeInt(0);
                return 0;
            }
            if (f.exists()) {
                arquivo = new RandomAccessFile(f, "rw");
                int i = arquivo.readInt();
                if (aumenta == true) {
                    arquivo.seek(0);
                    arquivo.writeInt(i + 1);
                    arquivo.seek(0);
                    return i + 1;
                }
                if (diminui == true) {
                    arquivo.seek(0);
                    arquivo.writeInt(i - 1);
                    arquivo.seek(0);
                    return i - 1;
                }
                return i;
            }

        } catch (Exception e) {
        } finally {
            if (arquivo != null) {
                arquivo.close();
            }
        }

        return 0;
    }
}
