/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dojointercalacao;


/**
 *
 * @author Beatriz
 */
public class No {

    private No left;
    private No right;
    private Cliente valor;

    public No(No left, No right, Cliente valor) {
        this.left = left;
        this.right = right;
        this.valor = valor;
    }

    public No getLeft() {
        return left;
    }

    public void setLeft(No left) {
        this.left = left;
    }

    public No getRight() {
        return right;
    }

    public void setRight(No right) {
        this.right = right;
    }

    public Cliente getValor() {
        return valor;
    }

    public void setValor(Cliente valor) {
        this.valor = valor;

    }
    
    
    
    
}
