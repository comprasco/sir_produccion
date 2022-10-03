/*
 * Created on 28-dic-2004
 */
package gov.sir.core.negocio.modelo.imprimibles.util;

import java.text.NumberFormat;

/**
 * @author gvillal
 * Clase que ofrece unas funciones para el formato de cadenas de caracteres.
 */
public class StringFormat {
	
	/**
	 * Funcion que retorna un numero aproximandolo a la decena como una cadena de texto.
	 * @param num el número que se quiere aproximar.
	 * @return String con el número formateado.
	 */
	public static String getNumeroFormateado(double num)
	{
		NumberFormat format = NumberFormat.getInstance(); 
		
		String valor = format.format(num);
		return valor;
	}
	
	/**
	 * Funcion que retorna el invero de una cadena
	 * @param cadena que se va a invertir.
	 * @return String con la cadena invertida.
	 */
	public static String inverso(String s) {
	      if (s.equals("")){
	        return "";
	      }
	      else{
	        return inverso(s.substring(1, s.length())) + s.substring(0, 1);
	      }
	    }

	/**
	 * Funcion que retorna un numero aproximandolo a la decena como un long.
	 * @param num el número que se quiere aproximar.
	 * @return long con el número formateado.
	 */    
	/*
	public static long getNumeroAproximadoADecena(double num)
	{
		long valor = 100*Math.round(num/100);
		
		return valor;
	}	*/

    /**
     * Funcion que retorna una cadena de texto rellena con espacios al principio con el fin
     * de centrar el texto respecto a un número máximo de caracteres. 
     * @param linea es la linea de texto que se quiere centrar.
     * @param maxCaracteres número máximo de caracteres por en cada linea del texto.
     * @param offset corrimiento de la linea desde el margen izquierdo 
     * @return la cadena centrada respecto a los parametros dados
     */
	public static String getCentrada(String linea, int maxCaracteres,int offset)
	{
		int tam = linea.length();
		int dif = maxCaracteres-tam;
		int delta = dif/2+offset;
		
		delta*= 2;//relacion caracter MAYUSCULA/espacio
		String relleno="";
		for(int i=0; i<delta; i++)
		{
			relleno+=" ";
		}
		
		
		return relleno+linea;
	}
	
	/**
	 * Imprime un string y valida si la cadena está en null
	 * @author olopez
	 * @param str
	 * @return String
	 */
	public static String printString(String str){
		String cadena = str!=null ? str : " ";
		return cadena; 
	}

}
