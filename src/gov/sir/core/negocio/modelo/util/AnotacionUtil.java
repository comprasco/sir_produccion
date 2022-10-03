/*
 * Created on 24-feb-2005
 *
 */
package gov.sir.core.negocio.modelo.util;

import gov.sir.core.negocio.modelo.Anotacion;

/**
 * @author gvillal
 *
 */
public class AnotacionUtil extends ObjectUtil implements AnotacionUtilKeys 
{
	
	private Anotacion anotacion;
	
	/**
	 * Constructor de la clase
	 * @param anotacion
	 */
	public AnotacionUtil(Anotacion anotacion)
	{
		this.anotacion = anotacion;
	}
	
	//anotacion.getDocumento().getTipoDocumento().getNombre();
	//anotacion.getDocumento().getNumero();	

}
