package algoritmocalculadora;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Intelimatica04
 */
public class Parseadorapos  {
    
    
    public static boolean remplazo(String expresion){
        return true;
    }
    
    public static boolean esValida(String expresion){
/*
		 * -> ++ dos simbolos, a menos de que sea *-
		 * -> parentesis balanceados
		 * -> .. tampoco dos puntos
		 * -> no se puede div. entre cero
		 * 
		*/
		boolean res = true;
		
		PilaU<String> parentesis = new PilaU<String>();
		PilaU<String> expresionPila = new PilaU<String>();
		PilaU<String> aux = new PilaU<String>();
		String numeros = "9876543210";
		String operadores = "*+/-^.";
		int i = 0;
		
		while(i<expresion.length()&&res) {
			
			String n = "" + expresion.charAt(i);
			expresionPila.push(n);
			
			if(n.equals("(")) {
				parentesis.push(n);
				aux.push(expresionPila.pop());
				
				if(numeros.contains(expresionPila.peek())) {
					res = false;
					
				}
				
				expresionPila.push(aux.pop());
				
			} else if (n.equals(")")){
				if(parentesis.isEmpty() ) {
					res = false;	
		
				} else {
					parentesis.pop();
				}					
			} else if (operadores.contains(n)) {
				aux.push(expresionPila.pop());
				if(!expresionPila.isEmpty()) {
					if(aux.peek().equals("-")) {
						if(expresionPila.peek().equals("-")) {
							res = false;
						}
						
					}else if(operadores.contains(expresionPila.peek())||expresionPila.peek().equals("(")) {

						res = false;
					}
				} if(i+1==expresion.length()) {
					res = false;
				}
				expresionPila.push(aux.pop());			
			} else if(n.equals("0")) {
				aux.push(expresionPila.pop());
				if(!expresionPila.isEmpty()) {
					if(expresionPila.peek().equals("/")&&!numeros.contains(""+ expresion.charAt(i+1))){
						res = false;
						
					}
				
				}
				expresionPila.push(aux.pop());
			}		
		
		
		
		i++;
	}
		if(!parentesis.isEmpty()) {
			res = false;

		}
		
		
		return res;
		
	
	}
    
    
    private static BigDecimal calcula(BigDecimal num1, BigDecimal num2,char oper){
        if (oper=='+')
            return num1.add(num2,MathContext.DECIMAL128);
        else if (oper=='*')
            return num1.multiply(num2,MathContext.DECIMAL128);
        else if (oper=='/'){
            if  (num2.equals(BigDecimal.ZERO)){
                return new BigDecimal(Double.NaN);    
            }
            return num1.divide(num2,22,RoundingMode.HALF_EVEN);
        }
        else if (oper=='-')
            return num1.subtract(num2,MathContext.UNLIMITED);
        else
        {
            if (num1.equals(BigDecimal.ZERO) && num2.equals(BigDecimal.ZERO))
                return new BigDecimal(Double.NaN);
            return  BigDecimal.valueOf(Math.pow(num1.doubleValue(), num2.doubleValue()));
        }
    }
    
    private static BigDecimal resultado(ArrayList posfijo){
        PilaU<BigDecimal> pendientes=new PilaU<BigDecimal>();
        while (posfijo.size()!=0){
            if (posfijo.get(0) instanceof BigDecimal) {
                pendientes.push((BigDecimal)posfijo.remove(0));
            } else {
                BigDecimal n2=(BigDecimal)pendientes.pop(), n1=(BigDecimal)pendientes.pop();
                BigDecimal res=calcula(n1,n2,(Character)posfijo.remove(0));
                pendientes.push(res);
            }
        }
        return pendientes.pop();
    }
        
    public static boolean esDeApertura(char carac){
         return (carac=='(' || carac=='{' || carac=='[');
    }
    
    public static void agregarPos(ArrayList posfi, PilaU<Character> operadores, String aux){
        posfi.add(new BigDecimal(aux));
        posfi.add(operadores.pop());    
    }
    
    public static boolean prioridadOperador (char previo, char actual){
        if (esDeApertura(previo))
            return false;
        int disprevio=Math.abs(previo-44),disactual=Math.abs(actual-44);
        return disactual<disprevio;
    }
    
    public static boolean esDeCierre (char iterado){
        return (iterado==')' || iterado=='}' || iterado==']');
    }
    
    public static BigDecimal evaluar(String cadena) throws NumberFormatException{
        PilaU<Character> operadores=new PilaU<Character>();
        ArrayList posfijo=new ArrayList();
        boolean previoOperador=false;
        String auxiliar="";
        if (remplazo(cadena)){
            for (int i = 0; i < cadena.length(); i++) {
                boolean iterOperador=false;
                char iterado=cadena.charAt(i);
                if ((iterado>=48 && iterado<=57) || iterado=='.' || ((previoOperador || i==0)&& (iterado=='-' || iterado=='+')) ) {
                    auxiliar+=iterado;
                } else {
                    if (esDeCierre(iterado)){
                        while (!esDeApertura(operadores.peek())) {
                            if (!auxiliar.isEmpty()){
                                posfijo.add(new BigDecimal(auxiliar));
                                auxiliar="";
                            } 
                            posfijo.add(new Character(operadores.pop()));
                        }
                        operadores.pop();
                        try {
                            while (!operadores.isEmpty()||!esDeApertura(operadores.peek())) {                            
                                posfijo.add(new Character(operadores.pop()));
                        }
                        } catch (Exception e) {
                        }
                    }
                    else {
                        iterOperador=true;
                        if (esDeApertura(iterado)){
                            if (auxiliar.equals("+") || auxiliar.equals("-")) {
                                posfijo.add(new BigDecimal(auxiliar+"1"));
                                auxiliar="";
                                operadores.push('*');
                            }
                            operadores.push(new Character(iterado));
                        }
                        else{
                            try {
                                Character prev=operadores.peek();
                                if (prioridadOperador(prev, iterado)) {
                                    posfijo.add(new BigDecimal(auxiliar));
                                    auxiliar="";                                  
                                    posfijo.add(operadores.pop());
                                    try {
                                        Character prev2=operadores.peek();
                                        if(prev2=='-' && (iterado=='+' ||iterado=='-') ){
                                            posfijo.add(operadores.pop());
                                        }
                                    } catch (Exception e) {
                                    }
                                    operadores.push(new Character(iterado));                                                                            
                                }
                                else if (prev=='-' && (iterado=='+' || iterado=='-')){
                                    posfijo.add(new BigDecimal(auxiliar));
                                    auxiliar="";
                                    posfijo.add(operadores.pop());                                    
                                    operadores.push(new Character(iterado));                                    
                                }  
                                else {
                                    operadores.push(new Character(iterado));
                                }
                            } catch (Exception e) {
                                operadores.push(new Character(iterado));
                            }
                        } 
                    }
                    if (!auxiliar.isEmpty()){
                        posfijo.add(new BigDecimal(auxiliar));
                        auxiliar="";
                    }
                }
                previoOperador=iterOperador;
                if (i==cadena.length()-1) {
                    if (!auxiliar.isEmpty()) {
                        posfijo.add(new BigDecimal(auxiliar));
                    }
                    while (!operadores.isEmpty()) {                        
                        posfijo.add(new Character(operadores.pop()));
                    }
                }
            }
            for (Object object : posfijo) {
                System.out.print(object.toString()+" ");
            }
            return resultado(posfijo);

        }
        else 
            return null;
    }
    

}
