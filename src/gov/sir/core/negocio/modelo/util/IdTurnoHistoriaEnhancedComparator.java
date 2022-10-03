package gov.sir.core.negocio.modelo.util;

import gov.sir.core.negocio.modelo.jdogenie.TurnoHistoriaEnhanced;

import java.util.Comparator;
import java.util.Date;

public class IdTurnoHistoriaEnhancedComparator implements Comparator 
{
	public int compare(Object turnoHistoria, Object anotherTurnoHistoria)
	{
		try
		{
			//se Compara primero la fecha si son iguales se compara el identificador
//			 Se valida fecha de Readicacion
			Date fecha1 = ((TurnoHistoriaEnhanced) turnoHistoria).getFecha();
			Date fecha2 = ((TurnoHistoriaEnhanced) anotherTurnoHistoria).getFecha();
			
			if (fecha1.compareTo(fecha2) == 0) {
				Integer idTurnoHistoria1 = new Integer(((TurnoHistoriaEnhanced) turnoHistoria).getIdTurnoHistoria());
				Integer idTurnoHistoria2 = new Integer(((TurnoHistoriaEnhanced) anotherTurnoHistoria).getIdTurnoHistoria());
				return idTurnoHistoria1.compareTo(idTurnoHistoria2);
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