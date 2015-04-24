/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dojohash;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class EncadeamentoExterior {

    public static final int mod = 7;

    /**
     * Cria uma tabela hash vazia de tamanho tam, e salva no arquivo
     * nomeArquivoHash Compartimento que não tem lista encadeada associada deve
     * ter valor igual a -1
     *
     * @param nomeArquivoHash nome do arquivo hash a ser criado
     * @param tam tamanho da tabela hash a ser criada
     */
    public void criaHash(String nomeArquivoHash, int tam) throws IOException {
        RandomAccessFile tabHash;

        try {
            tabHash = new RandomAccessFile(new File(nomeArquivoHash), "rw");

            for (int i = 0; i < tam; i++) {
                new CompartimentoHash(-1).salva(tabHash);
            }
        } catch (IOException e) {
        }
    }

    /**
     * Executa busca em Arquivos por Encadeamento Exterior (Hash) Assumir que
     * ponteiro para próximo nó é igual a -1 quando não houver próximo nó
     *
     * @param codCli: chave do cliente que está sendo buscado
     * @param nomeArquivoHash nome do arquivo que contém a tabela Hash
     * @param nomeArquivoDados nome do arquivo onde os dados estão armazenados
     * @return o endereco onde o cliente foi encontrado, ou -1 se não for
     * encontrado
     */
    public int busca(int codCli, String nomeArquivoHash, String nomeArquivoDados) throws Exception {
        RandomAccessFile tabHash = new RandomAccessFile(new File(nomeArquivoHash), "rw");
        RandomAccessFile tabDados = new RandomAccessFile(new File(nomeArquivoDados), "rw");
        Cliente c;
        int hashcode = codCli % mod;
        try{
        tabHash.seek(hashcode * CompartimentoHash.tamanhoRegistro);

        CompartimentoHash end = CompartimentoHash.le(tabHash);
        while (end.prox != -1) {
            tabDados.seek(end.prox * Cliente.tamanhoRegistro);
            c = Cliente.le(tabDados);
            if (codCli == c.codCliente && c.flag == Cliente.OCUPADO) {
                return end.prox;
            }
            end.prox = c.prox;
        }       
        return -1;
        }
        finally{
            tabHash.close();
            tabDados.close();
        }
    }

    /**
     * Executa inserção em Arquivos por Encadeamento Exterior (Hash)
     *
     * @param codCli: código do cliente a ser inserido
     * @param nomeCli: nome do Cliente a ser inserido
     * @param nomeArquivoHash nome do arquivo que contém a tabela Hash
     * @param nomeArquivoDados nome do arquivo onde os dados estão armazenados
     * @param numRegistros numero de registros que já existem gravados no
     * arquivo
     * @return endereço onde o cliente foi inserido, -1 se não conseguiu inserir
     */
    public int insere(int codCli, String nomeCli, String nomeArquivoHash, String nomeArquivoDados, int numRegistros) throws Exception {
        RandomAccessFile tabHash = new RandomAccessFile(new File(nomeArquivoHash), "rw");
        RandomAccessFile tabDados = new RandomAccessFile(new File(nomeArquivoDados), "rw");
        Cliente c = null;
        int hashcode = codCli % mod;

        tabHash.seek(hashcode * CompartimentoHash.tamanhoRegistro);
        CompartimentoHash end = CompartimentoHash.le(tabHash);
        Cliente prox_livre = null;
        int end_prox_livre = -2;
        int ultimo_end = -2;
        try{
        while (end.prox != -1) {
            tabDados.seek(end.prox * Cliente.tamanhoRegistro);
            c = Cliente.le(tabDados);
            if (codCli == c.codCliente && c.flag == Cliente.OCUPADO)
                return -1;
            if(c.flag == Cliente.LIBERADO){
                prox_livre = c;
                end_prox_livre = end.prox;
            }
            ultimo_end = end.prox;
            end.prox = c.prox;
        }       
        if(prox_livre != null){
            prox_livre.codCliente = codCli;
            prox_livre.nome = nomeCli;
            prox_livre.flag = Cliente.OCUPADO;
            tabDados.seek(end_prox_livre * Cliente.tamanhoRegistro);
            prox_livre.salva(tabDados);
            return end_prox_livre;
        }
        end.prox = numRegistros;
        tabDados.seek(end.prox*Cliente.tamanhoRegistro);
        new Cliente(codCli,nomeCli,-1,Cliente.OCUPADO).salva(tabDados);
        if(c == null){
            tabHash.seek(hashcode * CompartimentoHash.tamanhoRegistro);
            end.salva(tabHash);
            return end.prox;
        }
        else{
            c.prox = numRegistros;
            tabDados.seek(ultimo_end*Cliente.tamanhoRegistro);
            c.salva(tabDados);
        }
        return c.prox;
        }
        finally{
            tabHash.close();
            tabDados.close();
        }
    }

    /**
     * Executa exclusão em Arquivos por Encadeamento Exterior (Hash)
     *
     * @param codCli: chave do cliente a ser excluído
     * @param nomeArquivoHash nome do arquivo que contém a tabela Hash
     * @param nomeArquivoDados nome do arquivo onde os dados estão armazenados
     * @return endereço do cliente que foi excluído, -1 se cliente não existe
     */
    public int exclui(int codCli, String nomeArquivoHash, String nomeArquivoDados) throws IOException {
        RandomAccessFile tabHash = new RandomAccessFile(new File(nomeArquivoHash), "rw");
        RandomAccessFile tabDados = new RandomAccessFile(new File(nomeArquivoDados), "rw");
        Cliente c;
        int hashcode = codCli % mod;
        try{
        tabHash.seek(hashcode * CompartimentoHash.tamanhoRegistro);

        CompartimentoHash end = CompartimentoHash.le(tabHash);
        while (end.prox != -1) {
            tabDados.seek(end.prox * Cliente.tamanhoRegistro);
            c = Cliente.le(tabDados);
            if (codCli == c.codCliente && c.flag == Cliente.OCUPADO) {
                c.flag = Cliente.LIBERADO;
                tabDados.seek(end.prox * Cliente.tamanhoRegistro);
                c.salva(tabDados);
                return end.prox;
            }
            end.prox = c.prox;
        }       
        return -1;
        }
        finally{
            tabHash.close();
            tabDados.close();
        }
    }

}
