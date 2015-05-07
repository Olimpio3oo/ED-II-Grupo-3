/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dojohash;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class EncadeamentoInterior {

    /**
     * Cria uma tabela hash vazia de tamanho tam, e salva no arquivo
     * nomeArquivoHash Compartimento que não tem lista encadeada associada deve
     * ter registro com chave de Cliente igual a -1 Quando o ponteiro para
     * próximo for null, ele deve ser igual ao endereço do compartimento
     *
     * @param nomeArquivoHash nome do arquivo hash a ser criado
     * @param tam tamanho da tabela hash a ser criada
     */
    public void criaHash(String nomeArquivoHash, int tam) {
        RandomAccessFile tabelaHash;
        Cliente c;
        try {
            tabelaHash = new RandomAccessFile(new File(nomeArquivoHash), "rw");
            for (int i = 0; i < 7; i++) {
                c = new Cliente(-1, "          ", i, Cliente.LIBERADO);
                c.salva(tabelaHash);

            }
        } catch (Exception e) {
        }

    }

    /**
     * Executa busca em Arquivos por Encadeamento Interior (Hash) Assumir que
     * ponteiro para próximo nó é igual ao endereço do compartimento quando não
     * houver próximo nó
     *
     * @param codCli: chave do cliente que está sendo buscado
     * @param nomeArquivoHash nome do arquivo que contém a tabela Hash
     * @return Result contendo a = 1 se registro foi encontrado, e end igual ao
     * endereco onde o cliente foi encontrado ou a = 2 se o registro não foi
     * encontrado, e end igual ao primeiro endereço livre encontrado na lista
     * encadeada, ou -1 se não encontrou endereço livre
     */
    public Result busca(int codCli, String nomeArquivoHash) throws Exception {
        
        int registro = codCli % 7;
        int a = 0;
        int end = 0;
        int liberado = -1;
        Cliente c;
        boolean existeProximo = false;
        boolean buscando = true;
        RandomAccessFile tabela = null;
        try {
            tabela = new RandomAccessFile(new File(nomeArquivoHash), "r");
            while (buscando) {
                
                  if (!existeProximo) {
                    end = registro;
                }
                tabela.seek(end * Cliente.tamanhoRegistro);
                c = Cliente.le(tabela);

                if (c.flag == Cliente.LIBERADO) {
                    liberado = end;
                }
                if (c.flag == Cliente.OCUPADO && c.codCliente == codCli) {
                    a = 1;
                    buscando = false;

                } else if (end == c.prox) {
                    a = 2;
                    if (liberado != -1) {
                        end = liberado;
                    }
                    buscando = false;

                } else {
                    existeProximo = true;
                    end = c.prox;

                }

            }
        } catch (Exception e) {
        } finally {
            if (tabela != null) {
                tabela.close();
            }
        }
        return new Result(a, end);
        
    }

    /**
     * Executa inserção em Arquivos por Encadeamento Exterior (Hash)
     *
     * @param codCli: código do cliente a ser inserido
     * @param nomeCli: nome do Cliente a ser inserido
     * @param nomeArquivoHash nome do arquivo que contém a tabela Hash
     * @return endereço onde o cliente foi inserido, -1 se não conseguiu inserir
     * (inclusive em caso de overflow)
     */
    public int insere(int codCli, String nomeCli, String nomeArquivoHash) throws Exception {
             Result res = busca(codCli, nomeArquivoHash);
        int end = res.end;
        int j = end;
        int controle = res.a;

        if (controle == 1) {
            return -1;
        }

        RandomAccessFile raf = null;
        try {
            int i = 0;
            raf = new RandomAccessFile(new File(nomeArquivoHash), "rw");
            while (i < 7) {
                Cliente novo = new Cliente(codCli, nomeCli, j, Cliente.OCUPADO);
                raf.seek(j * Cliente.tamanhoRegistro);
                Cliente chave = Cliente.le(raf);
                raf.seek(j * Cliente.tamanhoRegistro);
                if (chave.flag == Cliente.LIBERADO) {
                    if (chave.prox != j) {
                        novo.prox = chave.prox;
                    }
                    novo.salva(raf);
                    if (j != end) {
                        Cliente aux = Cliente.le(raf);
                        aux.prox = j;
                        aux.salva(raf);
                    }
                    break;
                } else {
                    if (j != 6) {
                        j++;
                    } else {
                        j = 0;
                    }
                }
                i++;
            }
        } catch (Exception e) {

        } finally {
            if (raf != null) {
                raf.close();
            }
        }
        return j;
    }

    /**
     * Executa exclusão em Arquivos por Encadeamento Exterior (Hash)
     *
     * @param codCli: chave do cliente a ser excluído
     * @param nomeArquivoHash nome do arquivo que contém a tabela Hash
     * @return endereço do cliente que foi excluído, -1 se cliente não existe
     */
    public int exclui(int codCli, String nomeArquivoHash) throws Exception {
        Result res = busca(codCli, nomeArquivoHash);
        int end = res.end;
        int j = end;
        int controle = res.a;

        if (controle == 2) {
            return -1;
        }
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(new File(nomeArquivoHash), "rw");
            raf.seek(j * Cliente.tamanhoRegistro);
            Cliente c = Cliente.le(raf);
            c.flag = Cliente.LIBERADO;
            raf.seek(j * Cliente.tamanhoRegistro);
            c.salva(raf);

        } catch (Exception e) {

        } finally {
            if (raf != null) {
                raf.close();
            }
        }
        return end;
    }

}
