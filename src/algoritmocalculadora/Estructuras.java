package algoritmocalculadora;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import algoritmocalculadora.Parseadorapos;
import java.math.BigDecimal;

/**
 *
 * @author Intelimatica04
 */
public class Estructuras {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            BigDecimal bd=Parseadorapos.evaluar("4444^44");
            System.out.println(bd);
            System.out.println(bd.unscaledValue());
        } catch (NumberFormatException e) {
            System.out.println("Tienes una operación invalida");
        }
    }
    
}
