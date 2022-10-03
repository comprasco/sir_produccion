package gov.sir.core.negocio.modelo.util;

import gov.sir.core.negocio.modelo.Anotacion;

import java.util.Comparator;

public class IdAnotacionComparator implements Comparator 
{
	public int compare(Object anotacion, Object anotherAnotacion)
	{
		try
		{
			Integer idAnotacion1 = new Integer(((Anotacion) anotacion).getOrden().toUpperCase());
			Integer idAnotacion2 = new Integer(((Anotacion) anotherAnotacion).getOrden().toUpperCase());
			return idAnotacion1.compareTo(idAnotacion2);
		}
		catch (NumberFormatException e) 
		{
			return(0);
		}
	}
}