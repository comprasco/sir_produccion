package gov.sir.core.negocio.modelo.util;

import gov.sir.core.negocio.modelo.Anotacion;

import java.util.Comparator;
import java.util.Date;

public class radicacionAnotacionComparator implements Comparator 
{
	public int compare(Object anotacion, Object anotherAnotacion)
	{
		Anotacion anotacion1;
		Anotacion anotacion2;
		
		try
		{
			anotacion1 = (Anotacion) anotacion;
			anotacion2 = (Anotacion) anotherAnotacion;
			
			// Se valida fecha de Readicacion
			Date fecha1 = anotacion1.getFechaRadicacion();
			Date fecha2 = anotacion2.getFechaRadicacion();
			
			//Se valida el folio
			String mat1 = anotacion1.getIdMatricula();
			String mat2 = anotacion2.getIdMatricula();
			
			Integer matSplit1 = new Integer(0);
			Integer matSplit2 = new Integer(0); 
			
			String[] matSpplit1 = mat1.split("-");
			if (matSpplit1.length == 2) {
				matSplit1 = new Integer(matSpplit1[1]);
			}
			
			String[] matSpplit2 = mat2.split("-");
			if (matSpplit2.length == 2) {
				matSplit2 = new Integer(matSpplit2[1]);
			}
				
			//Se valida e orden de la anotacion
			Integer idAnotacion1 =  new Integer(anotacion1.getOrden());
			Integer idAnotacion2 =  new Integer(anotacion2.getOrden());
			
			if (fecha1.compareTo(fecha2) == 0) {
				if (matSplit1.compareTo(matSplit2) == 0){
					return idAnotacion1.compareTo(idAnotacion2);
				} else {
					return matSplit1.compareTo(matSplit2);
				}
			} else {
				return fecha1.compareTo(fecha2);
			}
			
		}
		catch (NumberFormatException e) 
		{
			return(0);
		}
	}
}