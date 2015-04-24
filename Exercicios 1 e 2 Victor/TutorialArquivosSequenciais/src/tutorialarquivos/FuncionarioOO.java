/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorialarquivos;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 * @author vanessa
 */
public class FuncionarioOO {

    private int codFuncionario;
    private String nome;
    private String cpf;
    private String dataNascimento;
    private double salario;
    
    /**
     *
     * Construtor vazio
     */
    public FuncionarioOO() {
    }

    /**
     *
     * Construtor com todos os atributos do objeto
     */
    public FuncionarioOO(int codFuncionario, String nome, String cpf, String dataNascimento, double salario) {
        this.codFuncionario = codFuncionario;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.salario = salario;
    }

    /**
     *
     * Salva um Funcionário no arquivo, na posição atual do cursor
     */
    public void salva(DataOutputStream out) throws IOException {
        out.writeInt(codFuncionario);
        out.writeUTF(nome);
        out.writeUTF(cpf);
        out.writeUTF(dataNascimento);
        out.writeDouble(salario);
        System.out.println("Gravação do funcionário de código " + codFuncionario + " realizada com sucesso");
    }

    /**
     *
     * Lê um Funcionário do arquivo, na posição atual do cursor
     */
    public void le(DataInputStream in) throws IOException {
        codFuncionario = in.readInt();
        nome = in.readUTF();
        cpf = in.readUTF();
        dataNascimento = in.readUTF();
        salario = in.readDouble();
    }

    public void imprime() {
        System.out.println("---------------------------");
        System.out.println(this.codFuncionario);
        System.out.println(this.nome);
        System.out.println(this.cpf);
        System.out.println(this.dataNascimento);
        System.out.println(this.salario);

    }
    
    public int getCodFuncionario(){
        return codFuncionario;
    }
    
    public String getNome(){
        return nome;
    }
    
    public String getCpf(){
        return cpf;
    }
    
    public String getDataNascimento(){
        return dataNascimento;
    }
    
    public double getSalario(){
        return salario;
    }
    
    public void setCodFuncionario(int cod){
        this.codFuncionario = cod;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    
    public void setDataNascimento(String data){
        this.dataNascimento = data;
    }
    
    public void setSalario(double salario){
        this.salario = salario;
    }
}
