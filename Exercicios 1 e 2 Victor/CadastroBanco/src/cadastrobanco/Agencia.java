
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadastrobanco;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
/**
 *
 * @author Victor
 */
public class Agencia {
    private int codAgencia;
    private String nome;
    private String gerente;
    
    public Agencia(){
        
    }
    
    public Agencia(int cod, String nome, String gerente){
        this.codAgencia = cod;
        this.nome = nome;
        this.gerente = gerente;
    }
    
    public void gravarArquivo(DataOutputStream out) throws IOException{
        out.writeInt(codAgencia);
        out.writeUTF(nome);
        out.writeUTF(gerente);
        System.out.println("Gravação da agencia de código " + codAgencia + " realizada com sucesso");
    }
    
    public void lerArquivo(DataInputStream in)throws IOException{
        codAgencia = in.readInt();
        nome = in.readUTF();
        gerente = in.readUTF();
    }
    
    public void imprimir(){
        System.out.println("----------------");
        System.out.println(codAgencia);
        System.out.println(nome);
        System.out.println(gerente);
    }
    public int getCodAgencia(){
        return codAgencia;
    }
    
    public String getNome(){
        return nome;
    }
    
    public String getGerente(){
        return gerente;
    }
    
    public void setCod(int cod){
        this.codAgencia = cod;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public void setGerente(String gerente){
        this.gerente = gerente;
    }
    
    
}
