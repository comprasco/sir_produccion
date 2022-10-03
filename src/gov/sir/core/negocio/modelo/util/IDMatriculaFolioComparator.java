package gov.sir.core.negocio.modelo.util;

import gov.sir.core.negocio.modelo.SolicitudFolio;

import java.util.Comparator;

public class IDMatriculaFolioComparator implements Comparator 
{
	public int compare(Object solicitudFolio, Object anotherSolicitudFolio)
	{
		String idMatricula1 = ((SolicitudFolio) solicitudFolio).getIdMatricula().toUpperCase();
		String idMatricula2 = ((SolicitudFolio) anotherSolicitudFolio).getIdMatricula().toUpperCase();
	
		return idMatricula1.compareTo(idMatricula2);
	}
}