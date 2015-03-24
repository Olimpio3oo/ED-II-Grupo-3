/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadastrobanco;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Victor
 */
public class CadastroBanco {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ArrayList<Agencia> agencias = new ArrayList();
        ArrayList<ContaCorrente> contas = new ArrayList();

        Agencia agencia = new Agencia();
        ContaCorrente conta = new ContaCorrente();

        Scanner teclado = new Scanner(System.in);

        DataOutputStream out = null;
        DataInputStream in = null;

        boolean continua = true;
        boolean checagem = true;
        int resposta;

        while (continua) {
            System.out.println("Escolha sua opção:");
            System.out.println("1-Cadastrar Agencia");
            System.out.println("2-Ler Agencia");
            System.out.println("3-Cadastrar Conta");
            System.out.println("4-Ler Conta");
            System.out.println("5-Sair");
            resposta = teclado.nextInt();

            switch (resposta) {
                case 1:
                    System.out.println("Digite o número da agencia");
                    agencia.setCod(teclado.nextInt());

                    System.out.println("Digite o nome da agencia");
                    agencia.setNome(teclado.next());

                    System.out.println("Digite o nome do gerente");
                    agencia.setGerente(teclado.next());

                    agencias.add(agencia);

                    try {
                        System.out.println("Abrindo arquivo agencia.dat para gravação...");
                        //passando true no FileOutputStrem, novos registros são adicionados ao arquivo, caso ele já exista
                        out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("agencia.dat", true)));
                        System.out.println("Gravando dados no arquivo agencia.dat ...");
                        agencia.gravarArquivo(out);
                    } finally {
                        System.out.println("Fechando arquivo agencia.dat ...");
                        if (out != null) {
                            out.close();
                        }
                    }

                    break;
                case 2:
                    lerArquivo(agencias, agencia, in);
                    break;
                case 3:
                    agencias = procurarAgencia(agencias, in);
                    System.out.println("Digite o número da conta");
                    conta.setCodConta(teclado.nextInt());

                    System.out.println("Digite o  número da agencia");
                    conta.setCodAgencia(teclado.nextInt());

                    for (int i = 0; i < agencias.size(); i++) {

                        if (conta.getCodAgencia() != agencias.get(i).getCodAgencia()) {

                            System.out.println("Numero de agencia incorreto, por favor digite novamente o nnumero da agencia");
                            conta.setCodAgencia(teclado.nextInt());
                        } 
                        
                        if(conta.getCodAgencia() == agencias.get(i).getCodAgencia()){
                            break;
                        }
                    }

                    System.out.println("Digite o saldo da conta");
                    conta.setSaldo(teclado.nextDouble());

                    contas.add(conta);

                    try {
                        System.out.println("Abrindo arquivo conta.dat para gravação...");
                        //passando true no FileOutputStrem, novos registros são adicionados ao arquivo, caso ele já exista
                        out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("conta.dat", true)));
                        System.out.println("Gravando dados no arquivo conta.dat ...");
                        conta.gravarArquivo(out);
                    } finally {
                        System.out.println("Fechando arquivo conta.dat ...");
                        if (out != null) {
                            out.close();
                        }
                    }

                    break;
                case 4:
                    lerArquivo(contas, conta, in);
                    break;

                case 5:
                    continua = false;
            }

        }
    }

    public static void lerArquivo(ArrayList a, Agencia agencia, DataInputStream in) throws IOException {
        try {
            System.out.println("Abrindo arquivo agencia.dat para gravação...");
            in = new DataInputStream(new BufferedInputStream(new FileInputStream("agencia.dat")));

            System.out.println("Lendo dados no arquivo agencia.dat ...");

            a = new ArrayList();

            while (true) {
                agencia = new Agencia();
                agencia.lerArquivo(in);
                agencia.imprimir();
                a.add(agencia);
                System.out.println("");
            }
        } catch (Exception e) {

        } finally {
            System.out.println("Fechando arquivo agencia.dat ...");
            if (in != null) {
                in.close();
            }
        }
    }

    public static void lerArquivo(ArrayList a, ContaCorrente conta, DataInputStream in) throws IOException {
        try {
            System.out.println("Abrindo arquivo conta.dat para gravação...");
            in = new DataInputStream(new BufferedInputStream(new FileInputStream("conta.dat")));

            System.out.println("Lendo dados no arquivo conta.dat ...");

            a = new ArrayList();

            while (true) {
                conta = new ContaCorrente();
                conta.lerArquivo(in);
                conta.imprimir();
                a.add(conta);
                System.out.println("");
            }
        } catch (Exception e) {

        } finally {
            System.out.println("Fechando arquivo conta.dat ...");
            if (in != null) {
                in.close();
            }
        }
    }

    public static ArrayList procurarAgencia(ArrayList<Agencia> a, DataInputStream in) throws IOException {
        try {

            in = new DataInputStream(new BufferedInputStream(new FileInputStream("agencia.dat")));

            a = new ArrayList<Agencia>();
            Agencia agencia;
            while (true) {
                agencia = new Agencia();
                agencia.lerArquivo(in);
                a.add(agencia);

            }
        } catch (Exception e) {

        } finally {
            if (in != null) {
                in.close();
            }
        }
        return a;
    }

}
