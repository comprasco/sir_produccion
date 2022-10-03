package gov.sir.core.negocio.modelo.util;

import gov.sir.core.negocio.modelo.TipoNota;

import java.util.Comparator;

public class IdTipoNotaComparator implements Comparator 
{
	public int compare(Object tipoNota, Object anotherTipoNota)
	{
		try
		{
			Integer idTipoNota1 = new Integer(((TipoNota) tipoNota).getIdTipoNota().toUpperCase());
			Integer idTipoNota2 = new Integer (((TipoNota) anotherTipoNota).getIdTipoNota().toUpperCase());
			return idTipoNota1.compareTo(idTipoNota2);
		}
		catch (NumberFormatException e) 
		{
			e.printStackTrace();
			return(0);
		}
	}
}