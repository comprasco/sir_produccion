package gov.sir.core.negocio.modelo.util;

import gov.sir.core.negocio.modelo.AnotacionCiudadano;

import java.util.Comparator;

public class RolAnotaCiudadanoComparator implements Comparator 
{
	public int compare(Object anotaCiudadano, Object anotherAnotaCiudadano)
	{
		String rolPersona1 = new String(((AnotacionCiudadano) anotaCiudadano).getRolPersona().toUpperCase());
		String rolPersona2 = new String(((AnotacionCiudadano) anotherAnotaCiudadano).getRolPersona().toUpperCase());
		return -1 * rolPersona1.compareTo(rolPersona2);
	}
}