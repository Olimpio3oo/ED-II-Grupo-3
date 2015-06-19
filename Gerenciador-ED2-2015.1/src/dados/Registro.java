/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Romulo Mourão
 */
public class Registro {

    public int chave;
    public ArrayList<Tipo> atributos = new ArrayList<>();
    public boolean flag;
    public int prox;

    public int tamanhoRegistro; //em bytes 

    public static boolean LIBERADO = true;
    public static boolean OCUPADO = false;

    public Registro() {

    }

    public Registro(int chave, ArrayList<Tipo> atributos, int prox, boolean flag) {
        this.chave = chave;
        this.atributos = atributos;
        this.prox = prox;
        this.flag = flag;
    }

    public static Registro le(RandomAccessFile arquivoRegistros, String nomeTabela) throws IOException {

        ArrayList<String> tipos = new ArrayList<>();

        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeTabela.toLowerCase() + "_atributos.dat")));

            while (true) {
                in.readUTF(); //lendo nomes 
                tipos.add(in.readUTF());
            }

        } catch (Exception e) {

            //lendo o registros atual que está ocupando a posição
            Registro atual = new Registro();
            atual.chave = arquivoRegistros.readInt();

            
                //percorrer array de tipos e ler
                for (int i = 1; i < tipos.size(); i++) {
                    if (tipos.get(i).equalsIgnoreCase("integer ")) {
                        Inteiro inteiro = new Inteiro(arquivoRegistros.readInt());
                        atual.atributos.add(inteiro);
                    }
                    if (tipos.get(i).equalsIgnoreCase("float   ")) {
                        PontoFlutuante ponto = new PontoFlutuante(arquivoRegistros.readFloat());
                        atual.atributos.add(ponto);
                    }
                    if (tipos.get(i).equalsIgnoreCase("double  ")) {
                        PontoFlutuanteDuplo ponto = new PontoFlutuanteDuplo(arquivoRegistros.readDouble());
                        atual.atributos.add(ponto);
                    }
                    if (tipos.get(i).equalsIgnoreCase("char    ")) {
                        Palavra palavra = new Palavra(arquivoRegistros.readUTF());
                        atual.atributos.add(palavra);
                    }
                    if (tipos.get(i).equalsIgnoreCase("boolean ")) {
                        Decisao decisao = new Decisao(arquivoRegistros.readBoolean());
                        atual.atributos.add(decisao);
                    }
                    if (tipos.get(i).equalsIgnoreCase("date    ")) {
                        String dataAux = arquivoRegistros.readUTF(); //leio a data em string do arquivo
                        Date data = new Date(dataAux); //crio um objeto dat com a data que estava no arquivo
                        Data dataFinal = new Data(data); // crio um objeto Data( Tipo ) com os dados pra pode adicionar nos atributos 
                        atual.atributos.add(dataFinal); //adiciona em atributos                 
                    }
                    if (tipos.get(i).equalsIgnoreCase("string  ")) {
                        Palavra palavra = new Palavra(arquivoRegistros.readUTF());
                        atual.atributos.add(palavra);
                    }
                    i++;
                }
            
                atual.flag = arquivoRegistros.readBoolean();
                atual.prox = arquivoRegistros.readInt();

           
                return atual;
            

        }
    }

    public void salva(RandomAccessFile arquivoRegistros, String nomeTabela) throws IOException {

        ArrayList<String> tipos = new ArrayList<>();

        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeTabela + "_atributos.dat")));

            while (true) {
                in.readUTF(); //lendo nomes 
                tipos.add(in.readUTF());
            }

        } catch (Exception e) {

            //escrevendo no arquivo o novo registro - chave, atributos , flag e prox
            arquivoRegistros.writeInt(this.chave);
            System.out.println(this.chave);

            //escrever os atributos
            for (int i = 1; i < tipos.size(); i++) {

                if (tipos.get(i).equalsIgnoreCase("integer ")) {
                    Inteiro inteiro = (Inteiro) this.atributos.get(i);
                    arquivoRegistros.writeInt(inteiro.inteiro);
                    System.out.println(inteiro.inteiro);

                }
                if (tipos.get(i).equalsIgnoreCase("float   ")) {
                    PontoFlutuante ponto = (PontoFlutuante) this.atributos.get(i);
                    arquivoRegistros.writeFloat(ponto.pontoFlutuante);
                }
                if (tipos.get(i).equalsIgnoreCase("double  ")) {
                    PontoFlutuanteDuplo ponto = (PontoFlutuanteDuplo) this.atributos.get(i);
                    arquivoRegistros.writeDouble(ponto.pontoFlutuanteDuplo);
                }
                if (tipos.get(i).equalsIgnoreCase("char    ")) {
                    Palavra palavra = (Palavra) this.atributos.get(i);
                    arquivoRegistros.writeUTF(palavra.palavra);
                }
                if (tipos.get(i).equalsIgnoreCase("boolean ")) {
                    Decisao decisao = (Decisao) this.atributos.get(i);
                    arquivoRegistros.writeBoolean(decisao.decisao);
                }
                if (tipos.get(i).equalsIgnoreCase("date    ")) {
                    Data data = (Data) this.atributos.get(i);
                    arquivoRegistros.writeUTF(data.data.toString());
                }
                if (tipos.get(i).equalsIgnoreCase("string  ")) {
                   // System.out.println(this.atributos.get(i));
                   //System.out.println("---------------------");
                    Palavra palavra = (Palavra) this.atributos.get(i);
                    arquivoRegistros.writeUTF(palavra.palavra);
                    System.out.println(palavra.palavra);
                }
                //i++;
            }

            //escrever a flag 
            arquivoRegistros.writeBoolean(this.flag);
            System.out.println(this.flag);

            //escrever proximo
            arquivoRegistros.writeInt(this.prox);
            System.out.println(this.prox);

        }
    }
    
    
    public void salvaVelho(RandomAccessFile arquivoRegistros, String nomeTabela) throws IOException {

        ArrayList<String> tipos = new ArrayList<>();
        int i = 0;

        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeTabela + "_atributos.dat")));

            while (true) {
                in.readUTF(); //lendo nomes 
                tipos.add(in.readUTF());
            }

        } catch (Exception e) {

            //escrevendo no arquivo o novo registro - chave, atributos , flag e prox
            arquivoRegistros.writeInt(this.chave);
            System.out.println(this.chave);

            //escrever os atributos
            for (int j = 1; j < tipos.size(); j++) {

                if (tipos.get(j).equalsIgnoreCase("integer ")) {
                    Inteiro inteiro = (Inteiro) this.atributos.get(i);
                    arquivoRegistros.writeInt(inteiro.inteiro);
                    System.out.println(inteiro.inteiro);

                }
                if (tipos.get(j).equalsIgnoreCase("float   ")) {
                    PontoFlutuante ponto = (PontoFlutuante) this.atributos.get(i);
                    arquivoRegistros.writeFloat(ponto.pontoFlutuante);
                }
                if (tipos.get(j).equalsIgnoreCase("double  ")) {
                    PontoFlutuanteDuplo ponto = (PontoFlutuanteDuplo) this.atributos.get(i);
                    arquivoRegistros.writeDouble(ponto.pontoFlutuanteDuplo);
                }
                if (tipos.get(j).equalsIgnoreCase("char    ")) {
                    Palavra palavra = (Palavra) this.atributos.get(i);
                    arquivoRegistros.writeUTF(palavra.palavra);
                }
                if (tipos.get(j).equalsIgnoreCase("boolean ")) {
                    Decisao decisao = (Decisao) this.atributos.get(i);
                    arquivoRegistros.writeBoolean(decisao.decisao);
                }
                if (tipos.get(j).equalsIgnoreCase("date    ")) {
                    Data data = (Data) this.atributos.get(i);
                    arquivoRegistros.writeUTF(data.data.toString());
                }
                if (tipos.get(j).equalsIgnoreCase("string  ")) {
                    Palavra palavra = (Palavra) this.atributos.get(i);
                    arquivoRegistros.writeUTF(palavra.palavra);
                    System.out.println(palavra.palavra);
                }
                i++;
            }

            //escrever a flag 
            arquivoRegistros.writeBoolean(this.flag);
            System.out.println(this.flag);

            //escrever proximo
            arquivoRegistros.writeInt(this.prox);
            System.out.println(this.prox);

        }
    }


    /**
     *
     * @param nomeTabela
     * @return
     * @throws IOException
     */
    public static int tamanhoReg(String nomeTabela) throws IOException {

        DataInputStream in = null;
        //ArrayList<String> nomes = new ArrayList<>();
        ArrayList<String> tipos = new ArrayList<>();
        int retorno = 0;
        try {
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeTabela.toLowerCase() + "_atributos.dat")));

            while (true) {
                in.readUTF();
                tipos.add(in.readUTF());
            }

        } catch (Exception e) {

            for (int i = 0; i < tipos.size(); i++) {

                if (tipos.get(i).equalsIgnoreCase("integer ")) {
                    retorno += Integer.BYTES;
                }
                if (tipos.get(i).equalsIgnoreCase("float   ")) {
                    retorno += Float.BYTES;
                }
                if (tipos.get(i).equalsIgnoreCase("double  ")) {
                    retorno += Double.BYTES;
                }
                if (tipos.get(i).equalsIgnoreCase("char    ")) {
                    retorno += 2;
                }
                if (tipos.get(i).equalsIgnoreCase("boolean ")) {
                    retorno += 1;
                }
                if (tipos.get(i).equalsIgnoreCase("date    ")) {
                    retorno += 12;
                }
                if (tipos.get(i).equalsIgnoreCase("string  ")) {
                    retorno += 20;
                }

            }
            retorno += 5;
            return retorno;
        } finally {
            in.close();
        }
    }
}
