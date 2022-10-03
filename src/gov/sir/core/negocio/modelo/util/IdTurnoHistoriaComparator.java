package gov.sir.core.negocio.modelo.util;

import gov.sir.core.negocio.modelo.TurnoHistoria;

import java.util.Comparator;
import java.util.Date;

public class IdTurnoHistoriaComparator implements Comparator 
{
	public int compare(Object turnoHistoria, Object anotherTurnoHistoria)
	{
		try
		{
			//se Compara primero la fecha si son iguales se compara el identificador
//			 Se valida fecha de Readicacion
			Date fecha1 = ((TurnoHistoria) turnoHistoria).getFecha();
			Date fecha2 = ((TurnoHistoria) anotherTurnoHistoria).getFecha();
			
			if (fecha1.compareTo(fecha2) == 0) {
				Integer idTurnoHistoria1 = new Integer(((TurnoHistoria) turnoHistoria).getIdTurnoHistoria());
				Integer idTurnoHistoria2 = new Integer(((TurnoHistoria) anotherTurnoHistoria).getIdTurnoHistoria());
				return idTurnoHistoria1.compareTo(idTurnoHistoria2);
			} else {
				return fecha1.compareTo(fecha2);
			}
			
			
		}
		catch (NumberFormatException e) 
		{
			e.printStackTrace();
			return(0);
		}
	}
}