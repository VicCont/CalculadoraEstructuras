/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Intelimatica04
 */
public class PilaU <T> implements Pila<T>{
    private static int max=20;
    private T[] pendientes;
    private int tope;

    public PilaU(){
        pendientes=(T[]) new Object[max];
        tope=-1;
    }
    
    
    
    private void aumentaTamano(){
    T[]  nueva =(T[]) new Object[2*pendientes.length];
    for (int i = 0; i < pendientes.length; i++) {
        nueva[i] = (T)pendientes[i];
    }
    }
    
    public void push(T objeto) {
        if (tope+1==pendientes.length) {
            aumentaTamano();
        }
        pendientes[++tope]=(T) objeto;
    }

    public T pop() {
        if (tope==-1) {
            throw new FilaVacia("");
        }
        return (T) pendientes[tope--];
    }

    @Override
    public boolean isEmpty() {
        return tope==-1;
    }

    public T peek () {
        if (tope==-1) {
            throw new FilaVacia("");
        }
        return (T) pendientes[tope];
    }
    
}
