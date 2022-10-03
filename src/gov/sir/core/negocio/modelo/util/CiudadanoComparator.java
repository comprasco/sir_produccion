package gov.sir.core.negocio.modelo.util;

import gov.sir.core.negocio.modelo.AnotacionCiudadano;

import java.util.Comparator;

public class CiudadanoComparator implements Comparator 
{
	
	public int compare(Object anotacionCiudadano, Object anotherAnotacionCiudadano)
	{
		AnotacionCiudadano c1;
		AnotacionCiudadano c2;
		try
		{
			c1 = (AnotacionCiudadano) anotacionCiudadano;
			c2 = (AnotacionCiudadano) anotherAnotacionCiudadano;
			
			Integer idAnotacion1 = new Integer((c1).getAnotacion().getOrden());
			Integer idAnotacion2 = new Integer((c2).getAnotacion().getOrden());
			
			if (idAnotacion1.compareTo(idAnotacion2) == 0) {
				String ap1 = c1.getCiudadano().getApellido1();
				String ap2 = c2.getCiudadano().getApellido1();
			
				return ap1.compareTo(ap2);
			} else {
				return idAnotacion1.compareTo(idAnotacion2);
			}
		}
		catch (NumberFormatException e) 
		{
			return(0);
		}
	}
}