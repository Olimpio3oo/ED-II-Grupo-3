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
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ED2 - GRUPO 3 | 2015.1
 */
public class Gerenciador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        //mostra um cabeçalho
        header();

        //Inicializa variavéis 
        boolean rodando = true;
        boolean inserindo = true;
        Tabela tab = new Tabela();
        HashMap<String, String> lista = new HashMap<>();
        List<Tabela> tabelas = new ArrayList<>();
        int option;
        String nome, atr, tipo, control;
        Scanner teclado = new Scanner(System.in);

        while (rodando) {
            //mostra as opções do programa
            menu();

            //Input do teclado
            option = teclado.nextInt();
            System.out.println((char) 27 + "[36m");
            teclado.nextLine();
            System.out.println("");
            switch (option) {
                case 0:
                    rodando = false;
                    break;

                case 1:
                    criaTabela(tab, tabelas, teclado, lista);
                    break;
                    
                case 2:
                    modificarTabela(tab, tabelas, teclado, lista);
                    break;
                    
                case 3:
                    mostrarTabela(tab, tabelas, teclado, lista);
                    break;
                    
                case 4:
                    inserirRegistros(tab, tabelas, teclado, lista);
                    break;
                case 5:
                    
                    modificarRegistros(tab, tabelas, teclado, lista);
                    break;
                case 6:
                    
                    mostrarRegistros(tab, tabelas, teclado, lista);
                    break;

                default:
                    System.out.println(" => " + option + " não é valido");

            }

        }

        //
        //salvaNoArquivo(tabelas);
        //imprime();

    }

    public static void salvaNoArquivo(List<Tabela> tab) throws Exception {
        DataOutputStream out = null;

        for (Tabela t : tab) {
    
            out.writeUTF(t.get(t));
        }
        try {
            System.out.println("Abrindo catalogo...");
            //passando true no FileOutputStrem, novos registros são adicionados ao arquivo, caso ele já exista
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("catalogo.dat", true)));
            System.out.println("Gravando dados no arquivo...");

        } finally {
            System.out.println("Fechando catalogo ...");
            if (out != null) {
                out.close();
            }
        }
    }

    public static void imprime() throws IOException {
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
            if (in != null) {
                in.close();
            }
        }
    }

    public static void menu() {
        System.out.println("");
        System.out.println((char) 27 + "[33m‖█ MENU ██████████████" + (char) 27 + "[0m");
        System.out.println("‖█ " + (char) 27 + "[36m 1 - CRIAR TABELA " + (char) 27 + "[0m");
        System.out.println("‖█ " + (char) 27 + "[36m 2 - MODIFICAR TABELA " + (char) 27 + "[0m");
        System.out.println("‖█ " + (char) 27 + "[36m 3 - MOSTRAR TABELA " + (char) 27 + "[0m");
        System.out.println("‖█ " + (char) 27 + "[36m 4 - INSERIR REGISTROS " + (char) 27 + "[0m");
        System.out.println("‖█ " + (char) 27 + "[36m 5 - MODIFICAR REGISTROS " + (char) 27 + "[0m");
        System.out.println("‖█ " + (char) 27 + "[36m 6 - MOSTRAR REGISTROS" + (char) 27 + "[0m");
        System.out.println("‖█ " + (char) 27 + "[36m 0 - SAIR   ");
        System.out.println((char) 27 + "[33m⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻" + (char) 27 + "[0m");
        System.out.print("===> ");
    }

    public static void header() {
        System.out.println((char) 27 + "[33m██████████████████████");
        System.out.println((char) 27 + "[33m██████████████████████");
        System.out.println((char) 27 + "[33m████ GRUPO 3 - ED2 2015.1█████");
        System.out.println((char) 27 + "[33m██████████████████████");
        System.out.println((char) 27 + "[33m██████████████████████" + (char) 27 + "[0m");
    }

    public static void criaTabela(Tabela tab, List<Tabela> tabelas, Scanner teclado, HashMap lista) throws JSONException {
        String nome, atr, tipo, control;
        boolean inserindo;
        System.out.println("_____________________________________");
        System.out.print("‖█ NOME DA TABELA:");
        nome = teclado.nextLine();
        inserindo = true;
        while (inserindo) {
            System.out.print("——————| " + nome.toUpperCase() + " |——————");
            System.out.println("");
            System.out.print("‖█ ATRIBUTO: ");
            atr = teclado.nextLine();
            System.out.print("‖█ TIPO DO ATRIBUTO " + atr.toUpperCase() + ": ");
            tipo = teclado.nextLine();
            lista.put(atr.toUpperCase(), tipo.toUpperCase());
            System.out.print((char) 27 + "[34;43m █ DESEJA INSERIR MAIS ATRIBUTOS? (s/n) █ : " + (char) 27 + "[0m");
            
                    
            //verifica se o usuario quer inserir mais atributos na tabela
            control = teclado.nextLine();
            
            JSONObject novaTab = new JSONObject();
            novaTab.put("nome", nome.toUpperCase());
                    
            novaTab.put("atributos", lista);
            System.out.println(novaTab);
            System.out.println("TABELA " + novaTab.get("nome"));
            System.out.println("ATRIBUTOS" + novaTab.get("atributos"));
            
            if (control.equals("n")) {
                inserindo = false;

            }
        }
        System.out.println("_____________________________________");
        System.out.println();
        tab = new Tabela(nome.toUpperCase(), lista);
        tabelas.add(tab);
    }

    private static void mostrarTabela(Tabela tab, List<Tabela> tabelas, Scanner teclado, HashMap<String, String> lista) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void modificarTabela(Tabela tab, List<Tabela> tabelas, Scanner teclado, HashMap<String, String> lista) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void mostrarRegistros(Tabela tab, List<Tabela> tabelas, Scanner teclado, HashMap<String, String> lista) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void modificarRegistros(Tabela tab, List<Tabela> tabelas, Scanner teclado, HashMap<String, String> lista) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void inserirRegistros(Tabela tab, List<Tabela> tabelas, Scanner teclado, HashMap<String, String> lista) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
