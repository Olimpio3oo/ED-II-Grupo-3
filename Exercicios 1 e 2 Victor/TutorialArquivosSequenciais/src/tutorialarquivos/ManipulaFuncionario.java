/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorialarquivos;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vanessa
 */
public class ManipulaFuncionario {

    /**
     * @param args the command line arguments
     */            
    public static void main(String[] args) throws Exception {
        //Declara o outputStream
        DataOutputStream out = null;
        
        //cria uma lista de funcionários
        List<Funcionario> funcs = null;
        funcs = new ArrayList<Funcionario>();
        
        //objeto para manipular Funcionário
        Funcionario f;                
        //Cria o primeiro funcionário
        f = new Funcionario();
        f.codFuncionario = 1;
        f.cpf = "012.345.678-90";
        f.nome = "Maria";
        f.dataNascimento = "01/01/1980";
        f.salario = 1000;
        //Adiciona o funcionário na lista
        funcs.add(f);
        //Cria o segundo funcionário
        f = new Funcionario();
        f.codFuncionario = 2;
        f.cpf = "234.567.890-12";
        f.nome = "Carlos";
        f.dataNascimento = "30/04/1976";
        f.salario = 3000;
        //Adiciona o funcionário na lista
        funcs.add(f);

        try {
            System.out.println("Abrindo arquivo Funcionario.dat para gravação...");
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("funcionario.dat")));
            System.out.println("Gravando dados no arquivo Funcionario.dat ...");
            for (int i = 0; i < funcs.size(); i++) {
                out.writeInt(funcs.get(i).codFuncionario);
                out.writeUTF(funcs.get(i).nome);
                out.writeUTF(funcs.get(i).cpf);
                out.writeUTF(funcs.get(i).dataNascimento);
                out.writeDouble(funcs.get(i).salario);
                System.out.println("Gravação do funcionário de código " + funcs.get(i).codFuncionario + " realizada com sucesso");                
            }                 
        } finally {
            System.out.println("Fechando arquivo Funcionario.dat ...");
            if (out != null)
                out.close();
        }

        //Trecho para ler os dados
        //Declara o InputStream
        DataInputStream in = null;
        int i = 0;
        try {
            System.out.println("Abrindo arquivo Funcionario.dat para leitura...");
            in = new DataInputStream(new BufferedInputStream(new FileInputStream("funcionario.dat")));
            System.out.println("Lendo os dados do arquivo Funcionario.dat ...");
            
            while (true) {                
                System.out.println("codigo -> " + in.readInt());
                System.out.println("nome -> " + in.readUTF());
                System.out.println("cpf -> " + in.readUTF());
                System.out.println("data de nascimento -> " + in.readUTF());
                System.out.println("salario -> " + in.readDouble());
                System.out.println();
                i++;                
            }
            
        } catch (EOFException e) {
            //Arquivo terminou - fecha no finally
        } finally {
            System.out.println("Fechando arquivo Funcionario.dat ...");
            if (in != null)
                in.close();
        }
        System.out.println("Total de " + i + " funcionários lidos!");
    }
}
