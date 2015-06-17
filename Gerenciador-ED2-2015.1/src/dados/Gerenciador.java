package dados;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.InputMismatchException;
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
        //int option;

        //String nome, atr, tipo, control;
        Scanner teclado = new Scanner(System.in);

        while (rodando) {

            try {
                //mostra as opções do programa
                menu();

                //Input do teclado
                int option = teclado.nextInt();
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
                        tab.nome = nome;
                        mostrarAtributos(nome);
                        break;

                    case 4:
                        System.out.println("Digite o nome da tabela que deseja excluir do catalogo");
                        String n = teclado.next();
                        excluirTabela(n.toUpperCase());
                        break;
                    case 5:
                        System.out.println("Digite o nome da tabela que deseja inserir o registro");
                        n = teclado.next();
                        inserirRegistros(n.toUpperCase(), teclado);
                        break;

                    case 6:
                        System.out.println("Digite o nome da tabela que deseja mostrar os registro");
                        n = teclado.next();
                        mostrarRegistros(n);
                        break;

                    default:
                        System.out.println(" => " + option + " não é valido");

                }
            } catch (EOFException e) {
                return;
            } catch (InputMismatchException f) {
                System.out.println(" ==>  Entrada inválida ");
                return;
            } catch (RuntimeException g) {
                System.out.println(" ==> Entrada inválida");
                return;
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
        System.out.println("‖█ " + (char) 27 + "[36m 4 - EXCLUIR TABELA " + (char) 27 + "[0m");
        System.out.println("‖█ " + (char) 27 + "[36m 5 - INSERIR REGISTROS " + (char) 27 + "[0m");
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
        String nomeTabela, atr, tipo, yesOrNo, chave, tipoChave = null;
        int option;
        boolean inserindo;
        System.out.println("_____________________________________");
        System.out.print("‖█ NOME DA TABELA:");
        nomeTabela = teclado.nextLine();

        //pega o atributo chave da tabela
        System.out.println("‖█ O ATRIBUTO CHAVE DEVE SEMPRE SER UM INTEIRO!");
        System.out.print("‖█ ATRIBUTO CHAVE (NO MÁXIMO 18 CARACTERES ): ");
        chave = teclado.nextLine();

        //garante tamanho do atributo
        if (chave.length() > 18) {
            boolean verdade = true;
            while (verdade) {
                System.out.println((char) 27 + "[34;43m █ ATRIBUTO MUITO GRANTE DIGITE NOVAMENTE █ : " + (char) 27 + "[0m");
                System.out.println("‖█ ATRIBUTO: ");
                chave = teclado.nextLine();

                if (chave.length() <= 18) {
                    verdade = false;
                }
            }
        }

        //preenche atributo com espaços vazios caso seja menor que 20 caracteres
        if (chave.length() < 18) {
            int espacos = 18 - chave.length();

            for (int i = 0; i < espacos; i++) {
                chave = chave + " ";
            }
        }

        //teste 
        System.out.println("tamanho do atributo:  " + chave.length());

        test:
        System.out.println("");
        System.out.println((char) 27 + "[33m‖█ ESCOLHA O TIPO DO ATRIBUTO:  " + chave.toUpperCase() + "█" + (char) 27 + "[0m");
        System.out.println("‖█ " + (char) 27 + "[36m 1 - INTEGER " + (char) 27 + "[0m");
        System.out.println((char) 27 + "[33m⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻" + (char) 27 + "[0m");
        System.out.print("===> ");

        //ESCOLHE O TIPO PRE DEFINIDO
        option = teclado.nextInt();
        System.out.println((char) 27 + "[36m");
        teclado.nextLine();
        System.out.println("");

        //Os campos do registro são 1 Int (4 bytes) + 1 String de 8 caracteres (10 bytes) = 14 bytes 
        RandomAccessFile arquivo = new RandomAccessFile(new File("tipo.dat"), "r");
        switch (option) {
            case 1:
                arquivo.seek(0);
                tipoChave = arquivo.readUTF();
                break;
            default:
                System.out.println(" => " + option + " não é válido");
                return;
        }

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
            System.out.print("‖█ ATRIBUTO (NO MÁXIMO 18 CARACTERES): ");
            atr = teclado.nextLine();

            //garante tamanho do atributo
            if (atr.length() > 18) {
                boolean verdade = true;
                while (verdade) {
                    System.out.println((char) 27 + "[34;43m █ ATRIBUTO MUITO GRANTE DIGITE NOVAMENTE █ : " + (char) 27 + "[0m");
                    System.out.println("‖█ ATRIBUTO: ");
                    atr = teclado.nextLine();

                    if (atr.length() <= 18) {
                        verdade = false;
                    }
                }
            }

            //preenche atributo com espaços vazios caso seja menor que 20 caracteres
            if (atr.length() < 18) {
                int espacos = 18 - atr.length();

                for (int i = 0; i < espacos; i++) {
                    atr = atr + " ";
                }
            }

            System.out.println((char) 27 + "[33m‖█ ESCOLHA O TIPO DO ATRIBUTO:  " + chave.toUpperCase() + "█" + (char) 27 + "[0m");
            System.out.println("‖█ " + (char) 27 + "[36m 1 - INTEGER " + (char) 27 + "[0m");
            System.out.println("‖█ " + (char) 27 + "[36m 2 - FLOAT " + (char) 27 + "[0m");
            System.out.println("‖█ " + (char) 27 + "[36m 3 - DOUBLE " + (char) 27 + "[0m");
            System.out.println("‖█ " + (char) 27 + "[36m 4 - CHAR " + (char) 27 + "[0m");
            System.out.println("‖█ " + (char) 27 + "[36m 5 - BOOLEAN " + (char) 27 + "[0m");
            System.out.println("‖█ " + (char) 27 + "[36m 6 - DATE " + (char) 27 + "[0m");
            System.out.println("‖█ " + (char) 27 + "[36m 7 - STRING " + (char) 27 + "[0m");
            System.out.println((char) 27 + "[33m⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻" + (char) 27 + "[0m");
            System.out.print("===> ");

            //ESCOLHE O TIPO PRE DEFINIDO
            option = teclado.nextInt();
            System.out.println((char) 27 + "[36m");
            teclado.nextLine();
            System.out.println("");

            //volta com o ponteiro para o começo do arquivo 
            arquivo.seek(0);

            //Os campos do registro são 1 Int (4 bytes) + 1 String de 8 caracteres (10 bytes) = 14 bytes 
            switch (option) {
                case 1:
                    arquivo.seek(0);
                    tipo = arquivo.readUTF();
                    break;

                case 2:
                    arquivo.seek(14);
                    tipo = arquivo.readUTF();
                    break;

                case 3:
                    arquivo.seek(28);
                    tipo = arquivo.readUTF();
                    break;

                case 4:
                    arquivo.seek(42);
                    tipo = arquivo.readUTF();
                    break;
                case 5:
                    arquivo.seek(56);
                    tipo = arquivo.readUTF();
                    break;

                case 6:
                    arquivo.seek(70);
                    tipo = arquivo.readUTF();
                    break;

                case 7:
                    arquivo.seek(84);
                    tipo = arquivo.readUTF();
                    break;

                default:
                    System.out.println(" => " + option + " não é valido");
                    return;

            }

            lista.put(atr.toUpperCase(), tipo.toUpperCase());
            System.out.print((char) 27 + "[34;43m █ DESEJA INSERIR MAIS ATRIBUTOS? (s/n) █ : " + (char) 27 + "[0m");

            //verifica se o usuario quer inserir mais atributos na tabela
            yesOrNo = teclado.nextLine();
            //mostrarAtributos(nomeTabela);

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
        RandomAccessFile arquivoHash = null;

        String nomeArquivoHash = nomeTabela + "_hash.dat";
        try {
            arquivoHash = new RandomAccessFile(nomeArquivoHash, "rw");

            EncadeamentoExterior encadeamento = new EncadeamentoExterior();
            encadeamento.criaHash(arquivoHash);
        } catch (Exception e) {

        } finally {
            arquivoHash.close();
        }
    }

    public static void mostrarAtributos(String nomeTabela) throws FileNotFoundException, IOException {
        DataInputStream in = null;
        try {
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeTabela.toLowerCase() + "_atributos.dat")));

            String alinhar = "| %-20s | %-20s |%n";
            String alinhar2 = "| %-40s    |%n";
            String alinhar3 = "| %-20s | %-20s |";
            System.out.format("+---------------------------------------------+%n");
            System.out.printf(alinhar2, "TABELA " + nomeTabela.toUpperCase());
            System.out.format("+---------------------------------------------+%n");
            System.out.printf("|       Atributo       |          Tipo        |%n");
            System.out.format("+----------------------+----------------------+%n");

            System.out.format(alinhar3, in.readUTF().toUpperCase(), in.readUTF().toUpperCase());
            System.out.format((char) 27 + "[0;41m C H A V E %n" + (char) 27 + "[0m");
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

    private static void excluirTabela(String tabelaExcluir) throws Exception {

        DataInputStream in = null;
        List<String> nomeTabelas = new ArrayList<String>();

        try {
            //teste para ver se a tabela existe
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(tabelaExcluir + "_atributos.dat")));
            in.close();

            //deleta arquivos de atributos e registros 
            String deleta = tabelaExcluir.toLowerCase() + "_atributos.dat";
            deletaArquivo(deleta);

            deleta = tabelaExcluir.toLowerCase() + "_registros.dat";
            deletaArquivo(deleta);

            deleta = tabelaExcluir.toLowerCase() + "_hash.dat";
            deletaArquivo(deleta);

            in = new DataInputStream(new BufferedInputStream(new FileInputStream("tabelas.dat")));

            while (true) {
                nomeTabelas.add(in.readUTF());
            }

        } catch (EOFException e) {

            in.close();

            deletaArquivo("tabelas.dat");

            //salava um novo arquivo tabelas.dat atualizado
            for (int i = 0; i < nomeTabelas.size(); i++) {
                if (nomeTabelas.get(i).equalsIgnoreCase(tabelaExcluir)) {
                    i++;
                }

                if (i < nomeTabelas.size()) {
                    salvaTabela(nomeTabelas.get(i));
                }

            }

            //exibe mensagem de sucesso
            System.out.println("TABELA " + tabelaExcluir + " EXCLUIDA");

        } catch (FileNotFoundException f) {
            System.out.println("Esta tabela nao existe.");

        } finally {
            System.out.println("Fechando arquivo  tabelas.dat ...");
            if (in != null) {
                in.close();
            }
        }

    }

    public static void deletaArquivo(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }

    private static void mostrarRegistros(String tabela) throws FileNotFoundException, IOException {

        RandomAccessFile arquivo;
        List<String> tipoAtributos = new ArrayList<String>();
        int i = 1;// controle do seek

        try {
            //teste para ver se a tabela existe
            //in = new DataInputStream(new BufferedInputStream(new FileInputStream(tabela+"_atributos.dat")));
            arquivo = new RandomAccessFile(new File(tabela + "_atributos.dat"), "r");

            //ler a tabela atributos 
            // tamanho do registro 20 ( atributo ) + 10 ( tipo) = 30;
            //mas só quero o tipo 30*numReg-10
            arquivo.seek(20);
            while (true) {
                tipoAtributos.add(arquivo.readUTF());
                i++;
                arquivo.seek(30 * i - 10);

            }

        } catch (EOFException e) {
            //ler regitros
            arquivo = new RandomAccessFile(new File(tabela.toLowerCase() + "_registros.dat"), "r");

            try {
                while (true) {
                    for (i = 0; i < tipoAtributos.size(); i++) {
                        if (tipoAtributos.get(i).equalsIgnoreCase("integer ")) {
                            System.out.println(arquivo.readUTF());
                        }
                        if (tipoAtributos.get(i).equalsIgnoreCase("float   ")) {
                            System.out.println(arquivo.readFloat());
                        }
                        if (tipoAtributos.get(i).equalsIgnoreCase("double  ")) {
                            System.out.println(arquivo.readFloat());
                        }
                        if (tipoAtributos.get(i).equalsIgnoreCase("char    ")) {
                            System.out.println(arquivo.readUTF());
                        }
                        if (tipoAtributos.get(i).equalsIgnoreCase("boolean ")) {
                            System.out.println(arquivo.readBoolean());
                        }
                        if (tipoAtributos.get(i).equalsIgnoreCase("date    ")) {
                            System.out.println(arquivo.readUTF());
                        }
                        if (tipoAtributos.get(i).equalsIgnoreCase("string  ")) {
                            System.out.println(arquivo.readUTF());
                        }
                    }
                }
            } catch (EOFException r) {
                arquivo.close();
                System.out.println("fechando tabela: " + tabela);
            }

        } finally {

        }

    }

    private static void modificarRegistros(Tabela tab, List<Tabela> tabelas, Scanner teclado, HashMap<String, String> lista) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void inserirRegistros(String nomeTabela, Scanner teclado) throws IOException {

        String nomeArquivoHash = nomeTabela + "_hash.dat";
        String nomeArquivoDados = nomeTabela + "_registros.dat";

        CompartimentoHash compartimento = new CompartimentoHash(-1);

        DataInputStream in = null;
        RandomAccessFile arquivoHash = null;
        RandomAccessFile arquivoDados = null;
        ArrayList<String> nomes = new ArrayList<>();
        ArrayList<String> tipos = new ArrayList<>();

        Registro registro = new Registro();

        try {
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeTabela.toLowerCase() + "_atributos.dat")));

            arquivoHash = new RandomAccessFile(nomeArquivoHash, "rw");
            arquivoDados = new RandomAccessFile(nomeArquivoDados, "rw");
//            for (int i = 0; i < 7; i++) {
//                compartimento.salva(arquivoHash);
//            }
            while (true) {
                nomes.add(in.readUTF());
                tipos.add(in.readUTF());
            }

        } catch (Exception e) {
//            System.out.println("Digite o valor da chave do registro");
//            registro.chave = teclado.nextInt();
//            Inteiro inteiro = new Inteiro(registro.chave);
//            registro.atributos.add(0, inteiro);

            for (int i = 0; i < tipos.size(); i++) {
                System.out.println("Digite o valor do atributo " + nomes.get(i) + " da tabela " + nomeTabela);
                if (tipos.get(i).equalsIgnoreCase("integer ")) {
                    Inteiro inteiro = new Inteiro(teclado.nextInt());
                    registro.atributos.add(inteiro);
                    registro.tamanhoRegistro += Integer.BYTES;
                }
                if (tipos.get(i).equalsIgnoreCase("float   ")) {
                    PontoFlutuante pf = new PontoFlutuante(teclado.nextFloat());
                    registro.atributos.add(pf);
                    registro.tamanhoRegistro += Float.BYTES;
                }
                if (tipos.get(i).equalsIgnoreCase("double  ")) {
                    PontoFlutuanteDuplo pfd = new PontoFlutuanteDuplo(teclado.nextDouble());
                    registro.atributos.add(pfd);
                    registro.tamanhoRegistro += Double.BYTES;
                }
                if (tipos.get(i).equalsIgnoreCase("char    ")) {
                    Palavra str = new Palavra(teclado.next());
                    registro.atributos.add(str);
                    registro.tamanhoRegistro += 2;
                }
                if (tipos.get(i).equalsIgnoreCase("boolean ")) {
                    Decisao bool = new Decisao(teclado.nextBoolean());
                    registro.atributos.add(bool);
                    registro.tamanhoRegistro += 2;
                }
                if (tipos.get(i).equalsIgnoreCase("date    ")) {
                    System.out.println("Digite a data no formato: MM/DD/AAAA");
                    Date d = new Date(teclado.next());
                    Data data = new Data(d);
                    registro.atributos.add(data);
                    registro.tamanhoRegistro += 12;
                }
                if (tipos.get(i).equalsIgnoreCase("string  ")) {
                    Palavra str = new Palavra(teclado.next());
                    registro.atributos.add(str);
                    registro.tamanhoRegistro += 20;
                }

            }
            try {
                EncadeamentoExterior encadeamento = new EncadeamentoExterior();

                int nRegistros = encadeamento.numeroRegistros(nomeArquivoHash, false, false);

                System.out.println(nRegistros);
                encadeamento.insere(registro.chave, registro.atributos, nomeArquivoHash, nomeArquivoDados, nomeTabela, nRegistros);
            } catch (Exception f) {

            } finally {

            }

        } finally {
            arquivoHash.close();
            arquivoDados.close();
        }
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
