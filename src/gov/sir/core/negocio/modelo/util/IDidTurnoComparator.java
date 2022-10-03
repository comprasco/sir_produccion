package gov.sir.core.negocio.modelo.util;

import gov.sir.core.negocio.modelo.Turno;

import java.util.Comparator;

public class IDidTurnoComparator implements Comparator 
{
	public int compare(Object turno, Object anotherTurno)
	{
		try
		{
			Turno turno1 = (Turno) turno;
			Turno turno2 = (Turno) anotherTurno;
			
			// se Compara el anio
			Integer anio1 = new Integer(turno1.getAnio());
			Integer anio2 = new Integer(turno2.getAnio());
			
			if (anio1.compareTo(anio2) == 0) {
				// Se comprara el idcirculo
				int comparator = 0;
				try {
					Integer circulo1 = new Integer(turno1.getIdCirculo());
					Integer circulo2 = new Integer(turno2.getIdCirculo());
					comparator = circulo1.compareTo(circulo2);
				} catch (Exception ee) {
					try {
						String cir1 = turno1.getIdCirculo();
						String cir2 = turno2.getIdCirculo();
						comparator = cir1.compareTo(cir2);
					} catch (Exception eee) {}
				}
				
				if ( comparator == 0) {
					// Se compara el idproceso
					Integer proceso1 = new Integer(Long.toString(turno1.getIdProceso()));
					Integer proceso2 = new Integer(Long.toString(turno2.getIdProceso()));
					
					if (proceso1.compareTo(proceso2) == 0) {
						//	Se compara el idTurno
						Integer turnito1 = new Integer(turno1.getIdTurno());
						Integer turnito2 = new Integer(turno2.getIdTurno());
						
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