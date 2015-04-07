package dados;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author ED2 - GRUPO 3 | 2015.1
 */
public class Gerenciador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
    
            
      
        
        boolean rodando = true;
        boolean inserindo = true;
        Tabela tab = new Tabela();
        HashMap<String, String> lista = new HashMap<>();
        List<Tabela> tabelas = new ArrayList<>();
        int option;
        String nome, atr, tipo, control;

        System.out.println((char)27 + "[33m██████████████████████");
        System.out.println((char)27 + "[33m██████████████████████");
        System.out.println((char)27 + "[33m████ GRUPO 3 - ED2 2015.1█████");
        System.out.println((char)27 + "[33m██████████████████████");
        System.out.println((char)27 + "[33m██████████████████████"+(char)27 + "[0m");
        Scanner teclado = new Scanner(System.in);
        while (rodando) {
            //menu
            System.out.println("");
            System.out.println((char)27 + "[33m‖█ MENU ██████████████"+(char)27 + "[0m");
           
            System.out.println("‖█ "+(char)27 + "[36m❶ - NOVA TABELA "+(char)27+"[0m");
          
            System.out.println("‖█ "+(char)27 + "[36m❷ - SAIR   ");
            System.out.println((char)27 + "[33m⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻"+(char)27 + "[0m");
           
            //Input do teclado
            option = teclado.nextInt();
            System.out.println((char)27 + "[36m");
            teclado.nextLine();
            System.out.println("");
            switch (option) {
                case 1:

                    System.out.println("_____________________________________");
                    System.out.print("‖█ NOME DA TABELA1:" );
                    nome = teclado.nextLine();
                    inserindo = true;
                    while (inserindo) {
                        System.out.print("——————| "+nome.toUpperCase()+" |——————");
                        System.out.println("");
                        System.out.print("‖█ ATRIBUTO: ");
                        atr = teclado.nextLine();
                        System.out.print("‖█ TIPO DO ATRIBUTO " + atr.toUpperCase() + ": ");
                        tipo = teclado.nextLine();
                        lista.put(atr.toUpperCase(), tipo.toUpperCase());
                        System.out.print((char)27 +"[34;43m █ DESEJA INSERIR MAIS ATRIBUTOS? (s/n) █ : "+(char)27 +"[0m");

                        //verifica se o usuario quer inserir mais atributos na tabela
                        control = teclado.nextLine();
                        if (control.equals("n")) {
                            inserindo = false;

                        }
                    }
                    System.out.println("_____________________________________");
                    System.out.println();
                    tab = new Tabela(nome.toUpperCase(), lista);
                    tabelas.add(tab);
                    break;

                case 2:
                    rodando = false;
                    break;

                default:
                    System.out.println(" => " + option + " não é valido");

            }

        }

        //
    
       
        salvaNoArquivo(tabelas);
        imprime();
        

    }
    
    public static void salvaNoArquivo(List<Tabela> tab) throws Exception{
        DataOutputStream out = null;
        
           for (Tabela t : tab) {
               System.out.println("TESTEEEEE: "+t.get(t));
            out.writeUTF(t.get(t));
        }
           try {
            System.out.println("Abrindo catalogo...");
            //passando true no FileOutputStrem, novos registros são adicionados ao arquivo, caso ele já exista
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("catalogo.dat", true)));
            System.out.println("Gravando dados no arquivo...");
         
                  
        } finally {
            System.out.println("Fechando catalogo ...");
            if (out != null)
                out.close();
        }
    }
    
    public static void imprime() throws IOException{
        DataInputStream in = null;
        //Zera a lista de funcionários
        
        try {
            System.out.println("Abrindo arquivo para leitura...");
            in = new DataInputStream(new BufferedInputStream(new FileInputStream("catalogo.dat")));
            System.out.println("Lendo...");

            while (true) {
                System.out.println(in.readUTF());
         
                System.out.println();                
            }

        } catch (EOFException e) {
            //arquivo terminou
        } finally {
            System.out.println("Fechando arquivo...");
            if (in != null)
                in.close();
        }
    }

}
