package gov.sir.core.negocio.modelo.util;


import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class HashTableToListConverter {
	
	/**
	 * Toma los elementos de una hashtable y los inserta en una lista
	 * @param list
	 * @param table
	 */
	public static void convert(List list, Hashtable table){
		Enumeration enumeracion = table.keys();
		while(enumeracion.hasMoreElements()){
			Object o = (Object)enumeracion.nextElement();
			list.add(o);
		}
	}
}
