/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Intelimatica04
 */
public interface Pila <T>{
    public void push(T objeto);
    public T pop();
    public boolean isEmpty();
    public T peek();
}
