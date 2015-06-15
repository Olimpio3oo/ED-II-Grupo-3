package dados;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import static javafx.application.Platform.exit;
import javax.swing.JOptionPane;
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

        //Inicializa variavéis ;
        boolean rodando = true;
        boolean inserindo = true;
        Tabela tab = new Tabela();
        LinkedHashMap<String, String> lista = new LinkedHashMap<>();
        List<Tabela> tabelas = new ArrayList<>();
        int option;
        //String nome, atr, tipo, control;
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
                    mostrarTabelas();
                    break;

                case 3:
                    System.out.println("DIGITE O NOME DA TABELA: ");
                    String nome = teclado.nextLine();
                    mostrarAtributos(nome);
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

    public static void salvaTabela(String nomeTabela) throws Exception {
        DataOutputStream out = null;

//        for (Tabela t : tab) {
//    
//            out.writeUTF(t.get(t));
//        }
        try {
            System.out.println("Abrindo tabelas.dat...");
            //passando true no FileOutputStrem, novos registros são adicionados ao arquivo, caso ele já exista
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("tabelas.dat", true)));

            out.writeUTF(nomeTabela);

        } finally {
            System.out.println("Fechando tabelas.dat ...");
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
        System.out.println("‖█ " + (char) 27 + "[36m 2 - MOSTRAR TABELAS " + (char) 27 + "[0m");
        System.out.println("‖█ " + (char) 27 + "[36m 3 - MOSTRAR ATRIBUTOS " + (char) 27 + "[0m");
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

    public static void criaTabela(Tabela tab, List<Tabela> tabelas, Scanner teclado, LinkedHashMap<String, String> lista) throws JSONException, Exception {
        lista = new LinkedHashMap<String, String>();
        String nomeTabela, atr, tipo, yesOrNo, chave, tipoChave;
        boolean inserindo;
        System.out.println("_____________________________________");
        System.out.print("‖█ NOME DA TABELA:");
        nomeTabela = teclado.nextLine();

        //pega o atributo chave da tabela
        System.out.print("‖█ ATRIBUTO CHAVE: ");
        chave = teclado.nextLine();
        System.out.print("‖█ TIPO DO ATRIBUTO " + chave.toUpperCase() + ": ");
        tipoChave = teclado.nextLine();
        lista.put(chave.toUpperCase(), tipoChave.toUpperCase());
        inserindo = true;
        System.out.print((char) 27 + "[34;43m █ DESEJA INSERIR MAIS ATRIBUTOS? (s/n) █ : " + (char) 27 + "[0m");
        yesOrNo = teclado.nextLine();
        if (yesOrNo.equals("n")) {
            inserindo = false;

        }

        while (inserindo) {
            System.out.print("——————| " + nomeTabela.toUpperCase() + " |——————");
            System.out.println("");
            System.out.print("‖█ ATRIBUTO: ");
            atr = teclado.nextLine();
            System.out.print("‖█ TIPO DO ATRIBUTO " + atr.toUpperCase() + ": ");
            tipo = teclado.nextLine();
            lista.put(atr.toUpperCase(), tipo.toUpperCase());
            System.out.print((char) 27 + "[34;43m █ DESEJA INSERIR MAIS ATRIBUTOS? (s/n) █ : " + (char) 27 + "[0m");

            //verifica se o usuario quer inserir mais atributos na tabela
            yesOrNo = teclado.nextLine();
            mostrarAtributos(nomeTabela);

            if (yesOrNo.equals("n")) {
                inserindo = false;

            }
        }
        System.out.println("_____________________________________");
        System.out.println();
        salvaTabela(nomeTabela);
        salvaAtributos(lista, nomeTabela);
        //tab = new Tabela(nomeTabela.toUpperCase(), lista);
        // tabelas.add(tab);
    }

    public static void mostrarAtributos(String nomeTabela) throws FileNotFoundException, IOException {
        DataInputStream in = null;
        try {
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeTabela.toLowerCase() + "_atributos.dat")));

            String alinhar = "| %-20s | %-20s |%n";
            String alinhar2 = "| %-40s    |%n";
            String alinhar3 = "| %-20s | %-20s |";
            System.out.format("+---------------------------------------------+%n");
            System.out.printf(alinhar2,"TABELA " + nomeTabela.toUpperCase());
            System.out.format("+---------------------------------------------+%n");
            System.out.printf("|       Atributo       |          Tipo        |%n");
            System.out.format("+----------------------+----------------------+%n");

            System.out.format(alinhar3,  in.readUTF().toUpperCase(), in.readUTF().toUpperCase());
            System.out.format((char)27 +"[0;41m C H A V E %n" +(char)27+"[0m");
            System.out.format("+----------------------+----------------------+%n");

            while (true) {
                System.out.format(alinhar, in.readUTF().toUpperCase(), in.readUTF().toUpperCase());
                System.out.format("+----------------------+----------------------+%n");
                

            }

        } catch (EOFException e) {
            //arquivo terminou
            System.out.println("FIM DE ARQUIVO");
        } catch (FileNotFoundException f) {
            System.out.println("Esta tabela nao existe.");

        } finally {
            System.out.println("Fechando arquivo" + nomeTabela.toLowerCase() + "_atributos .dat ...");
            if (in != null) {
                in.close();
            }
        }
    }

    public static void mostrarTabelas() throws FileNotFoundException, IOException {
        DataInputStream in = null;
        try {
            in = new DataInputStream(new BufferedInputStream(new FileInputStream("tabelas.dat")));

            String alinhar = "| %-25s |%n";

            System.out.format("+---------------------------+%n");
            System.out.printf("|████   TABELAS    ████|%n");
            System.out.format("+---------------------------+%n");

            //System.out.format("+-----------------+----------------------+%n");
            while (true) {
                System.out.format(alinhar, in.readUTF().toUpperCase());
                System.out.format("+---------------------------+%n");

            }

        } catch (EOFException e) {

        } catch (FileNotFoundException f) {
            System.out.println("Nenhuma Tabela existente. Crie uma");

        } finally {
            System.out.println("Fechando arquivo tabelas.dat ...");
            if (in != null) {
                in.close();
            }
        }
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

    public static void salvaAtributos(LinkedHashMap<String, String> lista, String nomeTabela) throws FileNotFoundException, IOException {
        DataOutputStream out = null;

        try {
            System.out.println("Abrindo " + nomeTabela + "_atributos.");
            //passando true no FileOutputStrem, novos registros são adicionados ao arquivo, caso ele já exista
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(nomeTabela + "_atributos.dat", true)));

            for (Entry<String, String> e : lista.entrySet()) {
                out.writeUTF(e.getKey());
                out.writeUTF(e.getValue());
            }

        } finally {
            System.out.println("Fechando catalogo ...");
            if (out != null) {
                out.close();
            }
        }
    }

}
