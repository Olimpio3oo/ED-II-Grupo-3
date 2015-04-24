/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package appbanco;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author BiaHome
 */
public class Agencia {
    public int cod;
    public String nome;
    public String gerente;
    
     /**
     *
     * Construtor vazio
     */
    public Agencia() {
    }
    
    /**
     *
     * Construtor com todos os atributos do objeto
     */
    public Agencia(int cod, String nome, String gerente) {
        this.cod = cod;
        this.nome = nome;
        this.gerente = gerente;
      
    }

    /**
     *
     * Salva um Funcionário no arquivo, na posição atual do cursor
     */
    public void salva(DataOutputStream out) throws IOException {
        out.writeInt(cod);
        out.writeUTF(nome);
        out.writeUTF(gerente);
        System.out.println("Gravação da agência de código " + cod + " realizada com sucesso");
    }

    /**
     *
     * Lê um Funcionário do arquivo, na posição atual do cursor
     */
    public void le(DataInputStream in) throws IOException {
        cod = in.readInt();
        nome = in.readUTF();
        gerente = in.readUTF();
    }

    public void imprime() {
        System.out.println("---------------------------");
        System.out.println(this.cod);
        System.out.println(this.nome);
        System.out.println(this.gerente);
    }
}
