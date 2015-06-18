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

    public ArrayList<Tipo> atributos = new ArrayList<>();

    public static boolean LIBERADO = true;
    public static boolean OCUPADO = false;
    public int tamanhoRegistro;
    public boolean flag;
    public int prox;
    public int chave;

    public Registro() {

    }

    public Registro(int chave, ArrayList<Tipo> atributos, int prox, boolean flag) {
        this.chave = chave;
        this.atributos = atributos;
        this.prox = prox;
        this.flag = flag;
    }

    public static Registro le(DataInputStream atributos, RandomAccessFile registros, String nomeTabela) throws IOException {

        DataInputStream in = null;

        ArrayList<String> tipos = new ArrayList<>();
        Registro registro = new Registro();
        try {
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeTabela.toLowerCase() + "_atributos.dat")));

            while (true) {
                in.readUTF();
                tipos.add(in.readUTF());
            }

        } catch (Exception e) {

            registro.chave = atributos.readInt();
            Inteiro inteiro = new Inteiro(registro.chave);
            registro.atributos.add(0, inteiro);

            for (int i = 1; i < tipos.size(); i++) {

                if (tipos.get(i).equalsIgnoreCase("integer ")) {
                    inteiro = new Inteiro(registros.readInt());
                    registro.atributos.add(inteiro);
                    registro.tamanhoRegistro += Integer.BYTES;
                }
                if (tipos.get(i).equalsIgnoreCase("float   ")) {
                    PontoFlutuante pf = new PontoFlutuante(registros.readFloat());
                    registro.atributos.add(pf);
                    registro.tamanhoRegistro += Float.BYTES;
                }
                if (tipos.get(i).equalsIgnoreCase("double  ")) {
                    PontoFlutuanteDuplo pfd = new PontoFlutuanteDuplo(registros.readDouble());
                    registro.atributos.add(pfd);
                    registro.tamanhoRegistro += Double.BYTES;
                }
                if (tipos.get(i).equalsIgnoreCase("char    ")) {
                    Palavra str = new Palavra(registros.readUTF());
                    registro.atributos.add(str);
                    registro.tamanhoRegistro += 2;
                }
                if (tipos.get(i).equalsIgnoreCase("boolean ")) {
                    Decisao bool = new Decisao(registros.readBoolean());
                    registro.atributos.add(bool);
                    registro.tamanhoRegistro += 1;
                }
                if (tipos.get(i).equalsIgnoreCase("date    ")) {
                    Date d = new Date(registros.readUTF());
                    Data data = new Data(d);
                    registro.atributos.add(data);
                    registro.tamanhoRegistro += 12;
                }
                if (tipos.get(i).equalsIgnoreCase("string  ")) {
                    Palavra str = new Palavra(registros.readUTF());
                    registro.atributos.add(str);
                    registro.tamanhoRegistro += 20;
                }
            }
            registro.prox = registros.readInt();
            registro.flag = registros.readBoolean();
            
        } finally {
            in.close();
        }
        return null;
    }

    public void salva(RandomAccessFile out) throws IOException {
        for (int i = 0; i < atributos.size(); i++) {
            out.writeUTF(atributos.get(i).toString());
        }
        out.writeInt(prox);
        out.writeBoolean(flag);
    }

    /**
     *
     * @param nomeTabela
     * @return
     * @throws IOException
     */
    public static int tamanhoReg(String nomeTabela) throws IOException{

        DataInputStream in = null;
        ArrayList<String> nomes = new ArrayList<>();
        ArrayList<String> tipos = new ArrayList<>();
        int retorno = 0;
        try {
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeTabela.toLowerCase() + "_atributos.dat")));

            while (true) {
                nomes.add(in.readUTF());
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
                    retorno += 2;
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
        }finally{
            in.close();
        }
    }
}
