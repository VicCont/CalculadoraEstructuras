package algoritmocalculadora;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.math.BigDecimal;

/**
 *
 * @author Intelimatica04
 */
public class Parseadorapos  {
    public static boolean esValida(String candidata){
    return true;
    }
    
    public static boolean esDeApertura(char carac){
         return (carac=='(' || carac=='{' || carac=='[');
    }
    
    public static boolean esDeCierre (char iterado){
        return (iterado==')' || iterado=='}' || iterado==']');
    }
    
    public static BigDecimal evaluar(String cadena){
        PilaU<Character> operadores=new PilaU<Character>();
        PilaParser posfijo=new PilaParser();
        boolean previoOperador=false;
        String auxiliar="";
        if (esValida(cadena)){
            for (int i = 0; i < cadena.length(); i++) {
                boolean iterOperador=false;
                char iterado=cadena.charAt(i);
                if ((iterado>=48 && iterado<=57) || iterado=='.' || ((previoOperador || i==0)&& (iterado=='-' || iterado=='+')) ) {
                    auxiliar+=iterado;
                } else {
                    if (esDeApertura(iterado)){
                        operadores.push(iterado);
                    }
                    else if (esDeCierre(iterado)){
                        while (!esDeApertura(operadores.peek())) {                            
                            posfijo.push(new Character(operadores.pop()));
                        }
                    }
                    else {
                        operadores.push(new Character(iterado));
                        iterOperador=true;
                    }
                    if (!auxiliar.isEmpty()){
                        posfijo.push(new BigDecimal(auxiliar));
                        auxiliar="";
                    }
                }
                previoOperador=iterOperador;
                if (i==cadena.length()-1) {
                    if (!auxiliar.isEmpty()) {
                        posfijo.push(new BigDecimal(auxiliar));
                    }
                    while (!operadores.isEmpty()) {                        
                        posfijo.push(new Character(operadores.pop()));
                    }
                }
            }
            while (!posfijo.isEmpty()) {                
                System.out.println(posfijo.pop().toString());
            }
            return BigDecimal.ONE;

        }
        else 
            return null;
    }
    

}
