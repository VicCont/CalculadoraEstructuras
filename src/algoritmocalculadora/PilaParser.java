/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Intelimatica04
 */
public class PilaParser implements Pila<Object>{

    private static int max=20;
    private Object[] pendientes;
    private int tope;
    
    public PilaParser(){
        tope=-1;
        pendientes=new Object[max];
    }

    private void aumentaTamano(){
        Object[]  nueva = new Object[2*pendientes.length];
        for (int i = 0; i < pendientes.length; i++) {
            nueva[i] = pendientes[i];
        }
    }
    
    public void push(Object objeto) {
        if (tope+1==pendientes.length) {
            aumentaTamano();
        }
        pendientes[++tope]= objeto;
    }

    public Object pop() {
        if (tope==-1) {
            throw new FilaVacia("");
        }
        return pendientes[tope--];
    }

    @Override
    public boolean isEmpty() {
        return tope==-1;
    }

    public Object peek () {
        if (tope==-1) {
            throw new FilaVacia("");
        }
        return pendientes[tope];
    }
    
}
