package gov.sir.core.negocio.modelo.util;

import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.Ciudadano;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AnotacionCiudadanoComparator implements Comparator {

	public int compare(Object o1, Object o2) {
		try 
		{
			AnotacionCiudadano anotaCiudadano1 = (AnotacionCiudadano)o1;
			AnotacionCiudadano anotaCiudadano2 = (AnotacionCiudadano)o2;
			
			if (anotaCiudadano1.getRolPersona().equals(AnotacionCiudadano.ROL_PERSONA_DE)
					&& anotaCiudadano2.getRolPersona().equals(AnotacionCiudadano.ROL_PERSONA_A))
			{
				return -1;
			}
			
			if (anotaCiudadano1.getRolPersona().equals(AnotacionCiudadano.ROL_PERSONA_A)
					&& anotaCiudadano2.getRolPersona().equals(AnotacionCiudadano.ROL_PERSONA_DE))
			{
				return 1;
			}
			
			Ciudadano ciu1 = anotaCiudadano1.getCiudadano();
			Ciudadano ciu2 = anotaCiudadano2.getCiudadano();
			
			String nombre1 = ((ciu1.getApellido1()==null)?" ":ciu1.getApellido1().trim()) + " " +((ciu1.getApellido2()==null)?" ":ciu2.getApellido1().trim()) + " " + ((ciu1.getNombre()==null)?" ":ciu1.getNombre().trim()) + " " ;
			String nombre2 = ((ciu2.getApellido1()==null)?" ":ciu2.getApellido1().trim()) + " " +((ciu2.getApellido2()==null)?" ":ciu2.getApellido2().trim()) + " " + ((ciu2.getNombre()==null)?" ":ciu2.getNombre().trim()) + " " ;
			
			return nombre1.compareTo(nombre2);
		} catch (Throwable th)
		{
			Log.getInstance().warn(AnotacionCiudadanoComparator.class, "Error ordenando anotaciones Ciudadano", th);
		}
		return 0;
	}
	
	public static void ordenarAnotacionesCiudadano (List anotaCiudadanos)
	{
		try
		{
			Collections.sort(anotaCiudadanos, new AnotacionCiudadanoComparator());
		} catch (Throwable th)
		{
			Log.getInstance().warn(AnotacionCiudadanoComparator.class, "Error en ordenarAnotacionesCiudadano", th);
		}
	}

}
