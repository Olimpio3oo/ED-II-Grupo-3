/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dados;

import static dados.Registro.tamanhoReg;
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
            Registro registro = Registro.le(arquivo, nomeTabela);
            arquivo.seek(compartimento.prox * tam);

            int anterior = -1;
            if (registro.chave == chave && registro.flag == registro.OCUPADO) {

                return compartimento.prox;
            } else {
                while (registro.prox != -1 && registro.chave != chave) {
                    anterior = registro.prox;
                    arquivo.seek(anterior * tam);
                    registro = Registro.le(arquivo, nomeTabela);

                }
                if (registro.chave == chave && registro.flag == registro.OCUPADO) {

                    return anterior;
                } else {
                    if (registro.flag == true) {
                        while (registro.prox != -1) {
                            anterior = registro.prox;
                            arquivo.seek(anterior * tam);
                            registro = Registro.le(arquivo, nomeTabela);
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

    //essa busca retorna o registro
    /**
     *
     * @param registroBusca
     * @param nomeArquivoDados
     * @param nomeTabela
     * @return
     * @throws Exception
     */
    public Registro buscaRegistro(Registro registroBusca, RandomAccessFile arquivoRegistros, String nomeTabela) throws Exception {

        //RandomAccessFile arquivoRegistros = new RandomAccessFile(new File(nomeArquivoDados), "rw");
        //DataInputStream atributos = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeTabela.toLowerCase() + "_atributos.dat")));
        Registro registroAtual = new Registro();
        int combina = 0;

        registroAtual = Registro.le(arquivoRegistros, nomeTabela); //registro atual

        if (registroBusca.chave == registroAtual.chave) {
            combina++;
        }

        try {
            for (int j = 0; j < registroAtual.atributos.size(); j++) {
                for (int x = 0; x < registroAtual.atributos.size(); x++) {
                    if (registroBusca.atributos.get(j) == registroAtual.atributos.get(x)) {
                        combina++;
                    }

                    Palavra p1 = (Palavra)registroBusca.atributos.get(j);
                    Palavra p2 = (Palavra)registroAtual.atributos.get(x);
                    if (p1.palavra.equalsIgnoreCase(p2.palavra)) {
                        combina++;
                    }

                }
            }
        } finally {
            if (combina > 0) {
                return registroAtual;
            } else {
                return null;
            }
        }

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
    public int insere(int chave, ArrayList<Tipo> atr, String nomeArquivoHash, String nomeArquivoDados, String nomeTabela) throws Exception {
        int qtdRegistros = 0;
        RandomAccessFile arquivoRegistros = new RandomAccessFile(new File(nomeArquivoDados), "rw"); //abrindo o arquivo de registros
        int tamRegistro = tamanhoReg(nomeTabela);

        try {//consultar quantros registros já tem no arquivo
            while (true) {
                arquivoRegistros.readInt();
                qtdRegistros++; //quantos registros já tem no arquivo 
                arquivoRegistros.seek(qtdRegistros * tamRegistro); //ja deixa o ponteiro na posição certa para inserção
            }
        } catch (Exception e) {
            RandomAccessFile tabelaHash = new RandomAccessFile(new File(nomeArquivoHash), "rw");
            //RandomAccessFile arquivoRegistros = new RandomAccessFile(new File(nomeArquivoDados), "rw");
            DataInputStream atributos = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeTabela + "_atributos.dat")));
            Registro registro = null; //registro atual
            int tam = Registro.tamanhoReg(nomeTabela);

            int hashcode = chave % 7;

            //bia -  pegando o ultimo endereço 
            tabelaHash.seek(hashcode * CompartimentoHash.tamanhoRegistro);
            int ultimoEnd = tabelaHash.readInt();

            tabelaHash.seek(hashcode * CompartimentoHash.tamanhoRegistro);
            CompartimentoHash end = CompartimentoHash.le(tabelaHash);
            Registro proxRegistro = null;
            int proxEndereco = -2;
            //int ultimoEnd = -2;

            try {
                while (end.prox != -1) { //colisão
                    arquivoRegistros.seek(end.prox * tam);
                    registro = Registro.le(arquivoRegistros, nomeTabela); //registro atual
                    if (chave == registro.chave && registro.flag == registro.OCUPADO) {
                        return -1;
                    }
                    if (registro.flag == registro.LIBERADO) {
                        proxRegistro = registro;
                        proxEndereco = end.prox;
                    }

                    end.prox = registro.prox;
                   //ultimoEnd = end.prox;

                    //bia 
                    if (registro.prox != -1) {
                        ultimoEnd = registro.prox;
                    }
                }
                if (proxRegistro != null) {
                    proxRegistro.chave = chave;
                    proxRegistro.atributos = atr;
                    proxRegistro.flag = registro.OCUPADO;
                    arquivoRegistros.seek(proxEndereco * tam);
                    proxRegistro.salva(arquivoRegistros, nomeTabela);
                    return proxEndereco;
                }
                end.prox = qtdRegistros;
                arquivoRegistros.seek(end.prox * tam);

                new Registro(chave, atr, -1, registro.OCUPADO).salva(arquivoRegistros, nomeTabela);
                if (registro == null) {
                    tabelaHash.seek(hashcode * CompartimentoHash.tamanhoRegistro);
                    end.salva(tabelaHash);
                    return end.prox;
                } else {
                    registro.prox = qtdRegistros;
                    arquivoRegistros.seek(ultimoEnd * tam);
                    registro.salvaVelho(arquivoRegistros, nomeTabela);
                }
                return registro.prox;
            } finally {
                tabelaHash.close();
                arquivoRegistros.close();
            }
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

                Registro registro = Registro.le(arquivo, nomeTabela);

                if (registro.chave == CodCli) {
                    registro.flag = registro.LIBERADO;
                    arquivo.seek(compartimento.prox * tam);
                    registro.salvaVelho(arquivo, nomeTabela);

                    return compartimento.prox;
                } else {
                    arquivo.seek(index * tam);
                    registro = Registro.le(arquivo, nomeTabela);
                    registro.flag = registro.LIBERADO;
                    arquivo.seek(index * tam);
                    registro.salvaVelho(arquivo, nomeTabela);
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
    public int numeroRegistros(String nomeTabela) throws IOException {

        int qtdRegistros = 0;
        RandomAccessFile arquivoRegistros = new RandomAccessFile(new File(nomeTabela + "_registros.dat"), "rw"); //abrindo o arquivo de registros
        int tamRegistro = tamanhoReg(nomeTabela);

        try {//consultar quantros registros já tem no arquivo
            while (true) {
                arquivoRegistros.readInt();
                qtdRegistros++; //quantos registros já tem no arquivo 
                arquivoRegistros.seek(qtdRegistros * tamRegistro); //ja deixa o ponteiro na posição certa para inserção
            }
        } catch (Exception e) {
            return qtdRegistros;
        }

    }
}

/*RandomAccessFile arquivo = null;

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
 }*/
