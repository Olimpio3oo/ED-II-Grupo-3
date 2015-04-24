/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package appbanco;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author BiaHome
 */
public class AppBanco {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //Declara o outputStream
        DataOutputStream out = null;
        
        //cria uma lista de funcionários
        List<ContaCorrente> contac = null;
       // contac = new ArrayList<>();
        
        //objeto para manipular Funcionário
        ContaCorrente cc;
        
         //cria uma lista de funcionários
        List<Agencia> agen = null;
        //agen = new ArrayList<>();
        
        //objeto para manipular Funcionário
        Agencia a;
        
        Scanner sc = new Scanner( System.in );
	boolean ok = true; 
        int op;

        while(ok){
            
             System.out.println( "\nEscolha sua opção:" );
             System.out.println( "\n1-Cadastrar Agencia" );
             System.out.println( "\n2-Ler Agencia" );
             System.out.println( "\n3-Cadastrar Conta" );
             System.out.println( "\n4-Ler Conta" );
             System.out.println( "\n5-Sair" );
             op = sc.nextInt();
 
            switch(op){
                
                case 1: 
                    System.out.println("\nDigite o código da agência ");
                    int c = sc.nextInt();
                    System.out.println("\nDigite o nome da agência ");
                    String n = sc.next();
                    System.out.println("\nDigite o nome do gerente ");
                    String g = sc.next();   
                    
                    agen = new ArrayList<>();  
                    
                    a = new Agencia(c,n,g);
                    agen.add(a);
                   
                    
                   try {
                         System.out.println("Abrindo arquivo agencia.dat para gravação...");
                        //passando true no FileOutputStrem, novos registros são adicionados ao arquivo, caso ele já exista
                        out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("agencia.dat", true)));
                        System.out.println("Gravando dados no arquivo agencia.dat ...");
                        agen.get(0).salva(out);        
                   }finally {
                        System.out.println("Fechando arquivo agencia.dat ...");
                        if (out != null)
                            out.close();
                    }
                   
                    break;

                case 2: 
                    
                     //Trecho para ler os dados
                    //Declara o InputStream
                    DataInputStream innn = null;
                    //Zera a lista de funcionários
                    agen = new ArrayList<>();        
                    try {
                        System.out.println("Abrindo arquivo agencia.dat para leitura...");
                        innn = new DataInputStream(new BufferedInputStream(new FileInputStream("agencia.dat")));
                        System.out.println("Lendos os dados do arquivo agencia.dat ...");

                        while (true) {
                            a = new Agencia();
                            a.le(innn);
                            a.imprime();
                            agen.add(a);
                            System.out.println();                
                        }

                    } catch (EOFException e) {
                        //arquivo terminou
                    } finally {
                        System.out.println("Fechando arquivo agencia.dat ...");
                        if (innn != null)
                            innn.close();
                    }
                    System.out.println("Total de " + agen.size() + " agencias lidas!");       
                    break;

                case 3: 
                    System.out.println("\nDigite o código da conta ");
                    int ccont = sc.nextInt();
                    System.out.println("\nDigite o código da agência ");
                    int ca = sc.nextInt();
                    System.out.println("\nDigite o saldo da conta ");
                    double s = sc.nextDouble();   
                    
                    contac = new ArrayList<>();  
                    
                    cc = new ContaCorrente(ccont,ca,s);
                    contac.add(cc);
                   
                    
                   try {
                         System.out.println("Abrindo arquivo conta.dat para gravação...");
                        //passando true no FileOutputStrem, novos registros são adicionados ao arquivo, caso ele já exista
                        out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("conta.dat", true)));
                        System.out.println("Gravando dados no arquivo conta.dat ...");
                        contac.get(0).salva(out);        
                   }finally {
                        System.out.println("Fechando arquivo conta.dat ...");
                        if (out != null)
                            out.close();
                    }
                    break;

                case 4: 
                    //Trecho para ler os dados
                    //Declara o InputStream
                    DataInputStream inn = null;
                    //Zera a lista de funcionários
                    contac = new ArrayList<>();        
                    try {
                        System.out.println("Abrindo arquivo conta.dat para leitura...");
                        inn = new DataInputStream(new BufferedInputStream(new FileInputStream("conta.dat")));
                        System.out.println("Lendos os dados do arquivo conta.dat ...");

                        while (true) {
                            cc = new ContaCorrente();
                            cc.le(inn);
                            cc.imprime();
                            contac.add(cc);
                            System.out.println();                
                        }

                    } catch (EOFException e) {
                        //arquivo terminou
                    } finally {
                        System.out.println("Fechando arquivo conta.dat ...");
                        if (inn != null)
                            inn.close();
                    }
                    System.out.println("Total de " + contac.size() + " contas lidas!");       
                    break;

               case 5: 
                    ok = false;
                    break;

                default:
                    System.out.println("Você digitou uma opção inválida!");

             }
        }
    }
}
