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
                    case 7:
                        System.out.println("Digite o nome da tabela onde o registro esta");
                        n = teclado.next();
                        excluirRegistro(n, teclado);
                        break;
                    case 8:
                        System.out.println("Digite o nome da tabela onde o registro esta");
                        n = teclado.next();
                        buscaRegistro(n, teclado);
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
        System.out.println("‖█ " + (char) 27 + "[36m 7 - EXCLUIR REGISTRO" + (char) 27 + "[0m");
        System.out.println("‖█ " + (char) 27 + "[36m 8 - BUSCAR REGISTRO" + (char) 27 + "[0m");
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

    private static void mostrarRegistros(String nomeTabela) throws FileNotFoundException, IOException {

        RandomAccessFile arquivoRegistros = null;
        EncadeamentoExterior ecadeamento = new EncadeamentoExterior();
        int qtdRegistros = ecadeamento.numeroRegistros(nomeTabela); //recuperando numero de registros contidos na tabela
        Registro registroAtual = new Registro(); //objeto para armazenar e imprimir os registros
        int tamRegistro = Registro.tamanhoReg(nomeTabela); //tamanho do registro para calcular seek

        System.out.format("+---------------------------+%n");
        System.out.printf("|████   TABELAS    ████|%n");
        System.out.format("+---------------------------+%n");

        try {
            arquivoRegistros = new RandomAccessFile(new File(nomeTabela.toLowerCase() + "_registros.dat"), "r");

            for (int i = 0; i < qtdRegistros; i++) {
                arquivoRegistros.seek(i * tamRegistro);
                registroAtual = Registro.le(arquivoRegistros, nomeTabela); //registro atual

                //escrevendo registros
                if (registroAtual.flag != true) {
                    System.out.println("Chave: " + registroAtual.chave);

                    System.out.println("Atributos: ");

                    for (int x = 0; x < registroAtual.atributos.size(); x++) {
                        System.out.println(registroAtual.atributos.get(x));
                    }

                    System.out.println("Flag: " + registroAtual.flag);
                    System.out.println("Próximo: " + registroAtual.prox);
                    System.out.println("-------------");
                }

            }

        } catch (Exception e) {
            System.out.println("----NÃO EXISTE NENHUM REGISTRO!----");
        } finally {
            arquivoRegistros.close();
            System.out.println("-------FIM DOS REGISTROS-------");
        }

    }

    private static void modificarRegistros(Tabela tab, List<Tabela> tabelas, Scanner teclado, HashMap<String, String> lista) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void buscaRegistro(String nomeTabela, Scanner teclado) throws IOException, Exception {

        System.out.println("PREENCHA OS ATRIBUTOS QUE DESEJA BUSCAR");

        //preenchedo os atributos
        Registro registro = new Registro(); //novo registro 
        ArrayList<String> tipos = new ArrayList<>();
        ArrayList<String> nomes = new ArrayList<>();
        DataInputStream in = null;
        RandomAccessFile arquivoDados = null;
        String resposta;
        int contChave = 0; // vai controlar se a chave foi escolhida como parametro
        Registro retorno = null;
        String nomeArquivoDados = nomeTabela + "_registros.dat";

        try {
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeTabela.toLowerCase() + "_atributos.dat")));

            arquivoDados = new RandomAccessFile(nomeTabela + "_registros.dat", "rw");
//            for (int i = 0; i < 7; i++) {
//                compartimento.salva(arquivoHash);
//            }
            while (true) {
                nomes.add(in.readUTF());
                tipos.add(in.readUTF());
            }

        } catch (Exception e) {
            //System.out.println("Digite o valor da chave do registro");
            //registro.chave = teclado.nextInt();
            //Inteiro inteiro = new Inteiro(registro.chave);
            //registro.atributos.add(0, inteiro);

            //chave não pode entrar em atributos
            for (int i = 0; i < tipos.size(); i++) {

                System.out.println("Digite deseja buscar o atributo " + nomes.get(i) + " s para sim e n para não");
                resposta = teclado.next();

                if (resposta.equalsIgnoreCase("s")) {
                    System.out.println("Digite o valor de busca do atributo " + nomes.get(i));
                    if (tipos.get(i).equalsIgnoreCase("integer ")) {
                        Inteiro inteiro = new Inteiro(teclado.nextInt());
                        registro.atributos.add(inteiro);
                        registro.tamanhoRegistro += Integer.BYTES;

                        if (i == 0) {
                            contChave++; //vendo se a chave foi escolhida como parametro
                        }
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
                        registro.tamanhoRegistro += 1;
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

            }
            EncadeamentoExterior encadeamento = new EncadeamentoExterior();
            int qtdRegistros = encadeamento.numeroRegistros(nomeTabela);
            int cont = 0;
            int tam = Registro.tamanhoReg(nomeTabela);

            if (contChave == 1) {
                int j = 0;
                Inteiro inteiro = (Inteiro) registro.atributos.get(0);
                registro.chave = inteiro.inteiro;
                //arquivoDados.seek(0);
                while (retorno == null && cont < qtdRegistros) {
                    arquivoDados.seek(j * tam);
                    retorno = encadeamento.buscaRegistro(registro, arquivoDados, nomeTabela);
                    cont++;
                    j++;
                }

                //escrevendo registro
                System.out.println("-----RETORNO DA BUSCA--------");
                System.out.println("Chave: " + retorno.chave);

                System.out.println("Atributos: ");

                for (int x = 0; x < retorno.atributos.size(); x++) {
                    System.out.println(retorno.atributos.get(x));
                }

            } else {

                arquivoDados.seek(0); //coloca no começo do arquivo

                for (int j = 0; j < qtdRegistros; j++) { //retorna quantos retornos possíves
                    arquivoDados.seek(j * tam);
                    
                    if(registro.chave == 0){
                        registro.chave = -10;
                    }
                    
                    retorno = encadeamento.buscaRegistro(registro, arquivoDados, nomeTabela);

                    if (retorno != null) {
                        //escrevendo registro
                        System.out.println("-----RETORNO DA BUSCA--------");
                        System.out.println("Chave: " + retorno.chave);

                        System.out.println("Atributos: ");

                        for (int x = 0; x < retorno.atributos.size(); x++) {
                            System.out.println(retorno.atributos.get(x));
                        }
                    }

                }

            }
        }
    }

    private static void inserirRegistros(String nomeTabela, Scanner teclado) throws IOException {

        String nomeArquivoHash = nomeTabela + "_hash.dat";
        String nomeArquivoDados = nomeTabela + "_registros.dat";

        //lerHash(nomeArquivoHash);
        CompartimentoHash compartimento = new CompartimentoHash(-1);

        DataInputStream in = null;
        RandomAccessFile arquivoHash = null;
        RandomAccessFile arquivoDados = null;
        ArrayList<String> nomes = new ArrayList<>();
        ArrayList<String> tipos = new ArrayList<>();

        Registro registro = new Registro(); //novo registro 

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
            //System.out.println("Digite o valor da chave do registro");
            //registro.chave = teclado.nextInt();
            //Inteiro inteiro = new Inteiro(registro.chave);
            //registro.atributos.add(0, inteiro);

            //chave não pode entrar em atributos
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
                    registro.tamanhoRegistro += 1;
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
                //registro.tamanhoRegistro += 1;
            }

            registro.tamanhoRegistro += 5; //flag + prox;
            registro.flag = false;
            registro.prox = -1;
            Inteiro inteiro = (Inteiro) registro.atributos.get(0);
            registro.chave = inteiro.inteiro;

            try {
                EncadeamentoExterior encadeamento = new EncadeamentoExterior();

                //int nRegistros = encadeamento.numeroRegistros(nomeArquivoDados, false, false);
                //System.out.println(nRegistros);
                encadeamento.insere(registro.chave, registro.atributos, nomeArquivoHash, nomeArquivoDados, nomeTabela);

            } catch (Exception f) {

            } finally {

            }

        } finally {
            arquivoHash.close();
            arquivoDados.close();
        }
    }

    private static void excluirRegistro(String nomeTabela, Scanner teclado) throws IOException {
        System.out.println("Digite a chave do registro");

        int chave = teclado.nextInt();
        int retorno = -1;
        String nomeArquivoHash = nomeTabela + "_hash.dat";
        String nomeArquivoDados = nomeTabela + "_registros.dat";

        RandomAccessFile arquivoHash = null;
        RandomAccessFile arquivoDados = null;

        try {
            arquivoHash = new RandomAccessFile(nomeArquivoHash, "rw");
            arquivoDados = new RandomAccessFile(nomeArquivoDados, "rw");

            EncadeamentoExterior encadeamento = new EncadeamentoExterior();
            retorno = encadeamento.exclui(chave, nomeArquivoHash, nomeArquivoDados, nomeTabela);

            if (retorno == -1) {
                System.out.println("Chave não encontrada");
            } else {
                System.out.println("Registro de endereço " + retorno + " removido");
            }

        } catch (Exception e) {
            System.out.println("TABELA INEXISTENTE!");

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

    public static void lerHash(String nomeTabelaHash) throws FileNotFoundException, IOException {
        DataInputStream in = null;
        //Zera a lista de funcionários

        try {
            System.out.println("Abrindo arquivo para leitura...");
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(nomeTabelaHash)));
            System.out.println("Lendo...");

            while (true) {
                System.out.println(in.readInt());

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

}
