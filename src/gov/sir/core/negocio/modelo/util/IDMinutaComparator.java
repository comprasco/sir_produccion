package gov.sir.core.negocio.modelo.util;

import gov.sir.core.negocio.modelo.Minuta;
import java.util.Comparator;
import java.util.StringTokenizer;


public class IDMinutaComparator implements Comparator 
{
	public int compare(Object minuta, Object anotherMinuta)
	{
		try
		{
			Minuta minuta1 = (Minuta) minuta;
			Minuta minuta2 = (Minuta) anotherMinuta;
			
			String idMinuta1 = minuta1.getIdMinuta();
			String idMinuta2 = minuta2.getIdMinuta();
			
			StringTokenizer st1 = new StringTokenizer(idMinuta1,"-");
			StringTokenizer st2 = new StringTokenizer(idMinuta2,"-");
			
			// se Compara el anio
			Integer anio1 = new Integer(st1.nextToken());
			Integer anio2 = new Integer(st2.nextToken());
			
			String circulo1 = st1.nextToken();
			String circulo2 = st2.nextToken();
			
			Integer proceso1 = new Integer(st1.nextToken());
			Integer proceso2 = new Integer(st2.nextToken());
			
			Integer turnito1 = new Integer(st1.nextToken());
			Integer turnito2 = new Integer(st2.nextToken());
			
			if (anio1.compareTo(anio2) == 0) {
				// Se comprara el idcirculo
				int comparator = 0;
				comparator = circulo1.compareTo(circulo2);
				if (comparator == 0) {
					// Se compara el idproceso
					if (proceso1.compareTo(proceso2) == 0) {
						//	Se compara el idTurno
						return turnito1.compareTo(turnito2);	
					} else {
						return proceso1.compareTo(proceso2);
					}
				} else {
					return comparator;
				}
			} else {
				return anio1.compareTo(anio2);
			}
			
		} catch (NumberFormatException e) {
			return(0);
		}
	}
}