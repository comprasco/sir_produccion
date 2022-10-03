package gov.sir.core.negocio.modelo.util;

import gov.sir.core.web.helpers.comun.ElementoLista;

import java.util.Comparator;

public class IDComparator implements Comparator 
{
	public int compare(Object elementoDato, Object anotherElementoDato)
	{
		try
		{
			ElementoLista dato1 = (ElementoLista) elementoDato;
			ElementoLista dato2 = (ElementoLista) anotherElementoDato;
			
			Integer id1 = new Integer(dato1.getId());
			Integer id2 = new Integer(dato2.getId());
			return id1.compareTo(id2);
			
		} catch (NumberFormatException e) {
			return(0);
		}
	}
}