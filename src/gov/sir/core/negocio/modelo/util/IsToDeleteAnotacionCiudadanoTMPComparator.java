package gov.sir.core.negocio.modelo.util;

import gov.sir.core.negocio.modelo.jdogenie.AnotacionCiudadanoTMP;

import java.util.Comparator;

public class IsToDeleteAnotacionCiudadanoTMPComparator implements Comparator 
{
	public int compare(Object anotacionCiudadanoTMP, Object anotherAnotacionCiudadanoTMP)
	{
		int i = 0;
		
		boolean isToDelete1 = ((AnotacionCiudadanoTMP) anotacionCiudadanoTMP).isToDelete();
		boolean isToDelete2 = ((AnotacionCiudadanoTMP) anotherAnotacionCiudadanoTMP).isToDelete();
		
		if(!isToDelete1 && isToDelete2)		
			i = 1;

		if(isToDelete1 && isToDelete2)
			i =  0;

		if(isToDelete1 && !isToDelete2)
			i = -1;
		
		return i;
	}
}