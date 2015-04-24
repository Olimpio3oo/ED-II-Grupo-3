/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadastrobanco;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Victor
 */
public class ContaCorrente {
    private int codConta;
    private int codAgencia;
    private double saldo;
    
    public ContaCorrente(){
        
    }
    
    public ContaCorrente(int cod, int codAgencia, double saldo){
        this.codConta = cod;
        this.codAgencia = codAgencia;
        this.saldo = saldo;
    }

    public void gravarArquivo(DataOutputStream out) throws IOException{
        out.writeInt(codConta);
        out.writeInt(codAgencia);
        out.writeDouble(saldo);
        System.out.println("Gravação da conta corrente de código " + codConta + " realizada com sucesso");
    }
    
    public void lerArquivo(DataInputStream in) throws IOException{
        codConta = in.readInt();
        codAgencia = in.readInt();
        saldo = in.readDouble();
    }
    
    public int getCodConta() {
        return codConta;
    }
    
    public void imprimir(){
        System.out.println("----------------");
        System.out.println(codConta);
        System.out.println(codAgencia);
        System.out.println(saldo);
    }

    public int getCodAgencia() {
        return codAgencia;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setCodConta(int codConta) {
        this.codConta = codConta;
    }

    public void setCodAgencia(int codAgencia) {
        this.codAgencia = codAgencia;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    
}
