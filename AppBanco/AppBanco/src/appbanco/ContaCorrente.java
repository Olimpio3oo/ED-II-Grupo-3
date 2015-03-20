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
public class ContaCorrente {
    public int codConta;
    public int codAgencia;
    public double saldo;
    
     /**
     *
     * Construtor vazio
     */
    public ContaCorrente() {
    }
    
    /**
     *
     * Construtor com todos os atributos do objeto
     */
    public ContaCorrente(int codConta, int codAgencia, double saldo) {
        this.codConta = codConta;
        this.codAgencia = codAgencia;
        this.saldo = saldo;
      
    }

    /**
     *
     * Salva um Funcionário no arquivo, na posição atual do cursor
     */
    public void salva(DataOutputStream out) throws IOException {
        out.writeInt(codConta);
        out.writeInt(codAgencia);
        out.writeDouble(saldo);
        System.out.println("Gravação do conta corrente de código " + codConta + " realizada com sucesso");
    }

    /**
     *
     * Lê um Funcionário do arquivo, na posição atual do cursor
     */
    public void le(DataInputStream in) throws IOException {
        codConta = in.readInt();
        codAgencia = in.readInt();
        saldo = in.readDouble();
    }

    public void imprime() {
        System.out.println("---------------------------");
        System.out.println(this.codConta);
        System.out.println(this.codAgencia);
        System.out.println(this.saldo);
    }
    
}
